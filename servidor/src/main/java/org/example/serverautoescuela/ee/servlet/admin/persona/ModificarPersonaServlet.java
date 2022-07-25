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
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosCoche;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;
import java.util.List;

@WebServlet(name = Constantes.MODIFICAR_PERSONA_SERVLET, value = Constantes.WEB_ADMIN_PERSONA_UPDATE)
public class ModificarPersonaServlet extends HttpServlet {
    private final ServiciosPersona sp;
    private final ServiciosCoche sc;

    @Inject
    public ModificarPersonaServlet(ServiciosPersona sp, ServiciosCoche sc) {
        this.sp = sp;
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
        var dniUser = request.getParameter(Constantes.USUARIO_DNI_LISTA);
        try {
            if (dniUser != null || !dniUser.isBlank()) {
                //Si es root, no se modifica.
                if (dniUser.equals(Constantes.DNI_ROOT)) {
                    request.setAttribute(Constantes.UPDATE_PERSONA_FAIL, Constantes.EL_USUARIO_ROOT_NO_ES_MODIFICABLE);
                } else {
                    //Busco al usuario para dárselo al jsp.
                    Either<ApiError, Persona> respuesta = sp.getPersonaByDni(dniUser);
                    if (respuesta.isRight()) {
                        //Comprobamos si es profesor o alumno, para mandar coches o profesores, respectivamente.
                        if (respuesta.get().getTipoPersona().equals(Constantes.PROFESOR)) {
                            Either<ApiError, List<Coche>> getCoches = sc.getAllCoches();
                            request.setAttribute(Constantes.LISTA_COCHES, getCoches.get());
                        } else if (respuesta.get().getTipoPersona().equals(Constantes.ALUMNO)) {
                            Either<ApiError, List<Persona>> getProfesores = sp.getAllProfesores();
                            request.setAttribute(Constantes.LISTA_PROFESORES, getProfesores.get());
                        }
                        request.setAttribute(Constantes.UPDATE_PERSONA, respuesta.get());
                    } else {
                        request.setAttribute(Constantes.UPDATE_PERSONA_FAIL, respuesta.getLeft().getMensaje());
                    }
                }
            } else {
                request.setAttribute(Constantes.UPDATE_PERSONA_FAIL, Constantes.ERROR_AL_COGER_EL_DNI_DEL_USUARIO);
            }
        } catch (Exception e) {
            request.setAttribute(Constantes.UPDATE_PERSONA_FAIL, Constantes.ERROR_GENERICO);
        }
        //Vamos a la pantalla de pintar
        request.getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_UPDATE_PERSONA_JSP).forward(request, response);
    }
}
