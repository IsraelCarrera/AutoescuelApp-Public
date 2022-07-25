<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/06/2022
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Alta clase</title>
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
            <li class="nav-item"><a href="../coche/all" class="nav-link">Coches</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Clases</a></li>
            <li class="nav-item"><a href="../incidencia/all" class="nav-link">Incidencias</a></li>
        </ul>
        <div class="col-md-3 text-end">
            <a href="../cuenta/cambiarPass" class="btn btn-secondary">Cambiar pass</a>
            <a href="../../../web" class="btn btn-light">Salir</a>
        </div>
    </div>
    <div class="border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="allProx" class="nav-link">Gestión de próximas clases</a></li>
            <li class="nav-item"><a href="allAnte" class="nav-link">Ver anteriores clases</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Alta clase</a></li>
        </ul>
    </div>
</header>


<!-- main -->
<main>
    <div class="container">
        <div class="h2">Registrar Clase</div>
        <c:choose>
            <c:when test="${listaAlumnosFail !=null}">
                <p>${listaAlumnosFail}</p>
            </c:when>
            <c:when test="${listaAlumnos != null}">
                <form action="allProx" method="POST" class="needs-validation">
                    <input type="text" name="accion" value="alta" hidden/>
                    <div class="row g-3">
                        <div class="col-12">
                            <label class="form-label">Alumno: </label>
                            <select class="form-select" id="claseDniAlumno" name="claseDniAlumno" required>
                                <option selected value="X" disabled>---</option>
                                <c:forEach items="${listaAlumnos}" var="alumno">
                                    <option value="${alumno.getDni()}">${alumno.getDni()}
                                        -- ${alumno.getNombre()} ${alumno.getApellidos()}</option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Selecciona un alumno.
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Fecha: </label>
                            <select class="form-select" id="claseFecha" name="claseFecha" required>
                                <option selected value="0" disabled>---</option>
                                <c:forEach items="${fecha}" var="fecha">
                                    <option value="${fecha}">${fecha}</option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Selecciona una fecha.
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Hora de inicio: </label>
                            <select class="form-select" id="claseInicio" name="claseInicio" required>
                                <option selected value="0" disabled>---</option>
                                <c:forEach items="${horario}" var="horario">
                                    <option value="${horario}">${horario}</option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Selecciona una hora.
                            </div>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="submit" class="btn btn-primary" value="Registrar"/>
                            <input type="reset" class="btn btn-default" value="Borrar">
                        </div>
                    </div>
                </form>
            </c:when>
        </c:choose>

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
