package org.example.serverautoescuela.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.serverautoescuela.dao.DaoClase;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Clase;
import org.example.serverautoescuela.domain.ClaseDTO;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.servicios.utils.Constantes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ServiciosClase {
    private final DaoClase dao;


    @Inject
    public ServiciosClase(DaoClase dao) {
        this.dao = dao;
    }

    //Coger todas las clases
    public Either<ApiError, List<Clase>> getAllClases() {
        return dao.getAllClases();
    }

    //Coger una clase por ID
    public Either<ApiError, Clase> getClaseById(int id) {
        return dao.getClaseById(id);
    }

    //Coger las clases que ha dado o dará un alumno por su ID
    public Either<ApiError, List<Clase>> getClaseByDniAlumno(String dniAlumno) {
        return dao.getClaseByDniAlumno(dniAlumno);
    }

    //Coger todas las clases que ha dado o dará un profesor por su ID
    public Either<ApiError, List<Clase>> getClaseByDniProfesor(String dniProfesor) {
        return dao.getClaseByDniProfesor(dniProfesor);
    }

    //Coger las clases que están ocupadas entre dos fechas.
    public Either<ApiError, List<Clase>> getClasesByTwoDates() {
        return dao.getClaseByDates(LocalDate.now(), LocalDate.now().plusWeeks(1));
    }

    //Coger las clases que están ocupadas de los 30 días anteriores.
    public Either<ApiError, List<Clase>> getClasesBy30daysBefore() {
        return dao.getClaseByDates(LocalDate.now().minusMonths(1), LocalDate.now());
    }

    //Coger las clases de un profesor entre dos fechas.
    public Either<ApiError, List<Clase>> getClasesByTwoDatesAndProfesor(String dniProfesor) {
        return dao.getClaseByDatesAndProfesor(dniProfesor);
    }

    //Coger las clases de un alumno entre dos fechas.
    public Either<ApiError, List<Clase>> getClasesByTwoDatesAndAlumno(String dniAlumno) {
        return dao.getClaseByDatesAndAlumno(dniAlumno);
    }

    //Coger las clases de un alumno de antes de la fecha actual.
    public Either<ApiError, List<Clase>> getClaseByDniAlumnoBeforeDate(String dniAlumno) {
        return dao.getClaseByDniAlumnoBeforeDate(dniAlumno);
    }

    //Coger las clases de un profesor de antes de la fecha actual.
    public Either<ApiError, List<Clase>> getClaseByDniProfesorBeforeDate(String dniProfesor) {
        return dao.getClaseByDniProfesorBeforeDate(dniProfesor);
    }

    //Registrar una clase
    public Either<ApiError, Clase> registrarClase(ClaseDTO claseDTO) {
        Either<ApiError, Clase> resultado;
        Clase clase = Clase.builder()
                .fecha(LocalDate.parse(claseDTO.getFecha()))
                .dniAlumno(claseDTO.getDniAlumno())
                .dniProfesor(claseDTO.getDniProfesor())
                .duracion(claseDTO.getDuracion())
                .horarioInicio(claseDTO.getHorarioInicio())
                .build();
        //Para que no haya duplicados, vamos a filtrar desde un array con todas las clases proximas.
        Either<ApiError, List<Clase>> getAllClasesProx = getClasesByTwoDates();
        if (getAllClasesProx.isRight()) {
            //Aquí filtramos.
            Clase comprobarRepetida = getAllClasesProx.get().stream()
                    .filter(cl -> cl.getFecha().equals(clase.getFecha()) &&
                            cl.getHorarioInicio().equals(clase.getHorarioInicio()) &&
                            cl.getDniProfesor().equals(clase.getDniProfesor())).findFirst().orElse(null);
            //Si fuese nulo, es que no hay una clase ahí.
            if (comprobarRepetida != null) {
                resultado = Either.left(ApiError.builder().fecha(LocalDateTime.now()).mensaje(Constantes.ESA_CLASE_YA_ESTA_SOLICITADA).build());
            } else {
                resultado = dao.registrarClase(clase);
            }
        } else {
            resultado = Either.left(ApiError.builder().fecha(LocalDateTime.now()).mensaje(Constantes.NO_SE_HA_PODIDO_ACCEDER_A_LA_INFORMACION_DE_LAS_CLASES).build());
        }
        return resultado;
    }

    //Eliminar una clase
    public Either<ApiError, Info> deleteClase(int id) {
        return dao.deleteClase(id);
    }

    public Either<ApiError, Info> deleteClaseByDate(String dni, String fechaStr) {
        return dao.deleteClaseByDate(dni, LocalDate.parse(fechaStr));
    }


    //Modificar una clase
    public Either<ApiError, Info> updateClase(Clase clase) {
        return dao.updateClase(clase);
    }
}
