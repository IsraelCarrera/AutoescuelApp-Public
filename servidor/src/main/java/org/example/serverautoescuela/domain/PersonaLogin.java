package org.example.serverautoescuela.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaLogin {
    private String dni;
    private String pass;
}
