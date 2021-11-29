package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Avion extends Elemento{
	protected double vidaMaxima;
	protected double vidaActual;
	
	public Avion(Hitbox hitbox, DIRECCION direccion, BANDO bando, double vidaMaxima, double velocidad, BufferedImage[] imagen) {
		super(hitbox, direccion, bando, velocidad, imagen);
		
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
	
	public void disparar(double dt) {}
}
