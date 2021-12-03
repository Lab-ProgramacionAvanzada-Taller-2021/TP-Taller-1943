package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

public class Isla extends Elemento{
	private static double RADIO_COLISION = 30;
	private static double VELOCIDAD_MOVIMIENTO = 10;
	private int num;
	
	public Isla() {
		super(new Hitbox(Math.random()*Ventana.ANCHO, -RADIO_COLISION, RADIO_COLISION), 
			DIRECCION.SUR,
			Elemento.BANDO.NEUTRAL, 
			VELOCIDAD_MOVIMIENTO, 
			"isla1"
		);
		this.num = (int)(Math.random() * (7-1+1) + 1);
	}
	
	public Isla(double x, double y, int num) {
		super(new Hitbox(x, y, RADIO_COLISION), 
			DIRECCION.SUR,
			Elemento.BANDO.NEUTRAL, 
			VELOCIDAD_MOVIMIENTO, 
			"isla" + num
		);
		this.num = num;
	}
	
	@Override
	public String getInfo() {
		return getPosicion().getX() + "|" + getPosicion().getY() + "|" + num;
	}
}
