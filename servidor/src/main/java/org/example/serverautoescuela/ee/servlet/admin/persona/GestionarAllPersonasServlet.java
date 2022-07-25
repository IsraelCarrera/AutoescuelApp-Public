package org.example.serverautoescuela.ee.servlet.admin.persona;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosFeedback;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;
import java.util.List;

@WebServlet(name = Constantes.GESTIONAR_ALL_PERSONAS_SERVLET, value = Constantes.WEB_ADMIN_PERSONA_ALL)
public class GestionarAllPersonasServlet extends HttpServlet {
    private final ServiciosPersona sp;
    private final ServiciosFeedback sf;

    @Inject
    public GestionarAllPersonasServlet(ServiciosPersona sp, ServiciosFeedback sf) {
        this.sp = sp;
        this.sf = sf;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintarTabla(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintarTabla(request, response);
    }

    private void pintarTabla(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var accion = request.getParameter(Constantes.ACCION);
        if (accion != null) {
            switch (accion) {
                case Constantes.ALTA_ADMIN:
                    realizarRegistroAdministrador(request);
                    break;
                case Constantes.ALTA_PROFE:
                    realizarRegistroProfesor(request);
                    break;
                case Constantes.ALTA_ALUMNO:
                    realizarRegistroAlumno(request);
                    break;
                case Constantes.BORRAR:
                    borrarPersona(request);
                    break;
                case Constantes.UPDATE:
                    update(request);
                    break;
                case Constantes.APROBADO:
                    aprobado(request);
                    break;
                case Constantes.REINICIAR_PASS:
                    solicitarPass(request);
                    break;
                case Constantes.BORRAR_COMENTARIOS:
                    borrarComentarios(request);
                    break;
            }
        }
        Either<ApiError, List<Persona>> resultado = sp.getAllPersonas();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_USER, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_USER_FAIL, resultado.getLeft().getMensaje());
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_GET_ALL_PERSONA_JSP).forward(request, response);
    }

    private void realizarRegistroAdministrador(HttpServletRequest request) {
        Persona persona = Persona.builder()
                .dni(request.getParameter(Constantes.USER_DNI))
                .nombre(request.getParameter(Constantes.USER_NOMBRE))
                .apellidos(request.getParameter(Constantes.USER_APELLIDOS))
                .telefono(request.getParameter(Constantes.USER_TELEFONO))
                .correo(request.getParameter(Constantes.USER_CORREO))
                .direccion(request.getParameter(Constantes.USER_DIRECCION))
                .tipoPersona(Constantes.ADMINISTRADOR)
                .build();
        Either<ApiError, Info> respuesta = sp.registrarPersona(persona);
        if (respuesta.isRight()) {
            request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
        } else {
            request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
        }
    }

    private void realizarRegistroAlumno(HttpServletRequest request) {
        var userDniProfesor = request.getParameter(Constantes.USER_DNI_PROFESOR);
        if (userDniProfesor != null) {
            Persona persona = Persona.builder()
                    .dni(request.getParameter(Constantes.USER_DNI))
                    .nombre(request.getParameter(Constantes.USER_NOMBRE))
                    .apellidos(request.getParameter(Constantes.USER_APELLIDOS))
                    .telefono(request.getParameter(Constantes.USER_TELEFONO))
                    .correo(request.getParameter(Constantes.USER_CORREO))
                    .direccion(request.getParameter(Constantes.USER_DIRECCION))
                    .tipoPersona(Constantes.ALUMNO)
                    .dniTutor(userDniProfesor)
                    .build();
            Either<ApiError, Info> respuesta = sp.registrarPersona(persona);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.NO_HAS_SELECCIONADO_UN_PROFESOR_CORRECTO_VUELVE_A_REGISTRARLO);
        }
    }

    private void realizarRegistroProfesor(HttpServletRequest request) {
        var userRegistrarMatriculaCoche = request.getParameter(Constantes.USER_MATRICULA_COCHE);
        if (userRegistrarMatriculaCoche != null) {
            Persona persona = Persona.builder()
                    .dni(request.getParameter(Constantes.USER_DNI))
                    .nombre(request.getParameter(Constantes.USER_NOMBRE))
                    .apellidos(request.getParameter(Constantes.USER_APELLIDOS))
                    .telefono(request.getParameter(Constantes.USER_TELEFONO))
                    .correo(request.getParameter(Constantes.USER_CORREO))
                    .direccion(request.getParameter(Constantes.USER_DIRECCION))
                    .matriculaCoche(userRegistrarMatriculaCoche)
                    .tipoPersona(Constantes.PROFESOR)
                    .build();
            Either<ApiError, Info> respuesta = sp.registrarPersona(persona);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.NO_HAS_SELECCIONADO_UN_COCHE_VUELVE_A_REGISTRAR_AL_PROFESOR);
        }
    }

    private void aprobado(HttpServletRequest request) {
        var dniUser = request.getParameter(Constantes.USUARIO_DNI_LISTA);
        if (dniUser != null || !dniUser.isBlank()) {
            Either<ApiError, Info> respuesta = sp.marcarAprobado(dniUser);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
        }
    }

    private void borrarPersona(HttpServletRequest request) {
        var dniUser = request.getParameter(Constantes.USUARIO_DNI_LISTA);
        if (dniUser != null || !dniUser.isBlank()) {
            Either<ApiError, Info> respuesta = sp.deletePersona(dniUser);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
        }
    }

    private void update(HttpServletRequest request) {
        Persona persona = Persona.builder()
                .dni(request.getParameter(Constantes.USER_DNI))
                .nombre(request.getParameter(Constantes.USER_NOMBRE))
                .apellidos(request.getParameter(Constantes.USER_APELLIDOS))
                .telefono(request.getParameter(Constantes.USER_TELEFONO))
                .correo(request.getParameter(Constantes.USER_CORREO))
                .direccion(request.getParameter(Constantes.USER_DIRECCION))
                .dniTutor(request.getParameter(Constantes.USER_DNI_PROFESOR))
                .matriculaCoche(request.getParameter(Constantes.USER_MATRICULA_COCHE))
                .build();
        Either<ApiError, Info> respuesta = sp.updatePersona(persona);
        if (respuesta.isRight()) {
            request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
        } else {
            request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
        }
    }

    private void solicitarPass(HttpServletRequest request) {
        var dniUser = request.getParameter(Constantes.USUARIO_DNI_LISTA);
        if (dniUser != null || !dniUser.isBlank()) {
            Either<ApiError, Info> respuesta = sp.reiniciarPass(dniUser);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
        }
    }

    private void borrarComentarios(HttpServletRequest request) {
        var dniUser = request.getParameter(Constantes.USUARIO_DNI_LISTA);
        if (dniUser != null || !dniUser.isBlank()) {
            Either<ApiError, Info> respuesta = sf.deleteAllFeedback(dniUser);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
        }
    }
}
