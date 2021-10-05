package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.model.estados.avion.AvionNormal;
import ar.edu.unlam.pa.model.estados.avion.EstadoAvion;
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
		if (!estado.tienePowerUp())
			this.pierdePowerUP();
	}

	public String toStringEstado() {
		return estado.toString();
	}

	public boolean moverArriba() {
		moverEnDireccion(0, MovimientoPlayer.DESPLAZAMIENTO_LINEAL);
		return true;
	}

	public boolean moverAbajo() {
		moverEnDireccion(0, -MovimientoPlayer.DESPLAZAMIENTO_LINEAL);
		return true;
	}

	public boolean moverDerecha() {
		moverEnDireccion(MovimientoPlayer.DESPLAZAMIENTO_LINEAL, 0);
		return true;
	}

	public boolean moverIzquierda() {
		moverEnDireccion(-MovimientoPlayer.DESPLAZAMIENTO_LINEAL, 0);
		return true;
	}

	public boolean moverArribaDerecha() {
		moverEnDireccion(MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL, MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL);
		return true;
	}

	public boolean moverArribaIzquierda() {
		moverEnDireccion(-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL, MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL);
		return true;
	}

	public boolean moverAbajoDerecha() {
		moverEnDireccion(MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL, -MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL);
		return true;
	}

	public boolean moverAbajoIzquierda() {
		moverEnDireccion(-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL, -MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL);
		return true;
	}

	public EstadoAvion getEstado() {
		return estado;
	}

}
