package ar.edu.unlam.pa.model;

public class AvionEnemigo extends Avion {

	private static String RUTA = "";
	private static double VELOCIDAD_MOVIMIENTO = 2;
	private static double RADIO_COLISION = 1;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;

	public AvionEnemigo(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, RUTA);
	}
	
	

}
