package org.example.serverautoescuela.ee.servlet.admin.persona;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverautoescuela.ee.servlet.utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.ALTA_ADMINISTRADOR_SERVLET, value = Constantes.WEB_ADMIN_PERSONA_ALTA_ADMIN)
public class AltaAdministradorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaPersona(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        altaPersona(request, response);
    }

    private void altaPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(Constantes.JSP_ADMIN_PERSONA_ALTA_ADMINISTRADOR_JSP).forward(request, response);
    }
}
