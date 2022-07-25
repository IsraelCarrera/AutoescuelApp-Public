package org.example.serverautoescuela.ee.servlet.admin.coche;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Coche;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosCoche;

import java.io.IOException;


@WebServlet(name = Constantes.MODIFICAR_COCHE_SERVLET, value = Constantes.WEB_ADMIN_COCHE_UPDATE)
public class ModificarCocheServlet extends HttpServlet {
    private final ServiciosCoche sc;

    @Inject
    public ModificarCocheServlet(ServiciosCoche sc) {
        this.sc = sc;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        update(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        update(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var matricula = request.getParameter(Constantes.COCHE_MATRICULA_LISTA);
        if (matricula != null || !matricula.isBlank()) {
            //Busco el coche para dárselo al jsp.
            Either<ApiError, Coche> respuesta = sc.getCocheByMatricula(matricula);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.UPDATE_COCHE, respuesta.get());
            } else {
                request.setAttribute(Constantes.UPDATE_COCHE_FAIL, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.UPDATE_COCHE_FAIL, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
        }
        //Vamos a la pantalla de pintar
        request.getRequestDispatcher(Constantes.JSP_ADMIN_COCHE_UPDATE_COCHE_JSP).forward(request, response);
    }
}
