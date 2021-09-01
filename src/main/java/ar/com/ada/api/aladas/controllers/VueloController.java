package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
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
            r.id = vuelo.getVueloId();
            r.message = "Vuelo creado con exito.";

            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "Error(" + resultado.toString() + ").";

            return ResponseEntity.badRequest().body(r);
        }
    }

    @GetMapping("/api/vuelos")
    public ResponseEntity<List<Vuelo>> traer() {

        return ResponseEntity.ok(service.traerVuelos());
    }

    @PutMapping("/api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> actualizarEstadoVuelo(@PathVariable Integer id,
            @RequestBody EstadoVueloRequest estadoVuelo) {

            Vuelo vuelo = service.buscarById(id);
            vuelo.setEstadoVueloId(estadoVuelo.estado);
            service.actualizar(vuelo);

            GenericResponse r = new GenericResponse();
            r.isOk = true;
            r.id = id;
            r.message = "Vuelo actualizado con exito.";

            return ResponseEntity.ok(r);
    }
}
