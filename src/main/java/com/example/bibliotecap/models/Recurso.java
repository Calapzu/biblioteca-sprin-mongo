package com.example.bibliotecap.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


@Document
public class Recurso {

    @Id
    private String id;
    private String nombreRecurso;
    private String tematicaRecurso;
    private String tipoRecurso;
    private LocalDate fechaPrestamo;
    private Integer cantidadDisponible;
    private Integer cantidadPrestada;

}
