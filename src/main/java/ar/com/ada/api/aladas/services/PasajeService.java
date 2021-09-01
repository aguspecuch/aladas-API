package ar.com.ada.api.aladas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.repos.PasajeRepository;

@Service
public class PasajeService {

    @Autowired
    PasajeRepository repo;

    @Autowired
    ReservaService reservaService;

    @Autowired
    VueloService vueloService;

    public Pasaje emitir(Integer reservaId) {

        Reserva reserva = reservaService.traerById(reservaId);

        Pasaje pasaje = new Pasaje();
        pasaje.setFechaEmision(new Date());
        pasaje.setReserva(reserva);

        reserva.getVuelo().actualizarCapacidad();
        vueloService.actualizar(reserva.getVuelo());

        repo.save(pasaje);

        return pasaje;
    }
}
