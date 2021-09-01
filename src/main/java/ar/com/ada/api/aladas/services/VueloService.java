package ar.com.ada.api.aladas.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.repos.VueloRepository;

@Service
public class VueloService {

    @Autowired
    VueloRepository repo;

    @Autowired
    AeropuertoService aeropuertoService;

    public void crear(Vuelo vuelo) {

        vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
        repo.save(vuelo);

    }

    public Vuelo buscarById(Integer id) {

        return repo.findByVueloId(id);

    }

    public void actualizar(Vuelo vuelo) {

        repo.save(vuelo);
        
    }

    public List<Vuelo> traerVuelos() {
        return repo.findAll();
    }

    // Faltaria la validacion de codigo moneda.
    public enum ValidacionVueloDataEnum {
        OK, ERROR_PRECIO, ERROR_AEROPUERTO_ORIGEN, ERROR_AEROPUERTO_DESTINO, ERROR_FECHA, ERROR_MONEDA,
        ERROR_CAPACIDAD_MINIMA, ERROR_CAPACIDAD_MAXIMA, ERROR_AEROPUERTOS_IGUALES, ERROR_GENERAL,
    }

    public ValidacionVueloDataEnum validarVuelo(Vuelo vuelo) {

        if (!validarPrecio(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_PRECIO;
        }
        if (validarAeropuertos(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES;
        }
        if (!validarAeropuertoOrigen(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_AEROPUERTO_ORIGEN;
        }
        if (!validarAeropuertoDestino(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_AEROPUERTO_DESTINO;
        }
        if (!validarFecha(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_FECHA;
        }
        if (!validarCapacidadMinima(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_CAPACIDAD_MINIMA;
        }
        if (!validarCapacidadMaxima(vuelo)) {
            return ValidacionVueloDataEnum.ERROR_CAPACIDAD_MAXIMA;
        }
        return ValidacionVueloDataEnum.OK;
    }

    public boolean validarPrecio(Vuelo vuelo) {

        if (vuelo.getPrecio() == null) {
            return false;
        }

        if (vuelo.getPrecio().doubleValue() > 0) {
            return true;
        }

        return false;
    }

    public boolean validarAeropuertos(Vuelo vuelo) {

        return vuelo.getAeropuertoOrigen().equals(vuelo.getAeropuertoDestino());

    }

    public boolean validarAeropuertoOrigen(Vuelo vuelo) {

        Aeropuerto aeropuertoOrigen = aeropuertoService.buscarById(vuelo.getAeropuertoOrigen());

        if (aeropuertoOrigen == null) {
            return false;
        }
        return true;
    }

    public boolean validarAeropuertoDestino(Vuelo vuelo) {

        Aeropuerto aeropuertoDestino = aeropuertoService.buscarById(vuelo.getAeropuertoDestino());

        if (aeropuertoDestino == null) {
            return false;
        }
        return true;
    }

    public boolean validarFecha(Vuelo vuelo) {

        Date fechaActual = new Date();

        if (vuelo.getFecha().before(fechaActual)) {
            return false;
        }
        return true;
    }

    public boolean validarCapacidadMinima(Vuelo vuelo) {

        if (vuelo.getCapacidad() < 1) {
            return false;
        }
        return true;
    }

    public boolean validarCapacidadMaxima(Vuelo vuelo) {

        if (vuelo.getCapacidad() > 600) {
            return false;
        }
        return true;
    }

}
