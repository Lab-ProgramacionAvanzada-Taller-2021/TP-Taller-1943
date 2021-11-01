package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class AvionEnemigo extends Avion {
	private static double VELOCIDAD_MOVIMIENTO = 100;
	private static double RADIO_COLISION = 8;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;

	public AvionEnemigo(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("chico"));
	}
	
	@Override
	public int darPuntos() {
		return PUNTOS;
	}
	
	@Override
	public void actualizar(double dt) {
		this.moverEnDireccion(0, dt);
	}

}
