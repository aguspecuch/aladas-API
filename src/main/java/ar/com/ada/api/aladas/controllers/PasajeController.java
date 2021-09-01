package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.models.request.PasajeNuevoRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.PasajeService;

@RestController
public class PasajeController {
    
    @Autowired
    PasajeService service;

    @PostMapping("/api/pasajes")
    public ResponseEntity<GenericResponse> emitir(@RequestBody PasajeNuevoRequest pasajeNuevo) {

        Pasaje pasaje = service.emitir(pasajeNuevo.reservaId);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = pasaje.getPasajeId();
        r.message = "Pasaje emitido con exito.";

        return ResponseEntity.ok(r);

    }
}
