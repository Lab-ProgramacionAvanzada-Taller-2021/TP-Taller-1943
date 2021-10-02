package ar.edu.unlam.pa.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MovimientoAvionEnemigoTest {

	AvionVerdeChiquito a1;

	@Before
	public void setUp() {
		a1 = new AvionVerdeChiquito(new Punto2D(0, 0));
	}

	@Test
	public void mueveHaciaDerecha() {
		// hacia la derecha equivale a un angulo de 0 grados
		a1.moverEnDireccion(0);
		// Cada avion sabe cuanto se debe mover estan como estaticos en cada clase
		assertEquals(new Punto2D(1, 0), a1.getPosicion());
	}

	@Test
	public void mueveHaciaArriba() {
		// hacia arriba equivale a un angulo de 90 grados
		a1.moverEnDireccion(90);

		assertEquals(new Punto2D(0, 1), a1.getPosicion());
	}

	@Test
	public void mueveHaciaIzquierda() {
		// hacia arriba equivale a un angulo de 180 grados
		a1.moverEnDireccion(180);

		assertEquals(new Punto2D(-1, 0), a1.getPosicion());
	}

	@Test
	public void mueveHaciaAbajo() {
		// hacia arriba equivale a un angulo de 270 grados
		a1.moverEnDireccion(270);

		assertEquals(new Punto2D(0, -1), a1.getPosicion());
	}

	@Test
	public void mueveHaciaArribaDerecha() {
		// hacia arriba-derecha equivale a un angulo de 45 grados
		a1.moverEnDireccion(45);

		assertEquals(new Punto2D(Math.cos(Math.toRadians(45)), Math.sin(Math.toRadians(45))), a1.getPosicion());
	}

	@After
	public void annihilate() {
		a1 = null;
	}

}
