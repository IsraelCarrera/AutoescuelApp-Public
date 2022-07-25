package org.example.serverautoescuela.ee.servlet.admin.incidencia;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Coche;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosCoche;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;
import java.util.List;


@WebServlet(name = Constantes.ALTA_INCIDENCIA_SERVLET, value = Constantes.WEB_ADMIN_INCIDENCIA_ALTA)
public class AltaIncidenciaServlet extends HttpServlet {
    private final ServiciosCoche sc;
    private final ServiciosPersona sp;

    @Inject
    public AltaIncidenciaServlet(ServiciosCoche sc, ServiciosPersona sp) {
        this.sc = sc;
        this.sp = sp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaIncidencia(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaIncidencia(request, response);
    }

    private void altaIncidencia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Either<ApiError, List<Coche>> allCoches = sc.getAllCoches();
        Either<ApiError, List<Persona>> allProfesores = sp.getAllProfesores();
        if (allCoches.isRight() && allProfesores.isRight()) {
            request.setAttribute(Constantes.LISTA_COCHES, allCoches.get());
            request.setAttribute(Constantes.LISTA_PROFESORES, allProfesores.get());
        } else {
            request.setAttribute(Constantes.ERROR_ALTA_INCIDENCIA, Constantes.NO_SE_HA_PODIDO_RECOGER_LOS_DATOS_NECESARIOS_PARA_TRAMITAR_UNA_INCIDENCIA);
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_INCIDENCIA_ALTA_INCIDENCIA_JSP).forward(request, response);
    }
}
