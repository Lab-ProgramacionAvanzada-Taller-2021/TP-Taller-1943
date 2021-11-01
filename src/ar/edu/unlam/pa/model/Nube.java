package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class Nube extends Elemento{
	private static double RADIO_COLISION = 250;
	private static double VELOCIDAD_MOVIMIENTO = 90;
	
	public Nube() {
		super(new Hitbox(Math.random()*Ventana.ANCHO, -RADIO_COLISION, RADIO_COLISION), 
				Elemento.BANDO.NEUTRAL, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("nubes" + (int)(Math.random() * (5-1+1) + 1))
				);
	}
	
	public void actualizar(double dt) {
		moverEnDireccion(0, dt);
	}

}
