package ar.edu.unlam.pa.model;

import java.awt.Color;
import java.awt.Graphics2D;


public class Avion extends Elemento{
	private double vidaMaxima;
	private double vidaActual;
	
	public Avion(Hitbox hitbox, int bando, double vidaMaxima, double velocidad, String ruta) {
		super(hitbox, bando, velocidad, ruta);
		
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
	
	public double getVidaActual() {
		return this.vidaActual;
	}
	
	public double getVidaMaxima() {
		return this.vidaMaxima;
	}
}
