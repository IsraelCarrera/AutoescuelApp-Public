package org.example.serverautoescuela.ee.servlet.admin.persona;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;
import java.util.List;

@WebServlet(name = Constantes.ALTA_ALUMNO_SERVLET, value = Constantes.WEB_ADMIN_PERSONA_ALTA_ALUMNO)
public class AltaAlumnoServlet extends HttpServlet {
    private final ServiciosPersona sp;

    @Inject
    public AltaAlumnoServlet(ServiciosPersona sp) {
        this.sp = sp;
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
        //Llamar a los profesores.
        Either<ApiError, List<Persona>> getProfesores = sp.getAllProfesores();
        if (getProfesores.isRight()) {
            request.setAttribute(Constantes.LISTA_PROFESORES, getProfesores.get());
            getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_ALTA_ALUMNO_JSP).forward(request, response);
        } else {
            request.setAttribute(Constantes.RESPUESTA, getProfesores.getLeft().getMensaje());
        }
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_ALTA_ALUMNO_JSP).forward(request, response);
    }
}