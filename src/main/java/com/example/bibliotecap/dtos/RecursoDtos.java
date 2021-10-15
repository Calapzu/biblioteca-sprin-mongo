package com.example.bibliotecap.dtos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class RecursoDtos {
    private String id;
    private String nombreRecurso;
    private String tematicaRecurso;
    private String tipoRecurso;
    private LocalDate fechaPrestamo;
    private Integer cantidadDisponible;
    private Integer cantidadPrestada;

}
