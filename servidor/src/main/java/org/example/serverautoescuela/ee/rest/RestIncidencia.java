package org.example.serverautoescuela.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Incidencia;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.rest.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosIncidencia;

import java.util.List;

@RolesAllowed(Constantes.PROFESOR)
@Log4j2
@Path(Constantes.INCIDENCIA_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestIncidencia {
    private final ServiciosIncidencia si;

    @Inject
    public RestIncidencia(ServiciosIncidencia si) {
        this.si = si;
    }

    @GET
    @Path(Constantes.GET_ALL_PATH)
    public Response getAllIncidencias() {
        Response response;
        Either<ApiError, List<Incidencia>> resultado = si.getAllIncidencias();
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
    @Path(Constantes.GET_ABIERTAS_PATH)
    public Response getAllIncidenciasAbiertas() {
        Response response;
        Either<ApiError, List<Incidencia>> resultado = si.getAllIncidenciasAbiertas();
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
    public Response getIncidenciaById(@PathParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Incidencia> resultado = si.getIncidenciaById(id);
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
    public Response registrarIncidencia(Incidencia incidencia) {
        Response response;
        Either<ApiError, Incidencia> resultado = si.registrarIncidencia(incidencia);
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
    public Response deleteIncidencia(@QueryParam(Constantes.ID) int id) {
        Response response;
        Either<ApiError, Info> resultado = si.deleteIncidencia(id);
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
    public Response updateIncidencia(Incidencia incidencia) {
        Response response;
        Either<ApiError, Info> resultado = si.updateIncidencia(incidencia);
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
    @Path(Constantes.CERRAR_INCIDENCIA_PATH)
    public Response updateIncidenciaCerrar(int id) {
        Response response;
        Either<ApiError, Info> resultado = si.updateIncidenciaCerrar(id);
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
