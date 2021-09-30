package ar.edu.unlam.pa.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import ar.edu.unlam.pa.servicios.Movimiento;

/*
 * Esta clase emula cualquier objeto que se este en mapa del juego y que puede interactuar con otros.
 * */
public class Elemento implements Movimiento {

	protected Hitbox hitbox;
	private int bando;
	protected double velocidad;

	public static final int AMERICANO = 1;
	public static final int JAPONES = 2;
	public static final int VELOCIDAD_UNO = 1;

	public Elemento(Hitbox hitbox, int bando, double velocidad) {
		this.hitbox = hitbox;
		this.bando = bando;
		// En futuro se multiplicara con la velocidad dependiendo de cada avion
		this.velocidad = VELOCIDAD_UNO;
	}

	public Punto2D getPosicion() {
		return hitbox.getPosicion();
	}

	private boolean esEnemigo(Elemento otro) {
		return bando != otro.bando;
	}

	public boolean colisionaCon(Elemento otro) {
		if (esEnemigo(otro) == true)
			return hitbox.colisionaCon(otro.hitbox);
		else
			return false;
	}

	@Override
	public String toString() {
		return "Posicion= " + hitbox + ", bando=" + bando + "]";
	}

	@Override
	public boolean moverEnDireccion(double angulo) {

		Double anguloRadianes = toRadians(angulo);

		double desplazamientoY = sin(anguloRadianes);
		double desplazamientoX = cos(anguloRadianes);

		hitbox.moverPunto(desplazamientoY, desplazamientoX);

		return true;
	}

}
