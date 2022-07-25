<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 25/04/2022
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Fallo al entrar en la app</title>
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
        </ul>
        <div class="col-md-3 text-end">
            <a href="../web" class="btn btn-light">Volver</a>
        </div>
    </header>
</div>
<!-- main -->
<main>
    <div class="container">
        <div class="bd-example-snippet bd-code-snippet">
            <div class="bd-example">
                <p class="lead">${respuesta}</p>
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
