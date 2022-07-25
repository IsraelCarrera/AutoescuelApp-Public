<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 07/05/2022
  Time: 19:54
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
            <li class="nav-item"><a href="alta" class="nav-link">Alta coche</a></li>
            <li class="nav-item"><a href="#" class="nav-link active disabled">Modificar coche</a></li>

        </ul>
    </div>
</header>

<!-- main -->
<main>
    <div class="container">
        <c:choose>
            <c:when test="${updateCoche !=null}">
                <h3>Modificar coche</h3>
                <form action="all" method="POST" class="needs-validation">
                    <input type="text" name="accion" value="update" hidden/>
                    <div class="row g-3">
                        <div class="col-12">
                            <label class="form-label">Matrícula </label>
                            <input type="text" class="form-control" value="${updateCoche.getMatricula()}"
                                   placeholder="Matrícula" required disabled>
                            <input type="text" name="cocheMatricula" value="${updateCoche.getMatricula()}" hidden/>
                            <div class="invalid-feedback">
                                La matrícula es necesario.
                            </div>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Marca </label>
                            <input type="text" class="form-control" id="cocheMarca" name="cocheMarca"
                                   value="${updateCoche.getMarca()}" placeholder="Marca" required>
                            <div class="invalid-feedback">
                                La marca del coche es necesario.
                            </div>
                        </div>
                        <div class="col-6">
                            <label class="form-label">Modelo </label>
                            <input type="text" class="form-control" id="cocheModelo" name="cocheModelo"
                                   placeholder="Modelo" value="${updateCoche.getModelo()}">
                            <div class="invalid-feedback">
                                El modelo del coche es necesarios.
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Próxima ITV </label>
                            <input type="date" class="form-control" id="cocheITV" name="cocheITV"
                                   placeholder="Fecha" value="${updateCoche.getProximaItv()}" required>
                            <div class="invalid-feedback">
                                Es necesario indicar la próxima ITV.
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="form-label">fecha de Creación </label>
                            <input type="date" class="form-control" id="cocheCreacion" name="cocheCreacion"
                                   value="${updateCoche.getFechaCreacion()}" placeholder="Fecha">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Fecha de la Compra </label>
                            <input type="date" class="form-control" id="cocheCompra" name="cocheCompra"
                                   placeholder="Fecha" value="${updateCoche.getFechaCompra()}" required>
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

            </c:when>
            <c:when test="${updateCocheFail != null}">
                <p>${updateCocheFail}</p>
            </c:when>
            <c:otherwise>
                <p>Ha habido un error interno, pruebe más tarde.</p>
            </c:otherwise>
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
