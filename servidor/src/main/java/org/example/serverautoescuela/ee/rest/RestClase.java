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
import org.example.serverautoescuela.domain.Clase;
import org.example.serverautoescuela.domain.ClaseDTO;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.rest.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosClase;

import java.util.List;

@RolesAllowed(Constantes.ALUMNO)
@Log4j2
@Path(Constantes.CLASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestClase {
    private final ServiciosClase sc;

    @Inject
    public RestClase(ServiciosClase sc) {
        this.sc = sc;
    }

    @GET
    @Path(Constantes.GET_ALL_PATH)
    public Response getAllClases() {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getAllClases();
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

    @GET
    @Path(Constantes.ID_PATH)
    public Response getById(@PathParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Clase> resultado = sc.getClaseById(id);
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

    @GET
    @Path(Constantes.GET_ALUMNO_PATH)
    public Response getByIdAlumno(@QueryParam(Constantes.DNI_ALUMNO) String dniAlumno) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClaseByDniAlumno(dniAlumno);
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

    @GET
    @Path(Constantes.GET_PROFESOR_PATH)
    public Response getByIdProfesor(@QueryParam(Constantes.DNI_PROFESOR) String dniProfesor) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClaseByDniProfesor(dniProfesor);
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

    @GET
    @Path(Constantes.GET_DATES_PATH)
    public Response getByDates() {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClasesByTwoDates();
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

    @GET
    @Path(Constantes.GET_DATES_PROFESOR_PATH)
    public Response getByDatesProfesor(@QueryParam(Constantes.DNI_PROFESOR) String dniProfesor) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClasesByTwoDatesAndProfesor(dniProfesor);
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

    @GET
    @Path(Constantes.GET_DATES_ALUMNO_PATH)
    public Response getByDatesAlumno(@QueryParam(Constantes.DNI_ALUMNO) String dniAlumno) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClasesByTwoDatesAndAlumno(dniAlumno);
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

    @GET
    @Path(Constantes.GET_DATES_BEFORE_ALUMNO_PATH)
    public Response getByDatesBeforeAlumno(@QueryParam(Constantes.DNI_ALUMNO) String dniAlumno) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClaseByDniAlumnoBeforeDate(dniAlumno);
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

    @GET
    @Path(Constantes.GET_DATES_BEFORE_PROFESOR_PATH)
    public Response getByDatesBeforeProfesor(@QueryParam(Constantes.DNI_PROFESOR) String dniProfesor) {
        Response response;
        Either<ApiError, List<Clase>> resultado = sc.getClaseByDniProfesorBeforeDate(dniProfesor);
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
    public Response registrarClase(ClaseDTO clase) {
        Response response;
        Either<ApiError, Clase> resultado = sc.registrarClase(clase);
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
    public Response borrarClase(@QueryParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Info> resultado = sc.deleteClase(id);
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

    @RolesAllowed(Constantes.PROFESOR)
    @DELETE
    @Path(Constantes.BORRAR_DATE_PATH)
    public Response borrarClaseByDate(@QueryParam(Constantes.DNI_PROFESOR) String dni, @QueryParam(Constantes.FECHA) String fecha) {
        Response response;
        Either<ApiError, Info> resultado = sc.deleteClaseByDate(dni, fecha);
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
    public Response modificarClase(Clase clase) {
        Response response;
        Either<ApiError, Info> resultado = sc.updateClase(clase);
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
