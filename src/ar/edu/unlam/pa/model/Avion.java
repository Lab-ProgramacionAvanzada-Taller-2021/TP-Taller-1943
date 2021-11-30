package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Avion extends Elemento{
	protected double vidaMaxima;
	protected double vidaActual;
	
	public Avion(Hitbox hitbox, 
			DIRECCION direccion, 
			BANDO bando, 
			double vidaMaxima, 
			double velocidad, 
			String imagenImagen) {
		super(hitbox, direccion, bando, velocidad, imagenImagen);
		
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
	
	public void disparar(double dt) {}
}
