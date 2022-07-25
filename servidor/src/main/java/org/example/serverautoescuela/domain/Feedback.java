package org.example.serverautoescuela.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feedback {
    private String titulo;
    private String cuerpo;
    private int puntuacion;
    private int idClase;
    private String dniAlumno;
}
