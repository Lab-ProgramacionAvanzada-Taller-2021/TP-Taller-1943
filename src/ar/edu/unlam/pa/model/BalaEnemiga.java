package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class BalaEnemiga extends Bala{
	private static double VELOCIDAD_MOVIMIENTO = 200;
	private static double RADIO_COLISION = 3;
	
	public BalaEnemiga(Punto2D posicion) {
		super(posicion, 
				BANDO.JAPONES, 
				VELOCIDAD_MOVIMIENTO, 
				RADIO_COLISION, 
				Grafico.obtenerGrafico("balaEnemiga")
				);
	}

	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, dt);
	}
}