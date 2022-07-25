package org.example.serverautoescuela.dao.modelo;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IncidenciaEntity {
    private int id;
    private String tituloIncidencia;
    private String descripcion;
    private String ubicacion;
    private Timestamp fecha;
    private String dniProfesor;
    private String matriculaCoche;
    private boolean resuelta;
}
