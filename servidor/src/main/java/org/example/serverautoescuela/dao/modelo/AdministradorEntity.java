package org.example.serverautoescuela.dao.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdministradorEntity {
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String passHash;

}
