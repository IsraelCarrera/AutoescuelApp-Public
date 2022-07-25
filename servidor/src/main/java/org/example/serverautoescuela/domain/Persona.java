package org.example.serverautoescuela.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Persona {
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String tipoPersona;
    //Opc - Del profesor
    private String matriculaCoche;
    //Opc - Del alumno
    private String dniTutor;
    private boolean aprobado;
}
