package ar.edu.unlam.pa.model;

import static java.lang.Math.pow;

/*
 * Esta clase representa a las  cajas delimitadoras o cajas de colisi�n entre los objetos del juego.
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
	
	public double sumarRadios(Double radio) {
		return this.radio + radio;
	}

	/***
	 * 
	 * @param Hitbox_de_otro_elemento.
	 * @return Si_colisionan_o_no.
	 */
	public boolean colisionaCon(Hitbox otro) {
		return this.sumarRadios(otro.radio) >= this.posicion.distancia(otro.posicion);
	}

	@Override
	public String toString() {
		return posicion + ", r=" + radio;
	}

	public void moverPunto(Double desplazamientoY, Double desplazamientoX) {
		this.posicion.desplazar(desplazamientoY, desplazamientoX);
	}

}
