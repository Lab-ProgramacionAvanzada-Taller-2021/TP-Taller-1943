package ar.edu.unlam.pa.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MovimientoAvionPlayerTest {

	AvionPlayer e1;

	@Before
	public void setUp() {
		e1 = new AvionPlayer(null, 1, 1, 300.00, 300.00, "jugador1");
	}

	@Test
	public void mueveHaciaArriba() {

		e1.moverArriba(1.0);

		assertEquals(new Punto2D(300, 120), e1.getPosicion());
	}

	@Test
	public void mueveHaciaAbajo() {

		e1.moverAbajo(1.0);

		assertEquals(new Punto2D(300, 480), e1.getPosicion());
	}

	@Test
	public void mueveHaciaDerecha() {

		e1.moverDerecha(1.0);

		assertEquals(new Punto2D(480, 300), e1.getPosicion());
	}

	@Test
	public void mueveHaciaIzquierda() {

		e1.moverIzquierda(1.0);

		assertEquals(new Punto2D(120, 300), e1.getPosicion());
	}

	@Test
	public void mueveHaciaArribaIzquierda() {

		e1.moverArribaIzquierda(1.0);

		assertEquals(new Punto2D(172.74, 172.74), e1.getPosicion());
	}

	@After
	public void annihilate() {
		e1 = null;
	}

}
