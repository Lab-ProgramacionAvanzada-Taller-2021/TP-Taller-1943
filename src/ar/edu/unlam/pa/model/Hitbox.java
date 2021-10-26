package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

/*
 * Esta clase representa a las  cajas delimitadoras o cajas de colisi�n entre los objetos del juego.
 * */
public class Hitbox {
	private Punto2D posicion;
	private double radio;
	
	public Hitbox(double x, double y, double radio) {
		this.posicion = new Punto2D(x, y);
		this.radio = radio;
	}

	public Hitbox(Punto2D posicion, double radio) {
		this.posicion = posicion;
		this.radio = radio;
	}

	public Hitbox(Hitbox otro) {
		this.posicion = otro.posicion;
		this.radio = otro.radio;
	}
	
	public double getX() {
		return this.posicion.getX();
	}
	
	public double getY() {
		return this.posicion.getY();
	}

	public Punto2D getPosicion() {
		return posicion;
	}
	
	public double getRadio() {
		return radio;
	}
	
	/**
	 * @param radio_de_otro_hitbox.
	 * @return suma_de_radios.
	 */
	public double sumaDeRadios(Double radio) {
		return this.radio + radio;
	}

	/***
	 * @param Hitbox_de_otro_elemento.
	 * @return Si_colisionan_o_no.
	 */
	public boolean colisionaCon(Hitbox otro) {
		return this.sumaDeRadios(otro.radio) >= this.posicion.distancia(otro.posicion);
	}

	@Override
	public String toString() {
		return posicion + ", r=" + radio;
	}

	public void moverPunto(Double desplazamientoX, Double desplazamientoY) {
		if(!this.posicion.esPosicionDesbordada(this.radio-desplazamientoX, Ventana.ANCHO-this.radio-desplazamientoX, 
				this.radio-desplazamientoY, Ventana.ALTO-this.radio-desplazamientoY))
			this.posicion.desplazar(desplazamientoX, desplazamientoY);
	}

}
