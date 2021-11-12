package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class BalaAliada extends Bala{
	private static double VELOCIDAD_MOVIMIENTO = 550;
	private static double RADIO_COLISION = 8;
	
	public BalaAliada(Punto2D posicion, DIRECCION direccion) {
		super(posicion, 
				BANDO.AMERICANO, 
				VELOCIDAD_MOVIMIENTO, 
				RADIO_COLISION, 
				direccion,
				Grafico.obtenerGrafico("balaAliada")
				);
	}
	
}