package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class Isla extends Elemento{
	private static double RADIO_COLISION = 30;
	private static double VELOCIDAD_MOVIMIENTO = 60;
	
	public Isla() {
		super(new Hitbox(Math.random()*Ventana.ANCHO, -RADIO_COLISION, RADIO_COLISION), 
				Elemento.BANDO.NEUTRAL, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("isla" + (int)(Math.random() * (4-1+1) + 1))
				);
	}
	
	public void actualizar(double dt) {
		moverEnDireccion(0, dt);
	}

}
