package ar.edu.unlam.pa.model;

public class AvionVerdeChiquito extends Avion {

	private static double VELOCIDAD_MOVIMIENTO = 2;
	private static double RADIO_COLISION = 1;
	private static double VIDA_MAXIMA = 10;
	private static double PUNTOS = 10;

	public AvionVerdeChiquito(Punto2D posicion) {
		super(new Hitbox(posicion, RADIO_COLISION), Elemento.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO);
	}
	

}
