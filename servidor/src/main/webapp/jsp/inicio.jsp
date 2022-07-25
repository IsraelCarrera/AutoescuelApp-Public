<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 25/04/2022
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Inicio Aplicación</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<!-- Cabecera -->
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <img class="bi me-2" width="40" height="32" src="../images/AutoescuelappIcon.png" alt="Logo autoescuelApp">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="#" class="nav-link active disabled">Inicio</a></li>
            <li class="nav-item"><a href="admin/persona/all" class="nav-link">Usuarios</a></li>
            <li class="nav-item"><a href="admin/coche/all" class="nav-link">Coches</a></li>
            <li class="nav-item"><a href="admin/clase/allProx" class="nav-link">Clases</a></li>
            <li class="nav-item"><a href="admin/incidencia/all" class="nav-link">Incidencias</a></li>
        </ul>
        <div class="col-md-3 text-end">
            <a href="admin/cuenta/cambiarPass" class="btn btn-secondary">Cambiar pass</a>
            <a href="../web" class="btn btn-light">Salir</a>
        </div>
    </header>
</div>

<!-- main -->
<main>
    <c:choose>
        <c:when test="${respuesta != null}">
            <div class="container">
                <div class="alert alert-primary alert-dismissible fade show" role="alert">
                        ${respuesta}
                </div>
            </div>
        </c:when>
    </c:choose>

    <div class="container">
        <div class="bd-example-snippet bd-code-snippet">
            <div class="bd-example">
                <p>Bienvenido, usuario ${usuarioLogeado.getNombre()}</p>
                <p>Estás en el inicio de la aplicación.</p>
            </div>
        </div>
    </div>
</main>


<!-- foot -->
<div class="container">
    <footer class="py-3 my-4">
        <div class="col-md-4 d-flex align-items-center">
            <span class="text-center text-muted">&copy; 2022 AutoescuelApp</span>
        </div>
    </footer>
</div>
</body>
</html>
