package com.example.bibliotecap.services;

import com.example.bibliotecap.dtos.RecursoDtos;
import com.example.bibliotecap.mappers.RecursoMapper;
import com.example.bibliotecap.models.Recurso;
import com.example.bibliotecap.repository.RepositorioRecurso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServicioRecursoTest {

    @MockBean
    private RepositorioRecurso repository;
    @Autowired
    private ServicioRecurso servicioRecurso;
    @Autowired
    private RecursoMapper recursoMapper;


    @Test
    @DisplayName("traer todos los recursos")
    void getTodos() {
        var primerRecurso = new Recurso();

        Mockito.when(repository.findAll()).thenReturn(recursos());
        var result = servicioRecurso.obtenerTodos();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Y10", result.get(0).getId());
        Assertions.assertEquals("El coronel", result.get(0).getNombreRecurso());
        Assertions.assertEquals(2, result.get(0).getCantidadDisponible());
        Assertions.assertEquals(null, result.get(0).getFechaPrestamo());
        Assertions.assertEquals(0, result.get(0).getCantidadPrestada());
        Assertions.assertEquals("Libro", result.get(0).getTipoRecurso());
        Assertions.assertEquals("terror",result.get(0).getTematicaRecurso());
        Assertions.assertEquals("C20", result.get(1).getId());
        Assertions.assertEquals("Simon bobito", result.get(1).getNombreRecurso());
        Assertions.assertEquals(5, result.get(1).getCantidadDisponible());
        Assertions.assertEquals(LocalDate.now(),result.get(1).getFechaPrestamo());
        Assertions.assertEquals(1, result.get(1).getCantidadPrestada());
        Assertions.assertEquals("Libro", result.get(1).getTipoRecurso());
        Assertions.assertEquals("comedia",result.get(1).getTematicaRecurso());
    }

    private List<Recurso> recursos() {

        var primerRecurso = new Recurso();
        primerRecurso.setId("Y10");
        primerRecurso.setNombreRecurso("El coronel");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(null);
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");
        var segundoRecurso = new Recurso();
        segundoRecurso.setId("C20");
        segundoRecurso.setNombreRecurso("Simon bobito");
        segundoRecurso.setCantidadDisponible(5);
        segundoRecurso.setFechaPrestamo(LocalDate.now());
        segundoRecurso.setCantidadPrestada(1);
        segundoRecurso.setTipoRecurso("Libro");
        segundoRecurso.setTematicaRecurso("comedia");
        var recursos = new ArrayList<Recurso>();
        recursos.add(primerRecurso);
        recursos.add(segundoRecurso);
        return recursos;
    }


    @Test
    @DisplayName("Test para crear recurso")
    void guardar(){
        var primerRecurso=new RecursoDtos();
        primerRecurso.setNombreRecurso("El coronel");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(null);
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");

        Mockito.when(repository.save(Mockito.any())).thenReturn(recursos().get(0));

        var result = servicioRecurso.guardar(primerRecurso);

        Assertions.assertNotNull(result, "no puede ser nulo");
        Assertions.assertEquals("El coronel", result.getNombreRecurso(), "Debe ser igual el nombre");
        Assertions.assertEquals(2, result.getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(null, result.getFechaPrestamo(), "La fecha debe ser nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "La cantidad prestada no corresponde");
        Assertions.assertEquals("Libro", result.getTipoRecurso(), "El tipo de recurso debe ser igual");
        Assertions.assertEquals("terror", result.getTematicaRecurso(), "La tematica debe ser igual");
    }


    @Test
    @DisplayName("buscar recurso por id")
    void obtenerPorId(){
        Mockito.when(repository.findById(Mockito.any())).thenReturn(recursos().stream().findAny().stream().findFirst());

        var result = servicioRecurso.obtenerPorId(recursos().get(0).getId());
        Assertions.assertEquals(recursos().get(0).getId(),result.getId(), "El id no corresponde");
        Assertions.assertEquals("El coronel", result.getNombreRecurso(), "Debe ser igual el nombre");
        Assertions.assertEquals(2, result.getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(null, result.getFechaPrestamo(), "La fecha debe ser nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "La cantidad prestada no corresponde");
        Assertions.assertEquals("Libro", result.getTipoRecurso(), "El tipo de recurso debe ser igual");
        Assertions.assertEquals("terror", result.getTematicaRecurso(), "La tematica debe ser igual");
    }

    @Test
    @DisplayName("Editar un recurso")
    void actualizar(){
        var primerRecurso=new RecursoDtos();
        primerRecurso.setId("Y10");
        primerRecurso.setNombreRecurso("El coronel");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(LocalDate.now());
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");

        Mockito.when(repository.save(Mockito.any())).thenReturn(recursoMapper.convertToDocument(primerRecurso));
        Mockito.when(repository.findById(primerRecurso.getId())).thenReturn(recursos().stream().findFirst());
        var result =servicioRecurso.actualizar(primerRecurso);

        Assertions.assertNotNull(result, "Debe ingresar dato");

        Assertions.assertEquals("Y10", result.getId(),"El id no corresponde");
        Assertions.assertEquals("El coronel", result.getNombreRecurso(), "Debe ser igual el nombre");
        Assertions.assertEquals(2, result.getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(LocalDate.now(), result.getFechaPrestamo(), "La fecha debe ser nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "La cantidad prestada no corresponde");
        Assertions.assertEquals("Libro", result.getTipoRecurso(), "El tipo de recurso debe ser igual");
        Assertions.assertEquals("terror", result.getTematicaRecurso(), "La tematica debe ser igual");
    }


    @Test
    @DisplayName("Disponibilidad de recurso")
    void cerificarDisponibilidadRecurso(){
        var primerRecurso=new RecursoDtos();
        primerRecurso.setId("Y20");
        primerRecurso.setNombreRecurso("El coronel");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(LocalDate.now());
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");

        Mockito.when(repository.findById(primerRecurso.getId())).thenReturn(recursos().stream().findFirst());

        var result = servicioRecurso.verificarDisponibilidad(primerRecurso.getId());

        Assertions.assertEquals("El recurso " + recursos().stream().findFirst().get().getNombreRecurso() + "esta disponible y hay " + (recursos().stream().findFirst().get().getCantidadDisponible() - recursos().stream().findFirst().get().getCantidadPrestada()) + " unidades disponibles", result);
    }


    @Test
    @DisplayName("prestar de recurso")
    void prestarRecurso(){
        var primerRecurso=new RecursoDtos();
        primerRecurso.setId("Y10");
        primerRecurso.setNombreRecurso("El coronel");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(LocalDate.now());
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");
        Mockito.when(repository.findById(primerRecurso.getId())).thenReturn(recursos().stream().findFirst());
        Mockito.when(repository.save(Mockito.any())).thenReturn(recursoMapper.convertToDocument(primerRecurso));

        var result = servicioRecurso.prestar(primerRecurso.getId());

        Assertions.assertEquals("El recurso " + primerRecurso.getNombreRecurso() + " se ha prestado", result);
    }




}
