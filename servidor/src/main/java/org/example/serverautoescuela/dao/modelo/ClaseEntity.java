package org.example.serverautoescuela.dao.modelo;

import lombok.*;

import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClaseEntity {
    private int id;
    private Date fecha;
    private String horarioInicio;
    private String duracion;
    private String dniProfesor;
    private String dniAlumno;
}
