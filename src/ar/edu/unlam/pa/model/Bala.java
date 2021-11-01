package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class Bala extends Elemento{
	private static double VELOCIDAD_MOVIMIENTO = 550;
	private static double RADIO_COLISION = 8;
	
	public Bala(Punto2D posicion, BANDO bando) {
		super(new Hitbox(posicion ,RADIO_COLISION), bando, VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("aliada"));

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
