package com.example.bibliotecap.controllers;

import com.example.bibliotecap.dtos.RecursoDtos;
import com.example.bibliotecap.services.ServicioRecurso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recursos")
public class RecursoController {
    Logger logger = LoggerFactory.getLogger(RecursoController.class);
    private ServicioRecurso servicioRecurso;

    @Autowired
    public RecursoController(ServicioRecurso servicioRecurso){
        this.servicioRecurso=servicioRecurso;
    }

    @PostMapping(value = "/agregar")
    public ResponseEntity<RecursoDtos> add(@RequestBody RecursoDtos recursoDtos){
        return ResponseEntity.accepted().body(servicioRecurso.guardar(recursoDtos));
        //return new ResponseEntity(servicioRecurso.guardar(recursoDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<RecursoDtos> findAll(){
        return new ResponseEntity(servicioRecurso.obtenerTodos(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecursoDtos> edit(@RequestBody RecursoDtos recursoDTO){
        if (!recursoDTO.getId().isEmpty()){
            return new ResponseEntity(servicioRecurso.actualizar(recursoDTO), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecursoDtos> delete(@PathVariable("id") String id){
        try {
            servicioRecurso.eliminar(id);
            return  new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("ocurrio un error: "+e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity availability(@PathVariable("id") String id){
        return new ResponseEntity(servicioRecurso.verificarDisponibilidad(id), HttpStatus.OK);
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity lend(@PathVariable("id") String id){
        return  new ResponseEntity(servicioRecurso.prestar(id), HttpStatus.OK);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity returnResource(@PathVariable("id") String id){
        return  new ResponseEntity(servicioRecurso.regresarRecurso(id),HttpStatus.OK);
    }

}
