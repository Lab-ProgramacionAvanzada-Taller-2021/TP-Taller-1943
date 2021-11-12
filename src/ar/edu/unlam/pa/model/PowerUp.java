package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class PowerUp extends Elemento{
	private static double RADIO_COLISION = 9;
	private static double VELOCIDAD_MOVIMIENTO = 60;

	public PowerUp(Punto2D posicion) {
		super(new Hitbox(posicion, RADIO_COLISION), 
				Elemento.BANDO.NEUTRAL, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("powerup")
		);
	}
	
	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, dt);
		
		super.actualizar(dt);
	}

}
