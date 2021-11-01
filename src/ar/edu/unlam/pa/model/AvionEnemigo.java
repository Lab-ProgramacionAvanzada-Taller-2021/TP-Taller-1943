package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class AvionEnemigo extends Avion {
	private static double VELOCIDAD_MOVIMIENTO = 100;
	private static double RADIO_COLISION = 8;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;
	private static double INTERVALO_DISPARO = 3; // lalalla
	private int tipo;
	private int comportamiento;
	private double tiempoDisparo = -1;
	private boolean disparo = false;
	private Escenario escenario;

	public AvionEnemigo(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("chico"));
	}

	@Override
	public int darPuntos() {
		return PUNTOS;
	}
		
	public void disparar(double dt) {
		
	}
	
	@Override
	public void actualizar(double dt) {
		switch (this.comportamiento) {
			case 0: // se mueve de izquierda a derecha
				this.moverEnDireccion(0, dt);
				break;
			case 1: // se mueve por el centro hacia abajo
				this.moverEnDireccion(0, dt);
				break;
			case 2: // se mueve de derecha a izquierda
				this.moverEnDireccion(0, dt);
				break;
			}
		
		disparar(dt);
	}
}
