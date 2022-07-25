package org.example.serverautoescuela.ee.servlet.admin.clase;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.domain.*;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosClase;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;
import java.util.List;


@WebServlet(name = Constantes.ALL_CLASES_PROX_SERVLET, value = Constantes.WEB_ADMIN_CLASE_ALL_PROX)
public class AllClasesProxServlet extends HttpServlet {
    private final ServiciosClase sc;
    private final ServiciosPersona sp;

    @Inject
    public AllClasesProxServlet(ServiciosClase sc, ServiciosPersona sp) {
        this.sc = sc;
        this.sp = sp;
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
            if (accion.equals(Constantes.ALTA)) {
                realizarRegistro(request);
            } else if (accion.equals(Constantes.BORRAR)) {
                borrarClase(request);
            }
        }
        Either<ApiError, List<Clase>> resultado = sc.getClasesByTwoDates();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_CLASE, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_CLASE_FAIL, resultado.getLeft().getMensaje());
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_CLASE_GET_ALL_CLASES_PROX_JSP).forward(request, response);
    }

    private void realizarRegistro(HttpServletRequest request) {
        //Cogemos el Dni del alumno y le llamamos para coger a su tutor.
        var dniAlumno = request.getParameter(Constantes.CLASE_DNI_ALUMNO);
        var claseInicio = request.getParameter(Constantes.CLASE_INICIO);
        var claseFecha = request.getParameter(Constantes.CLASE_FECHA);
        if (dniAlumno.equals(Constantes.X) && claseFecha.equals(Constantes.AN_OBJECT) && claseInicio.equals(Constantes.AN_OBJECT)) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.NO_HA_SELECCIONADO_ALGUNO_DE_LOS_DATOS_NECESARIOS);
        } else {
            Either<ApiError, Persona> getPersona = sp.getPersonaByDni(dniAlumno);
            if (getPersona.isRight()) {
                ClaseDTO clase = ClaseDTO.builder()
                        .horarioInicio(request.getParameter(Constantes.CLASE_INICIO))
                        .duracion(Constantes.DURACION)
                        .dniProfesor(getPersona.get().getDniTutor())
                        .dniAlumno(dniAlumno)
                        .fecha(request.getParameter(Constantes.CLASE_FECHA))
                        .build();
                Either<ApiError, Clase> respuesta = sc.registrarClase(clase);
                if (respuesta.isRight()) {
                    request.setAttribute(Constantes.RESPUESTA, Constantes.LA_CLASE_SE_HA_REGISTRADO_CORRECTAMENTE);
                } else {
                    request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
                }
            } else {
                request.setAttribute(Constantes.RESPUESTA, getPersona.getLeft().getMensaje());
            }
        }
    }

    private void borrarClase(HttpServletRequest request) {
        var idClase = Integer.parseInt(request.getParameter(Constantes.CLASE_ID_LISTA));
        try {
            Either<ApiError, Info> respuesta = sc.deleteClase(idClase);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_EL_ID_DE_LA_CLASE);
        }
    }
}
