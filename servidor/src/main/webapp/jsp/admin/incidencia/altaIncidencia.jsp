<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/06/2022
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Alta incidencia</title>
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
            <li class="nav-item"><a href="../clase/allProx" class="nav-link ">Clases</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Incidencias</a></li>
        </ul>
        <div class="col-md-3 text-end">
            <a href="../cuenta/cambiarPass" class="btn btn-secondary">Cambiar pass</a>
            <a href="../../../web" class="btn btn-light">Salir</a>
        </div>
    </div>
    <div class="border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="all" class="nav-link">Gestión de incidencias</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Alta incidencia</a></li>

        </ul>
    </div>
</header>

<!-- main -->
<main>
    <div class="container">
        <div class="h2">Registrar Incidencia</div>
        <c:choose>
            <c:when test="${errorAltaIncidencia !=null}">
                <p>${errorAltaIncidencia}</p>
            </c:when>
            <c:when test="${listaCoches != null}">
                <form action="all" method="POST" class="needs-validation">
                    <div class="col-12">
                        <label class="form-label">Titulo </label>
                        <input type="text" class="form-control" id="incidenciaTitulo" name="incidenciaTitulo"
                               placeholder="Título" required>
                        <div class="invalid-feedback">
                            Ese necesario un título.
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Descripción </label>
                        <textarea type="text" class="form-control" id="incidenciaDescripcion"
                                  name="incidenciaDescripcion"
                                  placeholder="Descripción" rows="15" required></textarea>
                        <div class="invalid-feedback">
                            Es necesario una descripción.
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Ubicación </label>
                        <input type="text" class="form-control" id="incidenciaUbicacion" name="incidenciaUbicacion"
                               placeholder="Ubicación" required>
                        <div class="invalid-feedback">
                            Es necesaria una ubicación.
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Fecha </label>
                        <input type="datetime-local" class="form-control" id="incidenciaFecha" name="incidenciaFecha"
                               placeholder="Fecha" required>
                        <div class="invalid-feedback">
                            Es necesaria una fecha.
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Coche con incidencia: </label>
                        <select class="form-select" id="incidenciaMatricula" name="incidenciaMatricula" required>
                            <option selected value="X" disabled>---</option>
                            <c:forEach items="${listaCoches}" var="coche">
                                <option value="${coche.getMatricula()}">${coche.getMatricula()} --
                                        ${coche.getMarca()} ${coche.getModelo()}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback">
                            Selecciona un coche.
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Profesor que tuvo la incidencia: </label>
                        <select class="form-select" id="incidenciaProfesor" name="incidenciaProfesor" required>
                            <option selected value="X" disabled>---</option>
                            <c:forEach items="${listaProfesores}" var="profes">
                                <option value="${profes.getDni()}">${profes.getDni()}
                                    -- ${profes.getNombre()} ${profes.getApellidos()}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback">
                            Selecciona un profesor.
                        </div>
                    </div>
                    <input type="text" name="accion" value="alta" hidden/>
                    <div class="form-floating mb-3">
                        <input type="submit" class="btn btn-primary" value="Registrar"/>
                        <input type="reset" class="btn btn-default" value="Borrar">
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