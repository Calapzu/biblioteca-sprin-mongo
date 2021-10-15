package com.example.bibliotecap.services;

import com.example.bibliotecap.dtos.RecursoDtos;
import com.example.bibliotecap.mappers.RecursoMapper;
import com.example.bibliotecap.models.Recurso;
import com.example.bibliotecap.repository.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServicioRecurso {

    private RepositorioRecurso repository;
    private RecursoMapper mapper;

    @Autowired
    public ServicioRecurso(RepositorioRecurso repository, RecursoMapper recursoMapper) {
        this.repository = repository;
        this.mapper = recursoMapper;
    }

    public List<RecursoDtos> obtenerTodos(){
        List<RecursoDtos> recursoDtos = new ArrayList<>();
        repository.findAll().forEach(recurso -> recursoDtos.add(mapper.convertToDTO(recurso)));
        return recursoDtos;
    }

    public RecursoDtos guardar(RecursoDtos recursoDtos){
        if (recursoDtos.getNombreRecurso().isEmpty()){
            throw new IllegalArgumentException("El nombre no debe estar vacio");
        }
        Recurso recurso = mapper.convertToDocument(recursoDtos);
        return  mapper.convertToDTO(repository.save(recurso));
    }

    public RecursoDtos obtenerPorId(String id){
        Optional<Recurso> recurso = repository.findById(id);
        if (recurso.isEmpty()){
            throw  new NoSuchElementException("No existe un recurso con el id: "+id);
        }
        return  mapper.convertToDTO(recurso.get());
    }

    public void eliminar(String id){
        repository.delete(mapper.convertToDocument(obtenerPorId(id)));
    }

    public RecursoDtos actualizar(RecursoDtos recursoDtos){
        Recurso recurso = mapper.convertToDocument(recursoDtos);
        obtenerPorId(recurso.getId());
        return mapper.convertToDTO(repository.save(recurso));
    }

    public String verificarDisponibilidad(String id){
        RecursoDtos dtos = obtenerPorId(id);
        if(estaDisponible(dtos)){
            return "El recurso "+dtos.getNombreRecurso() + "esta disponible y hay " +
                    (dtos.getCantidadDisponible() - dtos.getCantidadPrestada()) + " unidades disponibles";
        }

        return "El recurso " + dtos.getNombreRecurso() + "no esta disponible y se presto " + dtos.getCantidadPrestada();
    }

    private  boolean estaDisponible(RecursoDtos recursoDtos){
        return recursoDtos.getCantidadDisponible() > recursoDtos.getCantidadPrestada();
    }

    public String prestar(String id){
        RecursoDtos dtos = obtenerPorId(id);
        if (estaDisponible(dtos)){
            dtos.setCantidadPrestada(dtos.getCantidadPrestada()+1);
            dtos.setFechaPrestamo(LocalDate.now());
            actualizar(dtos);
            return  "El recurso " + dtos.getNombreRecurso() + " se ha prestado";
        }
        return "El recurso " + dtos.getNombreRecurso() + "no tiene unidades disponibles";
    }

    public List<RecursoDtos> recomendarPorTema(String tema){
        List<RecursoDtos> recursoDtos = new ArrayList<>();
        repository.findByTematicaRecurso(tema).forEach(recurso -> recursoDtos.add(mapper.convertToDTO(recurso)));
        return recursoDtos;
    }

    public List<RecursoDtos> recomendarPorTipo(String tipo){
        List<RecursoDtos> recursoDtos = new ArrayList<>();
        repository.findByTipoRecurso(tipo).forEach(recurso -> recursoDtos.add(mapper.convertToDTO(recurso)));
        return recursoDtos;
    }

    public List<RecursoDtos> recomendarPorTemaYTipo(String tema, String tipo){
        List<RecursoDtos> recursoDtos = new ArrayList<>();
        List<RecursoDtos> recursoDtos1 = new ArrayList<>();

        recursoDtos1.addAll(recomendarPorTipo(tipo));
        recursoDtos1.addAll(recomendarPorTema(tema));
        recursoDtos1.stream().distinct().forEach(recursoDto -> recursoDtos.add(recursoDto));
        return recursoDtos;
    }

    public String regresarRecurso(String id){
        RecursoDtos dtos = obtenerPorId(id);
        if (dtos.getCantidadPrestada() > 0){
            dtos.setCantidadPrestada(dtos.getCantidadPrestada() - 1);
            actualizar(dtos);
            return "Se ha regresado el recurso " + dtos.getNombreRecurso();
        }
        return "El recurso " + dtos.getNombreRecurso() + "no se encuentra prestado";
    }

}
