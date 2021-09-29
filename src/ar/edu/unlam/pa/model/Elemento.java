package ar.edu.unlam.pa.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import ar.edu.unlam.pa.servicios.Movimiento;

public class Elemento implements Movimiento {

	protected Hitbox hitbox;
	private int bando;
	protected double velocidad;

	public static final int AMERICANO = 1;
	public static final int JAPONES = 2;

	public Elemento(Hitbox hitbox, int bando, double velocidad) {
		this.hitbox = hitbox;
		this.bando = bando;
		this.velocidad = velocidad;
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

		getPosicion().setY(hitbox.getPosicion().getY() + sin(anguloRadianes) * velocidad);
		getPosicion().setX(hitbox.getPosicion().getX() + cos(anguloRadianes) * velocidad);

		return true;
	}

}
