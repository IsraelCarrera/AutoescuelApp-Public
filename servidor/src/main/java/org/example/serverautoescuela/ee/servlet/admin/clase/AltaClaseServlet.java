package org.example.serverautoescuela.ee.servlet.admin.clase;

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = Constantes.ALTA_CLASE_SERVLET, value = Constantes.WEB_ADMIN_CLASE_ALTA)
public class AltaClaseServlet extends HttpServlet {
    private final ServiciosPersona sp;

    @Inject
    public AltaClaseServlet(ServiciosPersona sp) {
        this.sp = sp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaClase(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaClase(request, response);
    }

    private void altaClase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Cogemos alumnos
        Either<ApiError, List<Persona>> resultado = sp.getAllAlumnosNoAprobados();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_ALUMNOS, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_ALUMNOS_FAIL, resultado.getLeft().getMensaje());
        }
        //Ponemos fechas
        List<LocalDate> fecha = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            LocalDate fechaAdd = LocalDate.now().plusDays(i);
            if (fechaAdd.getDayOfWeek() != DayOfWeek.SUNDAY && fechaAdd.getDayOfWeek() != DayOfWeek.SATURDAY) {
                fecha.add(fechaAdd);
            }
        }
        request.setAttribute(Constantes.FECHA, fecha);
        //Ponemos array de horas.
        List<String> horario = Constantes.HORARIO_LIST;
        request.setAttribute(Constantes.HORARIO, horario);
        //Y ya nos vamos a la pag web.
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_CLASE_ALTA_CLASE_JSP).forward(request, response);
    }
}

