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
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.domain.PersonaLogin;
import org.example.serverautoescuela.ee.rest.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.util.List;

@PermitAll
@Log4j2
@Path(Constantes.USUARIO_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersona {
    private final ServiciosPersona sp;

    @Inject
    public RestPersona(ServiciosPersona sp) {
        this.sp = sp;
    }

    @GET
    @Path(Constantes.LOG_PATH)
    public Response getUser(@QueryParam(Constantes.DNI) String nombreUsuario, @QueryParam(Constantes.PASS) String pass) {
        Response response;
        PersonaLogin personaLogin = PersonaLogin.builder().dni(nombreUsuario).pass(pass).build();
        Either<ApiError, Persona> resultado = sp.hacerLogin(personaLogin);
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
    @Path(Constantes.REGISTRO_PATH)
    public Response registrarPersona(Persona persona) {
        Response response;
        Either<ApiError, Info> resultado = sp.registrarPersona(persona);
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
    @GET
    @Path(Constantes.GET_ALL_PATH)
    public Response getAllUsers() {
        Response response;
        Either<ApiError, List<Persona>> resultado = sp.getAllPersonas();
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
    @Path(Constantes.GET_DNI_PATH)
    public Response getByDni(@QueryParam(Constantes.DNI) String dni) {
        Response response;
        Either<ApiError, Persona> resultado = sp.getPersonaByDni(dni);
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
    @GET
    @Path(Constantes.GET_PROFESORES_PATH)
    public Response getAllProfesores() {
        Response response;
        Either<ApiError, List<Persona>> resultado = sp.getAllProfesores();
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
    @Path(Constantes.GET_CLASE_ALUMNO_PATH)
    public Response getAlumnoByIdClase(@QueryParam(Constantes.ID_CLASE) int idClase) {
        Response response;
        Either<ApiError, Persona> resultado = sp.getAlumnoIdClase(idClase);
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
    @Path(Constantes.GET_CLASE_PROFESOR_PATH)
    public Response getProfesorByIdClase(@QueryParam(Constantes.ID_CLASE) int idClase) {
        Response response;
        Either<ApiError, Persona> resultado = sp.getProfesorIdClase(idClase);
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
    @DELETE
    @Path(Constantes.BORRAR_PATH)
    public Response deletePersona(@QueryParam(Constantes.DNI) String dni) {
        Response response;
        Either<ApiError, Info> resultado = sp.deletePersona(dni);
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
    public Response modificarPersona(Persona persona) {
        Response response;
        Either<ApiError, Info> resultado = sp.updatePersona(persona);
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
    @PUT
    @Path(Constantes.CAMBIAR_PASS_PATH)
    public Response cambiarPass(@QueryParam(Constantes.DNI) String dni,
                                @QueryParam(Constantes.PASS_VIEJA) String passVieja,
                                @QueryParam(Constantes.PASS_NUEVA) String passNueva) {
        Response response;
        Either<ApiError, Info> resultado = sp.cambioPassUsuario(dni, passVieja, passNueva);
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
    @Path(Constantes.REINICIAR_PASS_PATH)
    public Response reiniciarPass(@QueryParam(Constantes.DNI) String dni) {
        Response response;
        Either<ApiError, Info> resultado = sp.reiniciarPass(dni);
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
