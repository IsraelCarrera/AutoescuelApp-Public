package com.example.autoescuelapp.data.utils

import com.example.autoescuelapp.data.modelo.*
import com.example.autoescuelapp.domain.*
import java.time.LocalDate

fun PersonaData.toPersona(): Persona {
    return Persona(
        apellidos = this.apellidos,
        aprobado = this.aprobado,
        correo = this.correo,
        direccion = this.direccion,
        dni = this.dni,
        nombre = this.nombre,
        telefono = this.telefono,
        tipoPersona = this.tipoPersona,
        dniTutor = this.dniTutor,
        matriculaCoche = this.matriculaCoche
    )
}

fun ClaseData.toClase(): Clase {
    return Clase(
        dniAlumno = this.dniAlumno,
        dniProfesor = this.dniProfesor,
        horarioInicio = this.horarioInicio,
        duracion = this.duracion,
        fecha = LocalDate.parse(this.fecha),
        id = this.id
    )
}

fun Clase.toClaseData(): ClaseData {
    return ClaseData(
        dniAlumno = this.dniAlumno,
        dniProfesor = this.dniProfesor,
        horarioInicio = this.horarioInicio,
        duracion = this.duracion,
        fecha = this.fecha.toString(),
        id = 0
    )
}

fun CocheData.toCoche(): Coche {
    return Coche(
        matricula = this.matricula,
        marca = this.marca,
        modelo = this.modelo,
        proximaItv = LocalDate.parse(this.proximaItv)
    )
}

fun Incidencia.toIncidenciaData(): IncidenciaData {
    return IncidenciaData(
        descripcion = this.descripcion,
        dniProfesor = this.dniProfesor,
        fecha = this.fecha.toString(),
        id = this.id,
        matriculaCoche = this.matriculaCoche,
        tituloIncidencia = this.tituloIncidencia,
        ubicacion = this.ubicacion
    )
}

fun InfoData.toStr(): String {
    return this.info
}

fun FeedbackData.toFeedback(): Feedback {
    return Feedback(
        titulo = this.titulo,
        cuerpo = this.cuerpo,
        puntuacion = this.puntuacion,
        idClase = this.idClase,
        dniAlumno = this.dniAlumno,
    )
}

fun Feedback.toFeedbackData(): FeedbackData {
    return FeedbackData(
        titulo = this.titulo,
        cuerpo = this.cuerpo,
        puntuacion = this.puntuacion,
        idClase = this.idClase,
        dniAlumno = this.dniAlumno,
    )
}