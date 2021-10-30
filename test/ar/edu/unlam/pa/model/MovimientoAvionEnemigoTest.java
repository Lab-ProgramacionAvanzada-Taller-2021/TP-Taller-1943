package ar.edu.unlam.pa.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MovimientoAvionEnemigoTest {

	AvionEnemigo a1;

	//@Before
	//public void setUp() {
	//	a1 = new AvionEnemigo(0, 0);
	//}

	@Test
	public void mueveHaciaDerecha() {
		a1.moverEnDireccion(1, 0); 
		
		assertEquals(new Punto2D(1, 0), a1.getPosicion());
	}

	@Test
	public void mueveHaciaArriba() {
		a1.moverEnDireccion(0, 1);

		assertEquals(new Punto2D(0, 1), a1.getPosicion());
	}

	@Test
	public void mueveHaciaIzquierda() {
		a1.moverEnDireccion(-1, 0);
		
		assertEquals(new Punto2D(-1, 0), a1.getPosicion());
	}

	@Test
	public void mueveHaciaAbajo() {
		a1.moverEnDireccion(0, -1);

		assertEquals(new Punto2D(0, -1), a1.getPosicion());
	}

	@Test
	public void mueveHaciaArribaDerecha() {
		a1.moverEnDireccion(0.707, 0.707);

		assertEquals(new Punto2D(0.707, 0.707), a1.getPosicion());
	}

	@After
	public void annihilate() {
		a1 = null;
	}

}
