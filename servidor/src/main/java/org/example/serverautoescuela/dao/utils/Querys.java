package org.example.serverautoescuela.dao.utils;

public class Querys {

    //Usuario
    public static final String SELECT_USUARIO_BY_DNI = "SELECT * FROM Usuario WHERE dni=?";
    public static final String UPDATE_USUARIO_CAMBIO_PASS = "UPDATE Usuario SET passhash=? WHERE dni=?";
    public static final String INSERT_USUARIO = "INSERT INTO Usuario (dni,passhash,tipoUsuario) VALUES (?,?,?)";
    public static final String DELETE_USUARIO = "DELETE FROM Usuario WHERE dni=?";

    //Administrador
    public static final String SELECT_ADMINISTRADOR_ALL = "SELECT * FROM Administrador";
    public static final String SELECT_ADMINISTRADOR_BY_DNI = "SELECT * FROM Administrador WHERE dni=?";
    public static final String INSERT_ADMINISTRADOR = "INSERT INTO Administrador " +
            "(dni,nombre,apellidos,telefono,correo,direccion) VALUES (?,?,?,?,?,?)";
    public static final String DELETE_ADMINISTRADOR = "DELETE FROM Administrador WHERE dni=?";
    public static final String UPDATE_ADMINISTRADOR = "UPDATE Administrador SET nombre=?, apellidos=?," +
            " correo=?, direccion=?, telefono=? WHERE dni=?";

    //Profesor
    public static final String SELECT_PROFESOR_ALL = "SELECT * FROM Profesor";
    public static final String SELECT_PROFESOR_NODETALLES = "SELECT nombre,apellidos,dni FROM Profesor";
    public static final String SELECT_PROFESOR_BY_DNI = "SELECT * FROM Profesor WHERE dni=?";
    public static final String INSERT_PROFESOR = "INSERT INTO Profesor " +
            "(dni,nombre,apellidos,telefono,correo,direccion, matriculaCoche) " +
            "VALUES (?,?,?,?,?,?,?)";
    public static final String DELETE_PROFESOR = "DELETE FROM Profesor WHERE dni=?";
    public static final String UPDATE_PROFESOR = "UPDATE Profesor SET nombre=?, apellidos=?, correo=?, direccion=?, " +
            "matriculaCoche=?, telefono=? WHERE dni=?";


    //Buscar Profesor/alumno por clase
    public static final String SELECT_PROFESOR_BY_CLASE = "SELECT Profesor.nombre, Profesor.apellidos" +
            " FROM Profesor " +
            "INNER JOIN Clase ON Profesor.dni = Clase.dniProfesor WHERE Clase.id = ?";
    public static final String SELECT_ALUMNO_BY_CLASE = "SELECT Alumno.dni, Alumno.nombre, Alumno.apellidos, Alumno.correo, Alumno.telefono " +
            "FROM Alumno " +
            "INNER JOIN Clase ON Alumno.dni = Clase.dniAlumno WHERE Clase.id = ?";


    //Alumno
    public static final String SELECT_ALUMNO_ALL = "SELECT * FROM Alumno";
    public static final String SELECT_ALUMNO_BY_DNI = "SELECT * FROM Alumno WHERE dni=?";
    public static final String SELECT_ALUMNO_NO_APROBADO = "SELECT * FROM Alumno WHERE aprobado=0";
    public static final String INSERT_ALUMNO = "INSERT INTO Alumno " +
            "(dni,nombre,apellidos,telefono,correo,direccion,dniTutor,aprobado) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    public static final String DELETE_ALUMNO = "DELETE FROM Alumno WHERE dni=?";
    public static final String UPDATE_ALUMNO = "UPDATE Alumno SET nombre=?, apellidos=?, correo=?, direccion=?, telefono=? " +
            "dniTutor=? WHERE dni=?";
    public static final String UPDATE_ALUMNO_APROBADO = "UPDATE Alumno SET aprobado=true WHERE dni=?";

    //Coches
    public static final String SELECT_COCHE_ALL = "SELECT * FROM Coche";
    public static final String SELECT_COCHE_BY_MATRICULA = "SELECT * FROM Coche WHERE matricula=?";
    public static final String INSERT_COCHE = "INSERT INTO Coche " +
            "(matricula,marca,modelo,proximaItv,fechaCreacion,fechaCompra,dniAdmin) VALUES (?,?,?,?,?,?,?)";
    public static final String DELETE_COCHE = "DELETE FROM Coche WHERE matricula=?";
    public static final String UPDATE_COCHE = "UPDATE Coche SET marca=?, modelo=?, proximaItv=?," +
            " fechaCreacion=?, fechaCompra=? WHERE matricula=?";
    public static final String UPDATE_COCHE_ITV_PASADA = "UPDATE coche SET proximaItv=? WHERE matricula=?";

    //clases
    public static final String SELECT_CLASE_ALL = "SELECT * FROM Clase";
    public static final String SELECT_CLASE_BY_ID = "SELECT * FROM Clase WHERE id=?";
    public static final String SELECT_CLASE_BY_IDALUMNO = "SELECT * FROM Clase WHERE dniAlumno=?";
    public static final String SELECT_CLASE_BY_IDPROFESOR = "SELECT * FROM Clase WHERE dniProfesor=?";
    public static final String SELECT_CLASE_BY_TWO_DATES_OCCUPED = "SELECT * FROM Clase WHERE fecha BETWEEN ? AND ?";
    public static final String SELECT_CLASE_BY_TWO_DATES_PROFESOR = "SELECT * FROM Clase WHERE (dniProfesor=?)" +
            " AND (fecha BETWEEN ? AND ?)";
    public static final String SELECT_CLASE_BY_TWO_DATES_ALUMNO = "SELECT * FROM Clase WHERE (dniAlumno=?)" +
            " AND (fecha BETWEEN ? AND ?)";
    public static final String SELECT_CLASE_BY_IDALUMNO_BEFORE = "SELECT * FROM Clase WHERE (dniAlumno=?)" +
            "AND (fecha < ?) ORDER BY fecha DESC";
    public static final String SELECT_CLASE_BY_IDPROFESOR_BEFORE = "SELECT * FROM Clase WHERE (dniProfesor=?)" +
            "AND (fecha BETWEEN ? AND ?) ORDER BY fecha DESC";


    public static final String INSERT_CLASE = "INSERT INTO Clase " +
            "(fecha, horarioInicio, duracion, dniProfesor, dniAlumno) VALUES (?,?,?,?,?)";
    public static final String DELETE_CLASE = "DELETE FROM Clase WHERE id=?";
    public static final String DELETE_CLASE_DATE = "DELETE FROM Clase WHERE dniProfesor=? AND fecha=?";
    public static final String UPDATE_CLASE = "UPDATE Clase SET  horarioInicio=?, duracion=? WHERE id=?";

    //Incidencias
    public static final String SELECT_INCIDENCIA_ALL = "SELECT * FROM Incidencia";
    public static final String SELECT_INCIDENCIA_BY_ID = "SELECT * FROM Incidencia WHERE id=?";
    public static final String SELECT_INCIDENCIA_OPEN = "SELECT * FROM Incidencia WHERE resuelta=0";
    public static final String INSERT_INCIDENCIA = "INSERT INTO Incidencia " +
            "(tituloIncidencia, descripcion, ubicacion,fecha, dniProfesor, matriculaCoche,resuelta) VALUES (?,?,?,?,?,?,0)";
    public static final String DELETE_INCIDENCIA = "DELETE FROM Incidencia WHERE id=?";
    public static final String UPDATE_INCIDENCIA = "UPDATE Incidencia SET tituloIncidencia=?, descripcion=?," +
            " ubicacion=? WHERE id=?";
    public static final String UPDATE_INCIDENCIA_RESUELTA = "UPDATE Incidencia SET resuelta=1 WHERE id=?";

}
