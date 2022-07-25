package org.example.serverautoescuela.dao.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioEntity {
    private String dni;
    private String passHash;
    private String tipoUsuario;
}
