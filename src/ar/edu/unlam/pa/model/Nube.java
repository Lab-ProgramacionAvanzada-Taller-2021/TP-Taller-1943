package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

public class Nube extends Elemento{
	private static double RADIO_COLISION = 250;
	private static double VELOCIDAD_MOVIMIENTO = 50;
	
	public Nube() {
		super(new Hitbox(Math.random()*Ventana.ANCHO, -RADIO_COLISION, RADIO_COLISION), 
				DIRECCION.SUR,
				BANDO.NEUTRAL, 
				VELOCIDAD_MOVIMIENTO, 
				"nubes" + (int)(Math.random() * (5-1+1) + 1)
				);
	}
}
