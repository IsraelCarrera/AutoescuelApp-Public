package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Feedback;
import org.example.serverautoescuela.domain.Info;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;


@Log4j2
public class DaoFeedback {
    private final DBMongo db;

    @Inject
    public DaoFeedback(DBMongo db) {
        this.db = db;
    }

    //Get un feedback
    public Either<ApiError, Feedback> getOneFeedback(int idClase) {
        Either<ApiError, Feedback> resultado;
        try {
            Feedback feedback = db.getDocument()
                    .find(eq(Constantes.ID_CLASE, idClase))
                    .projection(new Document()
                            .append(Constantes.TITULO, 1)
                            .append(Constantes.CUERPO, 1)
                            .append(Constantes.PUNTUACION, 1)
                            .append(Constantes.ID_CLASE, 1)
                            .append(Constantes.DNI_ALUMNO, 1))
                    .into(new ArrayList<>())
                    .stream()
                    .map(Converters::convertDocumentToFeedback)
                    .findFirst().orElse(null);
            if (feedback == null) {
                resultado = Either.left(ApiError.builder().mensaje(Constantes.NO_HAY_FEEDBACK_PARA_ESTA_CLASE).fecha(LocalDateTime.now()).build());
            } else {
                resultado = Either.right(feedback);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        } finally {
            db.cerrarMongo();
        }
        return resultado;
    }

    //Post feedback
    public Either<ApiError, Feedback> postFeedback(Feedback feedback) {
        Either<ApiError, Feedback> resultado;
        try {
            db.getDocument().insertOne(Converters.convertFeedbackToDocument(feedback));
            resultado = Either.right(feedback);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        } finally {
            db.cerrarMongo();
        }
        return resultado;
    }

    //Delete feedback
    public Either<ApiError, Info> deleteFeedback(int idClase) {
        Either<ApiError, Info> resultado;
        try {
            db.getDocument().deleteOne(eq(Constantes.ID_CLASE, idClase));
            resultado = Either.right(Info.builder().info(Constantes.FEEDBACK_DE_LA_CLASE_BORRADA_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        } finally {
            db.cerrarMongo();
        }
        return resultado;
    }

    //Delete for dniAlumno

    public Either<ApiError, Info> deleteFeedbackMany(String dniAlumno) {
        Either<ApiError, Info> resultado;
        try {
            db.getDocument().deleteMany(eq(Constantes.DNI_ALUMNO, dniAlumno));
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_TODAS_LAS_ENTRADAS_DE_FEEDBACK_DEL_ALUMNO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        } finally {
            db.cerrarMongo();
        }
        return resultado;
    }


    //Update feedback
    public Either<ApiError, Info> updateFeedback(Feedback feedback) {
        Either<ApiError, Info> resultado;
        try {
            //Borro la anterior
            db.getDocument().deleteOne(eq(Constantes.ID_CLASE, feedback.getIdClase()));
            //Añado una nueva
            db.getDocument().insertOne(Converters.convertFeedbackToDocument(feedback));
            resultado = Either.right(Info.builder().info(Constantes.EL_FEEDBACK_HA_SIDO_CAMBIADO_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        } finally {
            db.cerrarMongo();
        }
        return resultado;
    }
}
