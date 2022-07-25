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
import org.example.serverautoescuela.domain.Coche;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.rest.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosCoche;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Path(Constantes.COCHE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCoche {
    private final ServiciosCoche sc;

    @Inject
    public RestCoche(ServiciosCoche sc) {
        this.sc = sc;
    }

    @RolesAllowed(Constantes.PROFESOR)
    @GET
    @Path(Constantes.GET_ALL_PATH)
    public Response getAllcoches() {
        Response response;
        Either<ApiError, List<Coche>> resultado = sc.getAllCoches();
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

    @RolesAllowed(Constantes.ALUMNO)
    @GET
    @Path(Constantes.GET_MATRICULA_PATH)
    public Response getCocheByMatricula(@QueryParam(Constantes.MATRICULA) String matricula) {
        Response response;
        Either<ApiError, Coche> resultado = sc.getCocheByMatricula(matricula);
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

    @RolesAllowed(Constantes.ADMINISTRADOR)
    @POST
    @Path(Constantes.REGISTRAR_PATH)
    public Response registrarCoche(Coche coche) {
        Response response;
        Either<ApiError, Coche> resultado = sc.registrarCoche(coche);
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

    @RolesAllowed(Constantes.ADMINISTRADOR)
    @DELETE
    @Path(Constantes.BORRAR_PATH)
    public Response borrarCoche(@QueryParam(Constantes.MATRICULA) String matricula) {
        Response response;
        Either<ApiError, Info> resultado = sc.deleteCoche(matricula);
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

    @RolesAllowed(Constantes.ADMINISTRADOR)
    @PUT
    @Path(Constantes.MODIFICAR_PATH)
    public Response updateCoche(Coche coche) {
        Response response;
        Either<ApiError, Info> resultado = sc.updateCoche(coche);
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

    @RolesAllowed(Constantes.ADMINISTRADOR)
    @PUT
    @Path(Constantes.CAMBIAR_ITV_PATH)
    public Response cambiarItv(@QueryParam(Constantes.FECHA_ITV) String fecha, @QueryParam(Constantes.MATRICULA) String matricula) {
        LocalDate proximaITV = LocalDate.parse(fecha);
        Response response;
        Either<ApiError, Info> resultado = sc.updateCocheITV(proximaITV, matricula);
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
