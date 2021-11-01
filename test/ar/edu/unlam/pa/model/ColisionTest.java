package ar.edu.unlam.pa.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public class ColisionTest {

//	Serian cualquiel personaje del juego 

	AvionPlayer player1;
	AvionPlayer player2;
	AvionEnemigo enemigo1;
	AvionEnemigo enemigo2;

	@Test
	public void colisionExitosa() {

		player1 = AvionPlayer.crearJugador1(null);
		enemigo1 = new AvionEnemigo(player1.getPosicion().getX(), player1.getPosicion().getY());
		assertTrue(player1.colisionaCon(enemigo1));
	}

	@Test
	public void colisionFallidaPorEstarDistanciaLejana() {
		player1 = AvionPlayer.crearJugador1(null);
		enemigo1 = new AvionEnemigo(1, 1);

		assertFalse(player1.colisionaCon(enemigo1));
	}

	@Test
	public void colisionFallidaPorSerDelMismoBando() {

		player1 = AvionPlayer.crearJugador1(null);
		player2 = AvionPlayer.crearJugador1(null);
		assertFalse(player1.colisionaCon(player2));
	}

	@Test
	public void colisionFallidaPorSerDelMismoBandoJapones() {
		enemigo1 = new AvionEnemigo(0, 0);
		enemigo2 = new AvionEnemigo(1, 1);
		assertFalse(enemigo1.colisionaCon(enemigo2));
	}

	@After
	public void annihilate() {
		player1 = null;
		player2 = null;
		enemigo1 = null;
		enemigo2 = null;
	}

}
