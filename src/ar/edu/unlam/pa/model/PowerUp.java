package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class PowerUp extends Elemento{
	private static double RADIO_COLISION = 9;
	private static double VELOCIDAD_MOVIMIENTO = 60;

	public PowerUp(Punto2D posicion) {
		super(new Hitbox(posicion, RADIO_COLISION),
				DIRECCION.SUR,
				BANDO.NEUTRAL, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("powerup")
		);
	}
	
	@Override
	public void colisiono(Elemento otro) {
		if(otro instanceof BalaAliada) {
			moverEnDireccion(0, -0.167);
			return;
		}
		
		destruir();
	}
}
