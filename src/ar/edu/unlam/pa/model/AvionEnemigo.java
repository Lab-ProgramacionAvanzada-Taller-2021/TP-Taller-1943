package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigo extends Avion {
	private static double VELOCIDAD_MOVIMIENTO = 100;
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;
	private static int PROBABILIDAD_POWERUP = 15;
	private static double INTERVALO_DISPARO = 1.5;
	private double tiempoDisparo = INTERVALO_DISPARO - 1;
	private Escenario escenario;

	public AvionEnemigo(Escenario escenario, double x, double y, DIRECCION direccion) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), direccion, BANDO.JAPONES, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("chico"));
		this.escenario = escenario;
	}
	
	public static AvionEnemigo AvionEnemigoFrontal(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								Math.random() * (Ventana.ANCHO-RADIO_COLISION), 
								-RADIO_COLISION, 
								DIRECCION.SUR);
	}
	
	public static AvionEnemigo AvionEnemigoLateralIzq(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								-RADIO_COLISION, 
								Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 
								DIRECCION.SURESTE);
	}
	
	public static AvionEnemigo AvionEnemigoLateralDer(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								Ventana.ANCHO+RADIO_COLISION, Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 
								DIRECCION.SUROESTE);
	}

	@Override
	public int darPuntos() {
		return PUNTOS;
	}
	
	@Override
	public void colisiono(Elemento otro) {
		super.colisiono(otro);
		
		if( Math.random()*100 < PROBABILIDAD_POWERUP) {
			escenario.agregarElemento(new PowerUp(getPosicion()));
		}
	}
		
	public void disparar(double dt) {
		if(tiempoDisparo > 0) {
			tiempoDisparo -= dt;
		}else {
			tiempoDisparo = INTERVALO_DISPARO;
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.SUR));
		}
	}
	
	@Override
	public void actualizar(double dt) {
		super.actualizar(dt);
		
		disparar(dt);
	}
}
