package ar.edu.unlam.pa.model;


import ar.edu.unlam.pa.graficos.Grafico;

public class AvionEnemigoJefe extends Avion{
	private static double VELOCIDAD_MOVIMIENTO = 40;
	private static double RADIO_COLISION = 80;
	private static double VIDA_MAXIMA = 2000;
	private static int PUNTOS = 1000;
	private double intervalo = 1;
	private Escenario escenario;
	
	public AvionEnemigoJefe() {
		super(new Hitbox(new Punto2D(100, 100), RADIO_COLISION), 
				DIRECCION.SUR,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				Grafico.obtenerGrafico("jefe")
				);
	}
	
	@Override
	public void colisiono(Elemento elemento) {
		vidaActual -= 5;
		
		if(vidaActual <= 0)
			this.destruir();
	}
	
	
}
