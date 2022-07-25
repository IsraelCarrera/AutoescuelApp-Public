<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 01/06/2022
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Gestión de clases</title>
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
            <li class="nav-item"><a href="#" class="nav-link active disabled">Gestión de próximas clases</a></li>
            <li class="nav-item"><a href="allAnte" class="nav-link">Ver anteriores clases</a></li>
            <li class="nav-item"><a href="alta" class="nav-link">Alta clase</a></li>
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
            <c:when test="${listaClaseFail !=null}">
                <p>${listaClaseFail}</p>
            </c:when>
            <c:when test="${listaClase != null}">
                <div class="h2">Proximas clases:</div>
                <div>
                    <div class="bd-example-snippet bd-code-snippet">
                        <div class="bd-example">
                            <table class="table table-striped">
                                <thead class="table-light">
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Dni Profesor</th>
                                    <th scope="col">Dni Alumno</th>
                                    <th scope="col">Fecha</th>
                                    <th scope="col">Horario de Inicio</th>
                                    <th scope="col">Duración</th>
                                </tr>
                                </thead>
                                <c:forEach items="${listaClase}" var="clas">
                                    <tr>
                                        <th scope="col">${clas.getId()}</th>
                                        <td>${clas.getDniProfesor()}</td>
                                        <td>${clas.getDniAlumno()}</td>
                                        <td>${clas.getFecha().toString()}</td>
                                        <td>${clas.getHorarioInicio()}</td>
                                        <td>${clas.getDuracion()}</td>
                                        <td>
                                            <div class="container">
                                                <div class="row">
                                                    <form action="allProx">
                                                        <input type="text" name="claseIdLista" value="${clas.getId()}"
                                                               hidden/>
                                                        <input type="text" name="accion" value="borrar" hidden/>
                                                        <input type="submit" class="btn btn-danger" value="Cancelar"/>
                                                    </form>
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
