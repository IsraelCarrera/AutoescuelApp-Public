package org.example.serverautoescuela.ee.servlet;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;


@WebServlet(name = Constantes.RESETEAR_PASS_DONE_SERVLET, value = Constantes.WEB_RESET_PASS_DONE)
public class ResetearPassDoneServlet extends HttpServlet {
    private final ServiciosPersona sp;

    @Inject
    public ResetearPassDoneServlet(ServiciosPersona sp) {
        this.sp = sp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        resetearPass(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        resetearPass(request, response);
    }

    private void resetearPass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var dni = request.getParameter(Constantes.DNI_RESET);
        Either<ApiError, Info> respuesta = sp.reiniciarPass(dni);
        if (respuesta.isRight()) {
            request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
        } else {
            request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());

        }
        request.getRequestDispatcher(Constantes.JSP_LOG_FAIL_JSP).forward(request, response);
    }
}
