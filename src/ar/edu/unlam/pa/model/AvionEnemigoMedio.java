package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigoMedio extends Avion{
	private static double VELOCIDAD_MOVIMIENTO = 40;
	private static double RADIO_COLISION = 32;
	private static double VIDA_MAXIMA = 20;
	private static int PUNTOS = 100;
	private double intervalo = 1;
	private Escenario escenario;
	
	public AvionEnemigoMedio(Escenario escenario) {
		super(new Hitbox(new Punto2D(Math.random() * (Ventana.ANCHO-RADIO_COLISION), Ventana.ALTO), RADIO_COLISION), 
				DIRECCION.NORTE,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("medio")
				);
		this.escenario = escenario;
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
		super.actualizar(dt);
		
		//falta disparar
	}
}
