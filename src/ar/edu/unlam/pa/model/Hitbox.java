package ar.edu.unlam.pa.model;

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
	
	/**
	 * 
	 * @param radio_de_otro_hitbox.
	 * @return suma_de_radios.
	 */
	public double sumaDeRadios(Double radio) {
		return this.radio + radio;
	}

	/***
	 * 
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
		this.posicion.desplazar(desplazamientoX, desplazamientoY);
	}

}
