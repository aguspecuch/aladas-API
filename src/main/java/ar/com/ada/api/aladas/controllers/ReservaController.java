package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.models.request.ReservaNuevaRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.ReservaService;
import ar.com.ada.api.aladas.services.UsuarioService;

@RestController
public class ReservaController {
    
    @Autowired
    ReservaService service;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> generar(@RequestBody ReservaNuevaRequest reservaNueva) {
        
        // Obtengo a quien esta autenticado del otro lado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());

        Reserva reserva = service.generar(reservaNueva.vueloId, usuario.getPasajero().getPasajeroId());

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = reserva.getReservaId();
        r.message = "Reserva generada con exito.";

        return ResponseEntity.ok(r);

    }
}
