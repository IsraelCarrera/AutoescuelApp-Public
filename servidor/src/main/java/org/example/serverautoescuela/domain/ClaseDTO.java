package org.example.serverautoescuela.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClaseDTO {
    private String fecha;
    private String horarioInicio;
    private String duracion;
    private String dniProfesor;
    private String dniAlumno;
}
