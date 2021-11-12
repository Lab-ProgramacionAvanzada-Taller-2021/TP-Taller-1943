package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Avion extends Elemento{
	protected double vidaMaxima;
	protected double vidaActual;
	
	public Avion(Hitbox hitbox, BANDO bando, double vidaMaxima, double velocidad, BufferedImage imagen) {
		super(hitbox, bando, velocidad, imagen);
		
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
	
	public void disparar() {}
}
