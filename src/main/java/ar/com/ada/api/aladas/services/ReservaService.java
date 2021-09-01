package ar.com.ada.api.aladas.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasajero;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    ReservaRepository repo;

    @Autowired
    VueloService vueloService;

    @Autowired
    PasajeroService pasajeroService;

    public Reserva generar(Integer vueloId, Integer pasajeroId) {

        Reserva reserva = new Reserva();
        reserva.setFechaEmision(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(reserva.getFechaEmision());
        c.add(Calendar.DATE, 1);

        reserva.setFechaVencimiento(c.getTime());

        Vuelo vuelo = vueloService.buscarById(vueloId)
        reserva.setVuelo(vuelo);
        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);

        Pasajero pasajero = pasajeroService.traerById(pasajeroId)
        reserva.setPasajero(pasajero);

        pasajero.agregarReserva(reserva);
        vuelo.agregarReserva(reserva);

        repo.save(reserva);

        return reserva;
    }

    public Reserva traerById(Integer id) {
        return repo.findByReservaId(id);
    }
}
