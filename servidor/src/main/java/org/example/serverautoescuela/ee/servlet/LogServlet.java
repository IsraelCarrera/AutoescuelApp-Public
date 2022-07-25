package org.example.serverautoescuela.ee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.LOG_SERVLET, value = Constantes.WEB)
public class LogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Constantes.JSP_LOGGIN_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Constantes.JSP_LOGGIN_JSP).forward(request, response);
    }
}
