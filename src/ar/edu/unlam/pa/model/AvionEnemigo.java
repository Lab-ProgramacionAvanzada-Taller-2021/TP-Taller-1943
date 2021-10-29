package ar.edu.unlam.pa.model;

public class AvionEnemigo extends Avion {

	private static String RUTA = "Recursos/Imagenes/enemigo.png";
	private static double VELOCIDAD_MOVIMIENTO = 180;
	private static double RADIO_COLISION = 8;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;

	public AvionEnemigo(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, RUTA);
	}
	
	@Override
	public void actualizar(double dt) {
		this.moverEnDireccion(0, dt);
	}

}
