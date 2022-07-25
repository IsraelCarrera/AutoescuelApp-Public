package org.example.serverautoescuela.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Incidencia {
    private int id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private LocalDateTime fecha;
    private String dniProfesor;
    private String matricula;
    private boolean resuelta;
}
