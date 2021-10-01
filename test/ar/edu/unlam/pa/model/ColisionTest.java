package ar.edu.unlam.pa.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public class ColisionTest {
//	Serian cualquiel personaje del juego 
	Elemento e1;
	Elemento e2;

	@Test
	public void colisionExitosa() {
		e1 = new Elemento(new Hitbox(new Punto2D(0, 0), 1), Elemento.AMERICANO, 0);
		e2 = new Elemento(new Hitbox(new Punto2D(1, 1), 1), Elemento.JAPONES, 0);
		assertTrue(e1.colisionaCon(e2));
	}

	@Test
	public void colisionFallidaPorEstarDistanciaAjana() {
		e1 = new Elemento(new Hitbox(new Punto2D(3, 3), 1), Elemento.AMERICANO, 0);
		e2 = new Elemento(new Hitbox(new Punto2D(1, 1), 1), Elemento.JAPONES, 0);
		assertFalse(e1.colisionaCon(e2));
	}

	@Test
	public void colisionFallidaPorSerDelMismoBando() {
		e1 = new Elemento(new Hitbox(new Punto2D(0, 0), 1), Elemento.AMERICANO, 0);
		e2 = new Elemento(new Hitbox(new Punto2D(1, 1), 1), Elemento.AMERICANO, 0);
		assertFalse(e1.colisionaCon(e2));
	}

	@After
	public void annihilate() {
		e1 = null;
		e2 = null;
	}

}
