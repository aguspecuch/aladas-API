package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.services.AeropuertoService;

@SpringBootTest
class AladasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	AeropuertoService aeropuertoService;

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
		aeropuerto1.setId(10);

		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setId(3);

		assertTrue(aeropuertoService.validarExiste(aeropuerto1));
		assertFalse(aeropuertoService.validarExiste(aeropuerto2));
	}

}
