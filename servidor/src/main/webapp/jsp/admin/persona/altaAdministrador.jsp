<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 25/04/2022
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Alta persona</title>
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
            <li class="nav-item"><a href="#" class="nav-link active disabled">Usuarios</a></li>
            <li class="nav-item"><a href="../coche/all" class="nav-link">Coches</a></li>
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
            <li class="nav-item"><a href="all" class="nav-link">Gestión de usuarios</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Alta administrador</a></li>
            <li class="nav-item"><a href="altaProfesor" class="nav-link">Alta profesor</a></li>
            <li class="nav-item"><a href="altaAlumno" class="nav-link">Alta alumno</a></li>
        </ul>
    </div>
</header>

<!-- main -->
<main>
    <div class="container">
        <div class="h2">Registrar usuario - Administrador</div>
        <form action="all" method="POST" class="needs-validation">
            <input type="text" name="accion" value="altaAdmin" hidden/>
            <div class="row g-3">
                <div class="col-12">
                    <label class="form-label">DNI </label>
                    <input type="text" class="form-control" id="userDni" name="userDni" placeholder="DNI" required>
                    <div class="invalid-feedback">
                        El DNI es necesario.
                    </div>
                </div>
                <div class="col-6">
                    <label class="form-label">Nombre </label>
                    <input type="text" class="form-control" id="userNombre" name="userNombre" placeholder="Nombre"
                           required>
                    <div class="invalid-feedback">
                        El nombre del alumno es necesario.
                    </div>
                </div>
                <div class="col-6">
                    <label class="form-label">Apellidos </label>
                    <input type="text" class="form-control" id="userApellidos" name="userApellidos"
                           placeholder="Apellidos" required>
                    <div class="invalid-feedback">
                        Los apellidos del alumno son necesarios.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">Email </label>
                    <input type="email" class="form-control" id="userCorreo" name="userCorreo"
                           placeholder="correo@correo.com" required>
                    <div class="invalid-feedback">
                        Es necesario un email válido del usuario.
                    </div>
                </div>
                <div class="col-12">
                    <label class="form-label">Teléfono: </label>
                    <input type="tel" class="form-control" id="userTelefono" name="userTelefono" placeholder="teléfono">
                </div>
                <div class="col-12">
                    <label class="form-label">Dirección: <span class="text-muted">(Optional)</span></label>
                    <input type="text" class="form-control" id="userDireccion" name="userDireccion"
                           placeholder="Calle Alcalá 102 3-B 28001 Madrid Madrid">
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