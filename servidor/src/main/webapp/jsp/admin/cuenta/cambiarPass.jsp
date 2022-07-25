<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 05/06/2022
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Cambiar de contraseña</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../../../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<!-- Cabecera -->
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <img class="bi me-2" width="40" height="32" src="../../../images/AutoescuelappIcon.png"
             alt="Logo autoescuelApp">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="../../admin" class="nav-link">Inicio</a></li>
            <li class="nav-item"><a href="../../admin/persona/all" class="nav-link">Usuarios</a></li>
            <li class="nav-item"><a href="../../admin/coche/all" class="nav-link">Coches</a></li>
            <li class="nav-item"><a href="../../admin/clase/allProx" class="nav-link">Clases</a></li>
            <li class="nav-item"><a href="../../admin/incidencia/all" class="nav-link">Incidencias</a></li>
        </ul>
        <div class="col-md-3 text-end">
            <a href="#" class="btn btn-secondary active disabled">Cambiar pass</a>
            <a href="../../../web" class="btn btn-light">Salir</a>
        </div>
    </header>
</div>
<!-- main -->
<main>
    <div class="container">
        <div class="h2">Cambiar contraseña</div>
        <form action="../../admin" method="POST" class="needs-validation">
            <input type="text" name="accion" value="cambiarPass" hidden/>
            <div class="row g-3">
                <div class="col-12">
                    <label class="form-label">Contraseña actual </label>
                    <input type="password" class="form-control" id="passVieja" name="passVieja"
                           placeholder="Contraseña actual" required>
                    <div class="invalid-feedback">
                        La contraseña actual es necesaria.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">Contraseña nueva </label>
                    <input type="password" class="form-control" id="passNueva" name="passNueva"
                           placeholder="Contraseña nueva" required>
                    <div class="invalid-feedback">
                        La contraseña nueva es necesaria.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">repita contraseña nueva </label>
                    <input type="password" class="form-control" id="passNuevaRep" name="passNuevaRep"
                           placeholder="Contraseña nueva" required>
                    <div class="invalid-feedback">
                        Tiene que repetir la contraseña nueva.
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <input type="submit" class="btn btn-primary" value="Cambiar contraseña"/>
                    <input type="reset" class="btn btn-default" value="Borrar">
                </div>
            </div>
        </form>
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
