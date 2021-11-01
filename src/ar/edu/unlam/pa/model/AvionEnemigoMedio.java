package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;

public class AvionEnemigoMedio extends Avion{
	private static double VELOCIDAD_MOVIMIENTO = 40;
	private static double RADIO_COLISION = 22;
	private static double VIDA_MAXIMA = 20;
	private static int PUNTOS = 100;
	private double intervalo = 1;

	public AvionEnemigoMedio(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("medio"));
	}
	
	public void colisiono(Elemento elemento) {
		vidaActual -= 10;
		
		if(vidaActual <= 0)
			this.destruir();
	}
	
	@Override
	public int darPuntos() {
		return PUNTOS;
	}
	
	@Override
	public void actualizar(double dt) {
		this.moverEnDireccion(0, -dt);
	}
}
