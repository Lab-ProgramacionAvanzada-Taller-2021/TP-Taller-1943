package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.model.Elemento.BANDO;

public class BalaAliada extends Bala{
	private static String RUTA_IMAGEN = "Recursos/Imagenes/balaAliada.png";
	private static double VELOCIDAD_MOVIMIENTO = 550;
	private static double RADIO_COLISION = 8;
	
	public BalaAliada(Punto2D posicion) {
		super(posicion, BANDO.AMERICANO, RUTA_IMAGEN, VELOCIDAD_MOVIMIENTO, RADIO_COLISION);
	}
	
}