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
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosIncidencia;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet(name = Constantes.ALL_INCIDENCIAS_SERVLET, value = Constantes.WEB_ADMIN_INCIDENCIA_ALL)
public class AllIncidenciasServlet extends HttpServlet {
    private final ServiciosIncidencia si;

    @Inject
    public AllIncidenciasServlet(ServiciosIncidencia si) {
        this.si = si;
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
                case Constantes.ALTA:
                    realizarRegistro(request);
                    break;
                case Constantes.BORRAR:
                    borrarIncidencia(request);
                    break;
                case Constantes.CERRAR:
                    cerrarIncidencia(request);
                    break;
                case Constantes.UPDATE:
                    updateIncidencia(request);
                    break;
            }
        }
        Either<ApiError, List<Incidencia>> resultado = si.getAllIncidencias();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_INCIDENCIA, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_INCIDENCIA_FAIL, resultado.getLeft().getMensaje());
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_INCIDENCIA_GET_ALL_INCIDENCIAS_JSP).forward(request, response);
    }


    private void realizarRegistro(HttpServletRequest request) {
        //Realizamos la construcción de la incidencia
        var dniProfesor = request.getParameter(Constantes.INCIDENCIA_PROFESOR);
        if (dniProfesor.equals(Constantes.X)) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.NO_SE_HA_SELECCIONADO_UN_PROFESOR);
        } else {
            Incidencia incidencia = Incidencia.builder()
                    .dniProfesor(dniProfesor)
                    .matricula(request.getParameter(Constantes.INCIDENCIA_MATRICULA))
                    .ubicacion(request.getParameter(Constantes.INCIDENCIA_UBICACION))
                    .titulo(request.getParameter(Constantes.INCIDENCIA_TITULO))
                    .fecha(LocalDateTime.parse(request.getParameter(Constantes.INCIDENCIA_FECHA)))
                    .descripcion(request.getParameter(Constantes.INCIDENCIA_DESCRIPCION))
                    .build();
            //Lo mandamos para guardar en la BBDD mediante
            Either<ApiError, Incidencia> respuesta = si.registrarIncidencia(incidencia);
            // Y ahora en base a lo que nos mande, lo imprimimos.
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, Constantes.LA_INCIDENCIA_SE_HA_REGISTRADO_CORRECTAMENTE);
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        }
    }

    private void borrarIncidencia(HttpServletRequest request) {
        var idIncidencia = Integer.parseInt(request.getParameter(Constantes.INCIDENCIA_ID_LISTA));
        try {
            Either<ApiError, Info> respuesta = si.deleteIncidencia(idIncidencia);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_ID_DE_LA_INCIDENCIA);
        }
    }

    private void cerrarIncidencia(HttpServletRequest request) {
        var idIncidencia = Integer.parseInt(request.getParameter(Constantes.INCIDENCIA_ID_LISTA));
        try {
            Either<ApiError, Info> respuesta = si.updateIncidenciaCerrar(idIncidencia);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_ID_DE_LA_INCIDENCIA);
        }
    }

    private void updateIncidencia(HttpServletRequest request) {
        //Creamos la incidencia
        try {
            Incidencia incidencia = Incidencia.builder()
                    .id(Integer.parseInt(request.getParameter(Constantes.INCIDENCIA_ID)))
                    .ubicacion(request.getParameter(Constantes.INCIDENCIA_UBICACION))
                    .titulo(request.getParameter(Constantes.INCIDENCIA_TITULO))
                    .descripcion(request.getParameter(Constantes.INCIDENCIA_DESCRIPCION))
                    .build();
            Either<ApiError, Info> respuesta = si.updateIncidencia(incidencia);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_ID_DE_LA_INCIDENCIA);
        }
    }
}
