package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.model.Elemento.BANDO;

public class BalaEnemiga extends Bala{
	private static String RUTA_IMAGEN = "Recursos/Imagenes/balaEnemiga.png";
	private static double VELOCIDAD_MOVIMIENTO = 200;
	private static double RADIO_COLISION = 8;
	
	public BalaEnemiga(Punto2D posicion) {
		super(posicion, BANDO.JAPONES, RUTA_IMAGEN, VELOCIDAD_MOVIMIENTO, RADIO_COLISION);
	}

	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, dt);
	}
}