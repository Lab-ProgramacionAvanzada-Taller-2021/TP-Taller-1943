package ar.edu.unlam.pa.model;


public class BalaEnemiga extends Bala{
	private static double VELOCIDAD_MOVIMIENTO = 150;
	private static double RADIO_COLISION = 4;
	
	public BalaEnemiga(Punto2D posicion, DIRECCION direccion) {
		super(posicion, 
				BANDO.JAPONES, 
				VELOCIDAD_MOVIMIENTO, 
				RADIO_COLISION, 
				direccion,
				"balaEnemiga"
			);
	}
}