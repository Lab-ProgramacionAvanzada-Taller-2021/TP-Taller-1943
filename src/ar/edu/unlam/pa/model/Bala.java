package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.model.Elemento.BANDO;

public class Bala extends Elemento{
		
	public Bala(Punto2D posicion, BANDO tipo_bando, String RUTA_IMAGEN, double VELOCIDAD_MOVIMIENTO, double RADIO_COLISION) {
		super(new Hitbox(posicion ,RADIO_COLISION), tipo_bando, VELOCIDAD_MOVIMIENTO, RUTA_IMAGEN);
	}

	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, -dt);
	}
	
	@Override
	public String toString() {
		return "Bala";
	}

}
