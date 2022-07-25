package org.example.serverautoescuela.ee.servlet.admin.clase;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Clase;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosClase;

import java.io.IOException;
import java.util.List;


@WebServlet(name = Constantes.ALL_CLASES_ANTE_SERVLET, value = Constantes.WEB_ADMIN_CLASE_ALL_ANTE)
public class AllClasesAnteServlet extends HttpServlet {
    private final ServiciosClase sc;

    @Inject
    public AllClasesAnteServlet(ServiciosClase sc) {
        this.sc = sc;
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
        Either<ApiError, List<Clase>> resultado = sc.getClasesBy30daysBefore();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_CLASE, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_CLASE_FAIL, resultado.getLeft().getMensaje());
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_CLASE_GET_ALL_CLASES_ANTE_JSP).forward(request, response);
    }
}
