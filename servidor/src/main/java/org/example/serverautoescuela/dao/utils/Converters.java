package org.example.serverautoescuela.dao.utils;

import org.bson.Document;
import org.example.serverautoescuela.dao.modelo.*;
import org.example.serverautoescuela.domain.*;

import java.time.LocalDateTime;

public class Converters {

    //Feedback
    public static Feedback convertDocumentToFeedback(Document d) {
        return Feedback.builder()
                .titulo(d.getString(Constantes.TITULO))
                .cuerpo(d.getString(Constantes.CUERPO))
                .puntuacion(d.getInteger(Constantes.PUNTUACION))
                .idClase(d.getInteger(Constantes.ID_CLASE))
                .dniAlumno(d.getString(Constantes.DNI_ALUMNO))
                .build();
    }

    public static Document convertFeedbackToDocument(Feedback feedback) {
        return new Document()
                .append(Constantes.TITULO, feedback.getTitulo())
                .append(Constantes.CUERPO, feedback.getCuerpo())
                .append(Constantes.PUNTUACION, feedback.getPuntuacion())
                .append(Constantes.ID_CLASE, feedback.getIdClase())
                .append(Constantes.DNI_ALUMNO, feedback.getDniAlumno());
    }

    //Personas
    public static Persona pasarAPersona(AdministradorEntity persona) {
        return Persona.builder()
                .nombre(persona.getNombre())
                .correo(persona.getCorreo())
                .dni(persona.getDni())
                .apellidos(persona.getApellidos())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .tipoPersona(Constantes.ADMINISTRADOR)
                .build();
    }

    public static Persona pasarAPersona(AlumnoEntity persona) {
        return Persona.builder()
                .nombre(persona.getNombre())
                .correo(persona.getCorreo())
                .dni(persona.getDni())
                .apellidos(persona.getApellidos())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .tipoPersona(Constantes.ALUMNO)
                .dniTutor(persona.getDniTutor())
                .aprobado(persona.isAprobado())
                .build();
    }

    public static Persona pasarAPersona(ProfesorEntity persona) {
        return Persona.builder()
                .nombre(persona.getNombre())
                .correo(persona.getCorreo())
                .dni(persona.getDni())
                .apellidos(persona.getApellidos())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .tipoPersona(Constantes.PROFESOR)
                .matriculaCoche(persona.getMatriculaCoche())
                .build();
    }

    //Incidencia
    public static Incidencia pasarAIncidencia(IncidenciaEntity incidencia) {
        return Incidencia.builder()
                .descripcion(incidencia.getDescripcion())
                .fecha(incidencia.getFecha().toLocalDateTime())
                .titulo(incidencia.getTituloIncidencia())
                .matricula(incidencia.getMatriculaCoche())
                .dniProfesor(incidencia.getDniProfesor())
                .ubicacion(incidencia.getUbicacion())
                .id(incidencia.getId())
                .resuelta(incidencia.isResuelta())
                .build();
    }

    //Coche
    public static Coche pasarACoche(CocheEntity coche) {
        return Coche.builder()
                .matricula(coche.getMatricula())
                .fechaCompra(coche.getFechaCompra().toLocalDate())
                .fechaCreacion(coche.getFechaCreacion().toLocalDate())
                .dniAdmin(coche.getDniAdmin())
                .marca(coche.getMarca())
                .modelo(coche.getModelo())
                .proximaItv(coche.getProximaItv().toLocalDate())
                .build();
    }

    //Clase
    public static Clase pasarAClase(ClaseEntity clase) {
        return Clase.builder()
                .fecha(clase.getFecha().toLocalDate())
                .horarioInicio(clase.getHorarioInicio())
                .duracion(clase.getDuracion())
                .dniAlumno(clase.getDniAlumno())
                .dniProfesor(clase.getDniProfesor())
                .id(clase.getId())
                .build();
    }


    //ApiError
    public static ApiError apiErrorGeneral() {
        return ApiError.builder()
                .mensaje(Constantes.FALLO_GENERAL)
                .fecha(LocalDateTime.now())
                .build();
    }
}
