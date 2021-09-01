package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;

@SpringBootTest
class AladasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	AeropuertoService aeropuertoService;

	@Autowired
	VueloService vueloService;

	@Test
	void aeropuertoValidarCodigoIATA() {

		Aeropuerto aeropuerto1 = new Aeropuerto();
		aeropuerto1.setCodigoIATA("EZE");
		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setCodigoIATA("PAL");

		Aeropuerto aeropuerto3 = new Aeropuerto();
		aeropuerto3.setCodigoIATA("E  ");
		Aeropuerto aeropuerto4 = new Aeropuerto();
		aeropuerto4.setCodigoIATA("PA8");

		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto1));
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto2));

		assertFalse(aeropuertoService.validarCodigoIATA(aeropuerto3));
		assertFalse(aeropuertoService.validarCodigoIATA(aeropuerto4));
	}

	@Test
	void aeropuertoValidarExiste() {

		Aeropuerto aeropuerto1 = new Aeropuerto();
		aeropuerto1.setAeropuertoId(10);

		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setAeropuertoId(3);

		assertTrue(aeropuertoService.validarExiste(aeropuerto1));
		assertFalse(aeropuertoService.validarExiste(aeropuerto2));
	}

	@Test
	void vueloValidarAeropuertosIguales() {
		
		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(1000));
		vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
		vuelo.setAeropuertoOrigen(116);
		vuelo.setAeropuertoDestino(116);

		assertTrue(vueloService.validarAeropuertos(vuelo));
	}

	@Test
	void vueloValidarPrecio() {

		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(1000));
		Vuelo vuelo2 = new Vuelo();
		vuelo2.setPrecio(new BigDecimal(-1000));
		Vuelo vuelo3 = new Vuelo();
		
		assertTrue(vueloService.validarPrecio(vuelo));
		assertFalse(vueloService.validarPrecio(vuelo2));
		assertFalse(vueloService.validarPrecio(vuelo3));

	}

	@Test
	void vueloValidarCapacidad() {

		Vuelo vuelo = new Vuelo();
		vuelo.setCapacidad(50);
		Vuelo vuelo2 = new Vuelo();
		vuelo2.setCapacidad(-50);
		Vuelo vuelo3 = new Vuelo();
		vuelo3.setCapacidad(15050);

		assertTrue(vueloService.validarCapacidadMinima(vuelo));
		assertTrue(vueloService.validarCapacidadMaxima(vuelo));
		assertFalse(vueloService.validarCapacidadMinima(vuelo2));
		assertFalse(vueloService.validarCapacidadMaxima(vuelo3));


	}

}
