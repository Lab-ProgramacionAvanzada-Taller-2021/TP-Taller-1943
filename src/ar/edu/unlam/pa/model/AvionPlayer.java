package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.model.estadosAvion.AvionNormal;
import ar.edu.unlam.pa.servicios.MovimientoPlayer;

public class AvionPlayer extends Avion implements MovimientoPlayer {
	private EstadoAvion estado = new AvionNormal();
	private static double RADIO_COLISION = 1;
	private static double VIDA_MAXIMA = 100;
	private static double VELOCIDAD_MOVIMIENTO = 1;
	
	public AvionPlayer(Punto2D posicion) {
		super(new Hitbox(posicion, RADIO_COLISION), Elemento.AMERICANO, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO);
	}
	
	public void disparar() {
		estado.disparar();
	}
	
	public void agarraPowerUP() {
		estado = estado.agarraPowerUp();
	}
	
	private void pierdePowerUP() {
		estado = estado.terminaPowerUp();
	}
	
	public void decrementarContadorPowerUP() {
		if(!estado.tienePowerUp())
			this.pierdePowerUP();
	}
	
	public String toStringEstado() {
		return estado.toString();
	}

	public boolean moverArriba() {
		moverEnDireccion(90);
		return true;
	}

	public boolean moverAbajo() {
		moverEnDireccion(270);
		return true;
	}

	public boolean moverDerecha() {
		moverEnDireccion(0);
		return true;
	}

	public boolean moverIzquierda() {
		moverEnDireccion(180);
		return true;
	}

	public boolean moverArribaDerecha() {
		moverEnDireccion(45);
		return true;
	}

	public boolean moverArribaIzquierda() {
		moverEnDireccion(135);
		return true;
	}

	public boolean moverAbajoDerecha() {
		moverEnDireccion(315);
		return true;
	}

	public boolean moverAbajoIzquierda() {
		moverEnDireccion(225);
		return true;
	}

}
