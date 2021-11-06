package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Bala extends Elemento{
	public Bala(Punto2D posicion, BANDO bando, double velocidad, double radio, BufferedImage imagen) {
		super(new Hitbox(posicion ,radio), bando, velocidad, imagen);
	}

	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, -dt);
	}
	
	@Override
	public String toString() {
		return "Bala";
	}

}
