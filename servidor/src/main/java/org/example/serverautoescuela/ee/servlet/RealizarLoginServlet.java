package org.example.serverautoescuela.ee.servlet;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.domain.PersonaLogin;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;

@WebServlet(name = Constantes.LOG_WEB, value = Constantes.WEB_LOG)
public class RealizarLoginServlet extends HttpServlet {
    private final ServiciosPersona sp;

    @Inject
    public RealizarLoginServlet(ServiciosPersona sp) {
        this.sp = sp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizarLogin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizarLogin(request, response);
    }

    private void realizarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var dniUser = request.getParameter(Constantes.USUARIO_DNI);
        var passUser = request.getParameter(Constantes.USUARIO_PASS);
        PersonaLogin personaLogin = PersonaLogin.builder().dni(dniUser).pass(passUser).build();
        Either<ApiError, Persona> respuesta = sp.hacerLogin(personaLogin);
        if (respuesta.isRight()) {
            //Dentro de la respuesta, hay que comprobar que el usuario es admin.
            if (respuesta.get().getTipoPersona().equals(Constantes.ADMINISTRADOR)) {
                //Guardo al usuario
                HttpSession session = request.getSession();
                session.setAttribute(Constantes.USUARIO_LOGEADO, respuesta.get());
                //Ahora mandamos a la página de inicio
                request.getRequestDispatcher(Constantes.WEB_ADMIN).forward(request, response);
            } else {
                request.setAttribute(Constantes.RESPUESTA, Constantes.EL_USUARIO_NO_ES_ADMINISTRADOR);
                request.getRequestDispatcher(Constantes.JSP_LOG_FAIL_JSP).forward(request, response);
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.USUARIO_Y_O_CONTRASENA_INCORRECTOS);
            request.getRequestDispatcher(Constantes.JSP_LOG_FAIL_JSP).forward(request, response);

        }
    }
}
