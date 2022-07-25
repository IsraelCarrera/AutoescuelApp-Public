package org.example.serverautoescuela.ee.servlet.admin.cuenta;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;

import java.io.IOException;


@WebServlet(name = Constantes.CAMBIAR_PASS_SERVLET, value = Constantes.WEB_ADMIN_CUENTA_CAMBIAR_PASS)
public class CambiarPassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cambiarPass(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cambiarPass(request, response);
    }

    private void cambiarPass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_CUENTA_CAMBIAR_PASS_JSP).forward(request, response);
    }
}
