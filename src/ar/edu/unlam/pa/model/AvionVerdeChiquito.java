package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.servicios.Movimiento;

public class AvionVerdeChiquito extends Avion implements Movimiento {

	private static double VELOCIDAD_MOVIMIENTO = 2;
	private static double RADIO_COLISION = 1;
	private static double VIDA_MAXIMA = 10;

	public AvionVerdeChiquito(Punto2D posicion) {
		super(new Hitbox(posicion, RADIO_COLISION), Elemento.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO);
	}

}
