<%--
  Created by IntelliJ IDEA.
  User: datadope
  Date: 3/5/22
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Gestión de coches</title>
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
            <li class="nav-item"><a href="#" class="nav-link active disabled">Gestión de coches</a></li>
            <li class="nav-item"><a href="alta" class="nav-link">Alta coche</a></li>
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
            <c:when test="${listaCarFail !=null}">
                <p>${listaCatFail}</p>
            </c:when>
            <c:when test="${listaCar != null}">
                <div class="h2">Coches registrados en nuestra aplicación:</div>
                <div>
                    <div class="bd-example-snippet bd-code-snippet">
                        <div class="bd-example">
                            <table class="table table-striped">
                                <thead class="table-light">
                                <tr>
                                    <th scope="col">Matrícula</th>
                                    <th scope="col">Marca</th>
                                    <th scope="col">Modelo</th>
                                    <th scope="col">proximaItv</th>
                                    <th scope="col">Fecha de Creación</th>
                                    <th scope="col">Fecha de compra</th>
                                </tr>
                                </thead>
                                <c:forEach items="${listaCar}" var="car">
                                    <tr>
                                        <th scope="col">${car.getMatricula()}</th>
                                        <td>${car.getMarca()}</td>
                                        <td>${car.getModelo()}</td>
                                        <td>${car.getProximaItv()}</td>
                                        <td>${car.getFechaCreacion()}</td>
                                        <td>${car.getFechaCompra()}</td>
                                        <td>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <form action="all">
                                                            <input type="text" name="cocheMatriculaLista"
                                                                   value="${car.getMatricula()}" hidden/>
                                                            <input type="text" name="accion" value="borrar" hidden/>
                                                            <input type="submit" class="btn btn-danger"
                                                                   value="Eliminar"/>
                                                        </form>
                                                    </div>
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <form action="update">
                                                            <input type="text" name="cocheMatriculaLista"
                                                                   value="${car.getMatricula()}" hidden/>
                                                            <input type="submit" class="btn btn-info"
                                                                   value="Modificar"/>
                                                        </form>
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
