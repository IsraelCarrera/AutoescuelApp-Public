package org.example.serverautoescuela.ee.servlet.admin.incidencia;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Incidencia;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosIncidencia;

import java.io.IOException;


@WebServlet(name = Constantes.MODIFICAR_INCIDENCIA_SERVLET, value = Constantes.WEB_ADMIN_INCIDENCIA_UPDATE)
public class ModificarIncidenciaServlet extends HttpServlet {
    private final ServiciosIncidencia si;

    @Inject
    public ModificarIncidenciaServlet(ServiciosIncidencia si) {
        this.si = si;
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
        var idIncidencia = Integer.parseInt(request.getParameter(Constantes.INCIDENCIA_ID_LISTA));
        try {
            Either<ApiError, Incidencia> respuesta = si.getIncidenciaById(idIncidencia);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.UPDATE_INCIDENCIA, respuesta.get());
            } else {
                request.setAttribute(Constantes.UPDATE_INCIDENCIA_FAIL, respuesta.getLeft().getMensaje());
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.UPDATE_INCIDENCIA_FAIL, Constantes.ERROR_AL_COGER_EL_ID_DE_LA_INCIDENCIA);
        }
        //Vamos a la pantalla de pintar
        request.getRequestDispatcher(Constantes.JSP_ADMIN_INCIDENCIA_UPDATE_INCIDENCIA_JSP).forward(request, response);
    }
}