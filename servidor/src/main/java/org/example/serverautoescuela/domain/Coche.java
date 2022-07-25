package org.example.serverautoescuela.domain;

import lombok.*;

import java.time.LocalDate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coche {
    private String matricula;
    private String marca;
    private String modelo;
    private LocalDate proximaItv;
    private LocalDate fechaCreacion;
    private LocalDate fechaCompra;
    private String dniAdmin;
}
