package org.example.serverautoescuela.dao.modelo;

import lombok.*;

import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CocheEntity {
    private String matricula;
    private String marca;
    private String modelo;
    private Date proximaItv;
    private Date fechaCreacion;
    private Date fechaCompra;
    private String dniAdmin;
}
