<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 06/05/2022
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Alta coche</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../../../css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<!-- Cabecera -->
<header>
    <div class="d-flex flex-wrap justify-content-center py-3 border-bottom">
        <img class="bi me-2" width="40" height="32" src="../../../images/AutoescuelappIcon.png"
             alt="Logo autoescuelApp">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="../../admin" class="nav-link">Inicio</a></li>
            <li class="nav-item"><a href="../persona/all" class="nav-link">Usuarios</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Coches</a></li>
            <li class="nav-item"><a href="../clase/allProx" class="nav-link">Clases</a></li>
            <li class="nav-item"><a href="../incidencia/all" class="nav-link">Incidencias</a></li>
        </ul>
        <div class="col-md-3 text-end">
            <a href="../cuenta/cambiarPass" class="btn btn-secondary">Cambiar pass</a>
            <a href="../../../web" class="btn btn-light">Salir</a>
        </div>
    </div>
    <div class="border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="all" class="nav-link">Gestión de coches</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Alta coche</a></li>
        </ul>
    </div>
</header>

<!-- main -->
<main>
    <div class="container">
        <div class="h2">Registrar Coche</div>
        <form action="all" method="POST" class="needs-validation">
            <input type="text" name="accion" value="alta" hidden/>
            <div class="row g-3">
                <div class="col-12">
                    <label class="form-label">Matrícula </label>
                    <input type="text" class="form-control" id="cocheMatricula" name="cocheMatricula"
                           placeholder="Matrícula" required>
                    <div class="invalid-feedback">
                        La matrícula es necesario.
                    </div>
                </div>
                <div class="col-6">
                    <label class="form-label">Marca </label>
                    <input type="text" class="form-control" id="cocheMarca" name="cocheMarca" placeholder="Marca"
                           required>
                    <div class="invalid-feedback">
                        La marca del coche es necesario.
                    </div>
                </div>
                <div class="col-6">
                    <label class="form-label">Modelo </label>
                    <input type="text" class="form-control" id="cocheModelo" name="cocheModelo"
                           placeholder="Modelo">
                    <div class="invalid-feedback">
                        El modelo del coche es necesarios.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">Próxima ITV </label>
                    <input type="date" class="form-control" id="cocheITV" name="cocheITV"
                           placeholder="Fecha" required>
                    <div class="invalid-feedback">
                        Es necesario indicar la próxima ITV.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">fecha de Creación </label>
                    <input type="date" class="form-control" id="cocheCreacion" name="cocheCreacion"
                           placeholder="Fecha">
                </div>
                <div class="col-12">
                    <label class="form-label">Fecha de la Compra </label>
                    <input type="date" class="form-control" id="cocheCompra" name="cocheCompra"
                           placeholder="Fecha" required>
                    <div class="invalid-feedback">
                        Es necesario indicar la fecha de la compra.
                    </div>
                </div>

                <div class="form-floating mb-3">
                    <input type="submit" class="btn btn-primary" value="Registrar"/>
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