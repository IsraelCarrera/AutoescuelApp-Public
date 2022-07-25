<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/06/2022
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Gestión de incidencias</title>
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
            <li class="nav-item"><a href="#" class="nav-link active disabled">Gestión de incidencias</a></li>
            <li class="nav-item"><a href="alta" class="nav-link">Alta incidencia</a></li>
        </ul>
    </div>
</header>

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
        <c:choose>
            <c:when test="${listaIncidenciaFail !=null}">
                <p>${listaIncidenciaFail}</p>
            </c:when>
            <c:when test="${listaIncidencia != null}">
                <div class="h2">Incidencias registradas en nuestra aplicación:</div>
                <div>
                    <div class="bd-example-snippet bd-code-snippet">
                        <div class="bd-example">
                            <table class="table table-striped">
                                <thead class="table-light">
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Matricula del coche</th>
                                    <th scope="col">Titulo Incidencia</th>
                                    <th scope="col">Descripción</th>
                                    <th scope="col">Ubicación</th>
                                    <th scope="col">Fecha</th>
                                    <th scope="col">Dni del profesor que tuvo la incidencia</th>
                                    <th scope="col">Está resuelta</th>
                                </tr>
                                </thead>
                                <c:forEach items="${listaIncidencia}" var="inci">
                                    <tr>
                                        <th scope="col">${inci.getId()}</th>
                                        <td>${inci.getMatricula()}</td>
                                        <td>${inci.getTitulo()}</td>
                                        <td>${inci.getDescripcion()}</td>
                                        <td>${inci.getUbicacion()}</td>
                                        <td>${inci.getFecha().toString()}</td>
                                        <td>${inci.getDniProfesor()}</td>
                                        <td>${inci.isResuelta()}</td>
                                        <td>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <form action="all">
                                                            <input type="text" name="incidenciaIdLista"
                                                                   value="${inci.getId()}"
                                                                   hidden/>
                                                            <input type="text" name="accion"
                                                                   value="borrar"
                                                                   hidden/>
                                                            <input type="submit" class="btn btn-danger"
                                                                   value="Borrar"/>
                                                        </form>
                                                    </div>
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <form action="update">
                                                            <input type="text" name="incidenciaIdLista"
                                                                   value="${inci.getId()}"
                                                                   hidden/>
                                                            <input type="submit" class="btn btn-info"
                                                                   value="Modificar"/>
                                                        </form>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 mb-1">
                                                        <c:if test="${inci.isResuelta() eq false}">
                                                            <form action="all">
                                                                <input type="text" name="incidenciaIdLista"
                                                                       value="${inci.getId()}"
                                                                       hidden/>
                                                                <input type="text" name="accion"
                                                                       value="cerrar"
                                                                       hidden/>
                                                                <input type="submit" class="btn btn-success"
                                                                       value="Cerrar Incidencia"/>
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
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

