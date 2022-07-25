package org.example.serverautoescuela.ee.rest;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Feedback;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.rest.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosFeedback;

@RolesAllowed(Constantes.ALUMNO)
@Log4j2
@Path(Constantes.FEEDBACK)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestFeedback {
    private final ServiciosFeedback sf;

    @Inject
    public RestFeedback(ServiciosFeedback sf) {
        this.sf = sf;
    }

    @GET
    @Path(Constantes.GET_ID_CLASE)
    public Response getCocheByMatricula(@QueryParam(Constantes.ID_CLASE) int idClase) {
        Response response;
        Either<ApiError, Feedback> resultado = sf.getFeedbackByClase(idClase);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @POST
    @Path(Constantes.REGISTRAR_PATH)
    public Response registrarCoche(Feedback feedback) {
        Response response;
        Either<ApiError, Feedback> resultado = sf.saveFeedback(feedback);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @DELETE
    @Path(Constantes.BORRAR_PATH)
    public Response borrarCoche(@QueryParam(Constantes.ID_CLASE) int idClase) {
        Response response;
        Either<ApiError, Info> resultado = sf.deleteFeedback(idClase);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @PUT
    @Path(Constantes.MODIFICAR_PATH)
    public Response updateCoche(Feedback feedback) {
        Response response;
        Either<ApiError, Info> resultado = sf.updateFeedback(feedback);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }
}
