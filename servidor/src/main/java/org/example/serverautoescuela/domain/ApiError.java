package org.example.serverautoescuela.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {
    private String mensaje;
    private LocalDateTime fecha;
}
