package ar.edu.unlam.pa.model;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MovimientoAvionPlayerTest {

	AvionPlayer e1;

	@Before
	public void setUp() {
		e1 = new AvionPlayer(new Punto2D(0, 0));
	}

	@Test
	public void mueveHaciaArriba() {

		e1.moverArriba();

		assertEquals(new Punto2D(0, 1), e1.getPosicion());
	}

	@Test
	public void mueveHaciaAbajo() {

		e1.moverAbajo();

		assertEquals(new Punto2D(0, -1), e1.getPosicion());
	}

	@Test
	public void mueveHaciaDerecha() {

		e1.moverDerecha();

		assertEquals(new Punto2D(1, 0), e1.getPosicion());
	}

	@Test
	public void mueveHaciaIzquierda() {

		e1.moverIzquierda();

		assertEquals(new Punto2D(-1, 0), e1.getPosicion());
	}

	@Test
	public void mueveHaciaArribaIzquierda() {

		e1.moverArribaIzquierda();

		assertEquals(new Punto2D(-sqrt(0.5), sqrt(0.5)), e1.getPosicion());
	}

	@After
	public void annihilate() {
		e1 = null;
	}

}
