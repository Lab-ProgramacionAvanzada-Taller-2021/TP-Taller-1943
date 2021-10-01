package ar.edu.unlam.pa.model;

public class Avion extends Elemento {
	private double vidaMaxima;
	private double vidaActual;
	
	public Avion(Hitbox hitbox, int bando, double vidaMaxima,double velocidad) {
		super(hitbox, bando, velocidad);
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}

}
