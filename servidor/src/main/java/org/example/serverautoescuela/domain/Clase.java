package org.example.serverautoescuela.domain;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Clase {
    private int id;
    private LocalDate fecha;
    private String horarioInicio;
    private String duracion;
    private String dniProfesor;
    private String dniAlumno;
}
