package com.example.bibliotecap.mappers;

import com.example.bibliotecap.dtos.RecursoDtos;
import com.example.bibliotecap.models.Recurso;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {
    private ModelMapper mapper;

    public RecursoMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public RecursoDtos convertToDTO(Recurso recurso){
        RecursoDtos recursoDTO = mapper.map(recurso, RecursoDtos.class);
        return recursoDTO;
    }

    public Recurso convertToDocument(RecursoDtos recursoDTO){
        Recurso recurso= mapper.map(recursoDTO, Recurso.class);
        return recurso;
    }
}
