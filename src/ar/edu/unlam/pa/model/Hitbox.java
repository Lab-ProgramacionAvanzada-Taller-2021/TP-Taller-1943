package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

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
	
	public double getExtremoIzq() {
		return this.posicion.getX() - this.radio;
	}
	
	public double getExtremoDer() {
		return this.posicion.getX() + this.radio;
	}
	
	public double getExtremoSup() {
		return this.posicion.getY() - this.radio;
	}
	
	public double getExtremoInf() {
		return this.posicion.getY() + this.radio;
	}
	
	public double getDiametro() {
		return this.radio * 2;
	}

	public Punto2D getPosicion() {
		return new Punto2D(posicion);
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
	
	public boolean puedeMover(double desplazamientoX, double desplazamientoY) {
		return !(this.getExtremoIzq()+desplazamientoX < 0 || 
				this.getExtremoDer()+desplazamientoX > Ventana.ANCHO ||
				this.getExtremoSup()+desplazamientoY < 0 || 
				this.getExtremoInf()+desplazamientoY > Ventana.ALTO);
	}

	public void moverPunto(double desplazamientoX, double desplazamientoY) {
		this.posicion.desplazar(desplazamientoX, desplazamientoY);
	}
	

}
