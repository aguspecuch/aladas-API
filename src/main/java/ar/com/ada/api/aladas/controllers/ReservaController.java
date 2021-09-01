package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.models.request.ReservaNuevaRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.ReservaService;

@RestController
public class ReservaController {
    
    @Autowired
    ReservaService service;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> generar(@RequestBody ReservaNuevaRequest reservaNueva) {
        
        Reserva reserva = service.generar(reservaNueva.vueloId, pasajeroId);
        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = reserva.getReservaId();
        r.message = "Reserva generada con exito.";

        return ResponseEntity.ok(r);

    }
}
