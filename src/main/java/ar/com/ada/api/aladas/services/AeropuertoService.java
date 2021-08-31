package ar.com.ada.api.aladas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.repos.AeropuertoRepository;

@Service
public class AeropuertoService {

    @Autowired
    AeropuertoRepository repo;

    public void crear(Aeropuerto aeropuerto) {
        repo.save(aeropuerto);
    }

    public enum ValidacionAeropuertoDataEnum {
        OK, ERROR_AEROPUERTO_YA_EXISTE, ERROR_CODIGO_IATA, ERROR_CODIGO_IATA_YA_REGISTRADO;
    }

    public boolean validarCodigoIATA(Aeropuerto aeropuerto) {

        if (aeropuerto.getCodigoIATA().length() != 3) {
            return false;
        }

        for (int i = 0; i < aeropuerto.getCodigoIATA().length(); i++) {
            
            char c = aeropuerto.getCodigoIATA().charAt(i);

            if (!(c >= 'A' && c <= 'Z'))
                return false;
        }

        return true;

    }

    public boolean validarExiste(Aeropuerto aeropuerto) {

        if(repo.existsById(aeropuerto.getId())) {
            return false;
        }

        return true;

    }

    public boolean validarCodigoIATAExiste(Aeropuerto aeropuerto) {
        
        if (repo.findByCodigoIATA(aeropuerto.getCodigoIATA()) != null) {
            return false;
        }

        return true;
    }

    public ValidacionAeropuertoDataEnum validarAeropuerto(Aeropuerto aeropuerto) {

        if(!validarExiste(aeropuerto)) {
            return ValidacionAeropuertoDataEnum.ERROR_AEROPUERTO_YA_EXISTE;
        }

        if(!validarCodigoIATA(aeropuerto)) {
            return ValidacionAeropuertoDataEnum.ERROR_CODIGO_IATA;
        }

        if (!validarCodigoIATAExiste(aeropuerto)) {
            return ValidacionAeropuertoDataEnum.ERROR_CODIGO_IATA_YA_REGISTRADO;
        }

        return ValidacionAeropuertoDataEnum.OK;
    }
}
