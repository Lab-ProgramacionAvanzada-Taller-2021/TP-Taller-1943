package ar.edu.unlam.pa.model.estados.avion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Punto2D;

public class EstadoAvionTest {

	AvionPlayer avion;

	@Before
	public void Arrage() {
		avion = new AvionPlayer(2, 3);
	}

	@Test
	public void estadoDeCreacion() {
		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}

	@Test
	public void estadoPrimerPowerUp() {
		avion.agarraPowerUP();

		Assert.assertEquals("SuperAvion", avion.toStringEstado());
	}

	@Test
	public void estadoSegundoPowerUp() {
		avion.agarraPowerUP();
		avion.agarraPowerUP();

		Assert.assertEquals("MegaAvion", avion.toStringEstado());
	}

	@Test
	public void estadoDespuesDeLaDuracion() {
		avion.agarraPowerUP();

		for (int i = 0; i++ < 32;) {
			avion.decrementarContadorPowerUP();
		}

		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}

	@Test
	public void estadoDespuesDeLaDobleDuracion() {
		avion.agarraPowerUP();
		avion.agarraPowerUP();

		for (int i = 0; i++ < 32;) {
			avion.decrementarContadorPowerUP();
		}

		Assert.assertEquals("SuperAvion", avion.toStringEstado());

		for (int i = 0; i++ < 32;) {
			avion.decrementarContadorPowerUP();
		}

		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}
/*
	@Test
	public void disparaEnEstadoNormal() {
		avion.disparar();
		Assert.assertEquals("Bala [cantidadTiros=2]", avion.getEstado().getBala().toString());
	}

	@Test
	public void disparaEnEstadoSuper() {
		avion.agarraPowerUP();
		avion.disparar();
		Assert.assertEquals("Bala [cantidadTiros=3]", avion.getEstado().getBala().toString());
	}

	@Test
	public void disparaEnEstadoMega() {
		avion.agarraPowerUP();
		avion.agarraPowerUP();
		avion.disparar();
		Assert.assertEquals("Bala [cantidadTiros=4]", avion.getEstado().getBala().toString());
	}
*/
	@After
	public void Annihilate() {
		avion = null;
	}

}
