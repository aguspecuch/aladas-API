package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {
    
    @Autowired
    VueloService service;

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Vuelo vuelo) {
        
        GenericResponse r = new GenericResponse();
        ValidacionVueloDataEnum resultado = service.validarVuelo(vuelo);

        if (resultado == ValidacionVueloDataEnum.OK) {
            
            service.crear(vuelo);

            r.isOk = true;
            r.id = vuelo.getId();
            r.message = "Vuelo creado con exito.";

            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "Error(" + resultado.toString() + ").";

            return ResponseEntity.badRequest().body(r);
        }

        

    }
}
