<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 05/06/2022
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Solicitar nueva contraseña</title>
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
        <div class="h2">Solicitar pass</div>
        <form action="resetPassDone" method="POST" class="needs-validation">
            <div class="row g-3">
                <div class="col-12">
                    <label class="form-label">Indique el DNI </label>
                    <input type="text" class="form-control" id="dniReset" name="dniReset"
                           placeholder="Contraseña actual" required>
                    <div class="invalid-feedback">
                        El DNI es necesario.
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <input type="submit" class="btn btn-primary" value="reiniciar contraseña"/>
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
