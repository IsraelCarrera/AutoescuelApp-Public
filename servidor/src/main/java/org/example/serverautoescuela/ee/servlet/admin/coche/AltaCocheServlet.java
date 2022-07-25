package org.example.serverautoescuela.ee.servlet.admin.coche;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.ALTA_COCHE_SERVLET, value = Constantes.WEB_ADMIN_COCHE_ALTA)
public class AltaCocheServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaCoche(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaCoche(request, response);
    }

    private void altaCoche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_COCHE_ALTA_COCHE_JSP).forward(request, response);
    }
}

