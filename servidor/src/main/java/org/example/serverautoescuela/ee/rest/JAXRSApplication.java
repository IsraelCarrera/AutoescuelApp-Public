package org.example.serverautoescuela.ee.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.serverautoescuela.ee.rest.utils.Constantes;

@ApplicationPath(Constantes.API)
@DeclareRoles({Constantes.ALUMNO, Constantes.PROFESOR, Constantes.ADMINISTRADOR})
public class JAXRSApplication extends Application {
}
