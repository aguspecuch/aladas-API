package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.AeropuertoService.ValidacionAeropuertoDataEnum;

@RestController
public class AeropuertoController {

    @Autowired
    AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Aeropuerto aeropuerto) {

        GenericResponse r = new GenericResponse();
        ValidacionAeropuertoDataEnum validacion = service.validarAeropuerto(aeropuerto);

        if (validacion == ValidacionAeropuertoDataEnum.OK) {
            service.crear(aeropuerto);

            r.isOk = true;
            r.id = aeropuerto.getAeropuertoId();
            r.message = "Aeropuerto creado con exito.";
    
            return ResponseEntity.ok(r);

        } else {

            r.isOk = false;
            r.message = "Error(" + validacion.toString() + ")";

            return ResponseEntity.badRequest().body(r);
        }

    }

    

}
