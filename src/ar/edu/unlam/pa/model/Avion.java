package ar.edu.unlam.pa.model;

public class Avion extends Elemento{
	protected double vidaMaxima;
	protected double vidaActual;
	
	public Avion(Hitbox hitbox, BANDO bando, double vidaMaxima, double velocidad, String ruta) {
		super(hitbox, bando, velocidad, ruta);
		
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
}
