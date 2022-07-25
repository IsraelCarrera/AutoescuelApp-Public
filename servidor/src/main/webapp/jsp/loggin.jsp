<%@ page import="org.example.serverautoescuela.domain.PersonaLogin" %><%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 25/04/2022
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Entrar en la app</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<!-- Cabecera -->
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <ul class="nav nav-pills">
        </ul>
        <div class="col-md-3 text-end">
        </div>
    </header>
</div>

<!-- Main -->
<main>
    <div class="container col-xl-10 col-xxl-8 px-4 py-5">
        <div class="row align-items-center g-lg-5 py-5">
            <div class="col-lg-7 text-center">
                <img class="img-responsive center-block" width="400" height="320" src="images/AutoescuelappIcon.png"
                     alt="Logo autoescuelApp">
            </div>
            <div class="col-md-10 mx-auto col-lg-5">
                <h1 class="hidden-xs">Bienvenido/a a AutoescuelApp</h1>
                <form class="p-4 p-md-5 border rounded-3 bg-light" action="web/log" method="POST">
                    <div class="form-floating mb-3">
                        <input class="form-control" type="text" id="usuarioDni" name="usuarioDni"
                               placeholder="Introduzca aquí DNI"
                               required>
                        <label>DNI</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input class="form-control" type="password" id="usuarioPass" name="usuarioPass"
                               placeholder="Introduzca aquí su contraseña" required>
                        <label>Contraseña</label>
                    </div>
                    <div class="form-floating mb-3">
                        <div class="form-floating mb-3">
                            <input type="submit" class="btn btn-primary" value="Enviar">
                            <input type="reset" class="btn btn-default" value="Cancelar">
                        </div>
                    </div>
                </form>
                <div class="p-0"> Si no recuerda la contraseña, pulse
                    <a href="web/resetPass" class="btn btn-link">aqui</a>
                    y recibirá un correo con una nueva
                </div>
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
