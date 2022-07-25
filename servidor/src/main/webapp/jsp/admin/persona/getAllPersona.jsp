<%--
  Created by IntelliJ IDEA.
  User: Isra
  Date: 25/04/2022
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es-ES">
<head>
    <title>Gestión de personas</title>
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
    <div class="d-flex flex-wrap border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="#" class="nav-link active disabled">Gestión de usuarios</a></li>
            <li class="nav-item"><a href="altaAdmin" class="nav-link">Alta administrador</a></li>
            <li class="nav-item"><a href="altaProfesor" class="nav-link">Alta profesor</a></li>
            <li class="nav-item"><a href="altaAlumno" class="nav-link">Alta alumno</a></li>
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
            <c:when test="${listaUserFail !=null}">
                <p>${listaUserFail}</p>
            </c:when>
            <c:when test="${listaUser != null}">
                <div class="h2">Usuarios registrados en nuestra aplicación:</div>
                <div>
                    <div class="bd-example-snippet bd-code-snippet">
                        <div class="bd-example">
                            <table class="table table-striped">
                                <thead class="table-light">
                                <tr>
                                    <th scope="col">DNI</th>
                                    <th scope="col">Tipo de usuario</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellidos</th>
                                    <th scope="col">Correo</th>
                                    <th scope="col">teléfono</th>
                                    <th scope="col">Dirección</th>
                                    <th scope="col">Coche asignado</th>
                                    <th scope="col">Profesor Asignado</th>
                                    <th scope="col">Aprobado</th>
                                </tr>
                                </thead>
                                <c:forEach items="${listaUser}" var="usu">
                                    <tr>
                                        <th scope="col">${usu.getDni()}</th>
                                        <td>${usu.getTipoPersona()}</td>
                                        <td>${usu.getNombre()}</td>
                                        <td>${usu.getApellidos()}</td>
                                        <td>${usu.getCorreo()}</td>
                                        <td>${usu.getTelefono()}</td>
                                        <td>${usu.getDireccion()}</td>
                                        <c:choose>
                                            <c:when test="${usu.getTipoPersona() == 'profesor'}">
                                                <td>${usu.getMatriculaCoche()}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${usu.getTipoPersona() == 'alumno'}">
                                                <td>${usu.getDniTutor()}</td>
                                                <td>${usu.isAprobado()}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <c:if test="${usu.getDni() != usuarioLogeado.getDni() }">
                                                            <form action="all">
                                                                <input type="text" name="usuarioDniLista"
                                                                       value="${usu.getDni()}"
                                                                       hidden/>
                                                                <input type="text" name="accion" value="borrar" hidden/>
                                                                <input type="submit" class="btn btn-danger"
                                                                       value="Eliminar"/>
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-lg-6 col-md-6 col-sm-12 mb-2">
                                                        <form action="update">
                                                            <input type="text" name="usuarioDniLista"
                                                                   value="${usu.getDni()}"
                                                                   hidden/>
                                                            <input type="submit" class="btn btn-info"
                                                                   value="Modificar"/>
                                                        </form>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 mb-1">
                                                        <form action="all">
                                                            <input type="text" name="usuarioDniLista"
                                                                   value="${usu.getDni()}"
                                                                   hidden/>
                                                            <input type="text" name="accion" value="reiniciarPass"
                                                                   hidden/>
                                                            <input type="submit" class="btn btn-secondary"
                                                                   value="Reiniciar Contraseña"/>
                                                        </form>
                                                    </div>
                                                    <div class="col-lg-12 col-md-12 col-sm-12 mb-1">
                                                        <c:if test="${usu.getTipoPersona() eq 'alumno' and usu.isAprobado() eq false}">
                                                            <form action="all">
                                                                <input type="text" name="usuarioDniLista"
                                                                       value="${usu.getDni()}"
                                                                       hidden/>
                                                                <input type="text" name="accion" value="aprobado"
                                                                       hidden/>
                                                                <input type="submit" class="btn btn-success"
                                                                       value="Marcar Aprobado"/>
                                                            </form>
                                                        </c:if>
                                                        <c:if test="${usu.getTipoPersona() eq 'alumno' and usu.isAprobado() eq true}">
                                                            <form action="all">
                                                                <input type="text" name="usuarioDniLista"
                                                                       value="${usu.getDni()}"
                                                                       hidden/>
                                                                <input type="text" name="accion"
                                                                       value="borrarComentarios" hidden/>
                                                                <input type="submit" class="btn btn-danger"
                                                                       value="Borrar los comentarios"/>
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