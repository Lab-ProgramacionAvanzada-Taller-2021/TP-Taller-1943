package ar.edu.unlam.pa.model;

import static java.lang.Math.pow;

/*
 * Esta clase representa a las  cajas delimitadoras o cajas de colisión entre los objetos del juego.
 * */
public class Hitbox {

	private Punto2D posicion;
	private double radio;

	public Hitbox(Punto2D posicion, double radio) {
		this.posicion = posicion;
		this.radio = radio;
	}

	public Hitbox(Hitbox otro) {
		this.posicion = otro.posicion;
		this.radio = otro.radio;
	}

	public Punto2D getPosicion() {
		return posicion;
	}

	public boolean colisionaCon(Hitbox otro) {

		double x1 = posicion.getX();
		double y1 = posicion.getY();
		double x2 = otro.posicion.getX();
		double y2 = otro.posicion.getY();

		double sumaDeRadiosAlCuadrado = pow(radio + otro.radio, 2);
		double distanciaEntreCentrosAlCuadrado = pow(x2 - x1, 2) + pow(y2 - y1, 2);

		return sumaDeRadiosAlCuadrado >= distanciaEntreCentrosAlCuadrado;
	}

	@Override
	public String toString() {
		return posicion + ", r=" + radio;
	}

	public void moverPunto(Double desplazamientoY, Double desplazamientoX) {
		this.posicion.desplazar(desplazamientoY, desplazamientoX);
	}

}
