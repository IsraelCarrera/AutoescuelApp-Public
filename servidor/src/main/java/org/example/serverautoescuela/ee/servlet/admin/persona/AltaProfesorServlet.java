package org.example.serverautoescuela.ee.servlet.admin.persona;

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
import java.util.List;


@WebServlet(name = Constantes.ALTA_PROFESOR_SERVLET, value = Constantes.WEB_ADMIN_PERSONA_ALTA_PROFESOR)
public class AltaProfesorServlet extends HttpServlet {
    private final ServiciosCoche sc;

    @Inject
    public AltaProfesorServlet(ServiciosCoche sc) {
        this.sc = sc;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaPersona(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaPersona(request, response);
    }

    private void altaPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Llamar a los coches.
        Either<ApiError, List<Coche>> getCoches = sc.getAllCoches();
        if (getCoches.isRight()) {
            request.setAttribute(Constantes.LISTA_COCHES, getCoches.get());
            getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_ALTA_PROFESOR_JSP).forward(request, response);
        } else {
            request.setAttribute(Constantes.RESPUESTA, getCoches.getLeft().getMensaje());
        }
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_ALTA_PROFESOR_JSP).forward(request, response);
    }
}