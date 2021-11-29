package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class BalaAliada extends Bala{
	private static double VELOCIDAD_MOVIMIENTO = 500;
	private static double RADIO_COLISION = 8;
	private AvionPlayer jugador;
	
	public BalaAliada(Punto2D posicion, DIRECCION direccion, AvionPlayer jugador) {
		super(posicion, 
				BANDO.AMERICANO, 
				VELOCIDAD_MOVIMIENTO, 
				RADIO_COLISION, 
				direccion,
				Grafico.obtenerGrafico("balaAliada")
			);
		this.jugador = jugador;
	}
	
	public void sumarPuntosAvion(int puntos) {
		jugador.sumarPuntos(puntos);
	}
	
}