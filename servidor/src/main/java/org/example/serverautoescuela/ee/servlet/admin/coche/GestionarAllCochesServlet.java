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
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosCoche;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet(name = Constantes.GESTIONAR_ALL_COCHES_SERVLET, value = Constantes.WEB_ADMIN_COCHE_ALL)
public class GestionarAllCochesServlet extends HttpServlet {
    private final ServiciosCoche sc;

    @Inject
    public GestionarAllCochesServlet(ServiciosCoche sc) {
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
        var accion = request.getParameter(Constantes.ACCION);
        if (accion != null) {
            switch (accion) {
                case Constantes.ALTA:
                    realizarRegistro(request);
                    break;
                case Constantes.BORRAR:
                    borrarCoche(request);
                    break;
                case Constantes.UPDATE:
                    update(request);
                    break;
            }
        }
        Either<ApiError, List<Coche>> resultado = sc.getAllCoches();
        if (resultado.isRight()) {
            request.setAttribute(Constantes.LISTA_CAR, resultado.get());
        } else {
            request.setAttribute(Constantes.LISTA_CAR_FAIL, resultado.getLeft().getMensaje());
        }
        request.getRequestDispatcher(Constantes.JSP_ADMIN_COCHE_GET_ALL_COCHE_JSP).forward(request, response);
    }

    private void realizarRegistro(HttpServletRequest request) {
        //Llamamos a todos los parámetros
        Coche coche = Coche.builder()
                .matricula(request.getParameter(Constantes.COCHE_MATRICULA))
                .marca(request.getParameter(Constantes.COCHE_MARCA))
                .modelo(request.getParameter(Constantes.COCHE_MODELO))
                .proximaItv(LocalDate.parse(request.getParameter(Constantes.COCHE_ITV)))
                .fechaCreacion(LocalDate.parse(request.getParameter(Constantes.COCHE_CREACION)))
                .fechaCompra(LocalDate.parse(request.getParameter(Constantes.COCHE_COMPRA)))
                .dniAdmin(((Persona) (request.getSession().getAttribute(Constantes.USUARIO_LOGEADO))).getDni())
                .build();
        Either<ApiError, Coche> respuesta = sc.registrarCoche(coche);
        if (respuesta.isRight()) {
            request.setAttribute(Constantes.RESPUESTA, Constantes.EL_COCHE_SE_HA_REGISTRADO_CORRECTAMENTE);
        } else {
            request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
        }
    }

    private void borrarCoche(HttpServletRequest request) {
        var matricula = request.getParameter(Constantes.COCHE_MATRICULA_LISTA);
        if (matricula != null || !matricula.isBlank()) {
            Either<ApiError, Info> respuesta = sc.deleteCoche(matricula);
            if (respuesta.isRight()) {
                request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
            } else {
                request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
            }
        } else {
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_AL_COGER_LA_MATRICULA_DEL_COCHE);
        }
    }

    private void update(HttpServletRequest request) {
        Coche coche = Coche.builder()
                .fechaCompra(LocalDate.parse(request.getParameter(Constantes.COCHE_COMPRA)))
                .fechaCreacion(LocalDate.parse(request.getParameter(Constantes.COCHE_CREACION)))
                .proximaItv(LocalDate.parse(request.getParameter(Constantes.COCHE_ITV)))
                .marca(request.getParameter(Constantes.COCHE_MARCA))
                .modelo(request.getParameter(Constantes.COCHE_MODELO))
                .matricula(request.getParameter(Constantes.COCHE_MATRICULA))
                .build();
        Either<ApiError, Info> respuesta = sc.updateCoche(coche);
        if (respuesta.isRight()) {
            request.setAttribute(Constantes.RESPUESTA, respuesta.get().getInfo());
        } else {
            request.setAttribute(Constantes.RESPUESTA, respuesta.getLeft().getMensaje());
        }
    }
}
