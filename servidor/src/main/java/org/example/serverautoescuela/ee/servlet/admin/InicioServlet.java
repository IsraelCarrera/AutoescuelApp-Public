package org.example.serverautoescuela.ee.servlet.admin;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;
import org.example.serverautoescuela.servicios.ServiciosPersona;

import java.io.IOException;

@WebServlet(name = Constantes.INICIO_ADMIN, value = Constantes.BARRA_WEB_ADMIN)
public class InicioServlet extends HttpServlet {
    private final ServiciosPersona sp;

    @Inject
    public InicioServlet(ServiciosPersona sp) {
        this.sp = sp;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerInicio(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerInicio(request, response);
    }

    private void hacerInicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var accion = request.getParameter("accion");
        if(accion!=null){
            if(accion.equals("cambiarPass")){
                cambiarPass(request);
            }
        }
        getServletContext().getRequestDispatcher(Constantes.JSP_INICIO_JSP).forward(request,response);
    }

    private void cambiarPass(HttpServletRequest request){
        //Recogemos los datos y hacemos comprobaciones.
        var passVieja = request.getParameter(Constantes.PASS_VIEJA);
        var passNueva = request.getParameter(Constantes.PASS_NUEVA);
        var passNuevaRep = request.getParameter(Constantes.PASS_NUEVA_REP);
        //Comprobamos que la pass vieja es la misma que la del usuario, para ello cogemos al usuario que está en sesión
        try{
            HttpSession session = request.getSession();
            Persona userLog = (Persona) session.getAttribute(Constantes.USUARIO_LOGEADO);
            //Ahora llamo a la función de cambiar contraseña, que lo comprueba to-do.
            if(passNueva.equals(passNuevaRep)) {
                Either<ApiError, Info> resultado = sp.cambioPassUsuario(userLog.getDni(), passVieja, passNueva);
                if(resultado.isRight()){
                    request.setAttribute(Constantes.RESPUESTA, resultado.get().getInfo());
                }else{
                    request.setAttribute(Constantes.RESPUESTA, resultado.getLeft().getMensaje());
                }
            }else{
                request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_LAS_CONTRASENAS_NO_COINCIDEN);
            }
        }catch (Exception e){
            request.setAttribute(Constantes.RESPUESTA, Constantes.ERROR_A_LA_COMPROBACION_DE_LA_CONTRASENA_ACTUAL);
        }finally {
            request.setAttribute(Constantes.VALUE_TITULO, Constantes.CAMBIO_DE_CONTRASENA);
        }
    }
}
