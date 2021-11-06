package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigo extends Avion {
	private static double VELOCIDAD_MOVIMIENTO = 100;
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;
	private static double INTERVALO_DISPARO = 1;
	private int comportamiento;
	private double tiempoDisparo = INTERVALO_DISPARO;
	private Escenario escenario;

	public AvionEnemigo(Escenario escenario, double x, double y, int comportamiento) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, Grafico.obtenerGrafico("chico"));
		this.escenario = escenario;
		this.comportamiento = comportamiento;
	}
	
	public static AvionEnemigo AvionEnemigoFrontal(Escenario escenario) {
		return new AvionEnemigo(escenario, Math.random() * (Ventana.ANCHO-RADIO_COLISION), -RADIO_COLISION, 0);
	}
	
	public static AvionEnemigo AvionEnemigoLateralIzq(Escenario escenario) {
		return new AvionEnemigo(escenario, -RADIO_COLISION, Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 1);
	}
	
	public static AvionEnemigo AvionEnemigoLateralDer(Escenario escenario) {
		return new AvionEnemigo(escenario, Ventana.ANCHO+RADIO_COLISION, Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 2);
	}

	@Override
	public int darPuntos() {
		return PUNTOS;
	}
		
	public void disparar(double dt) {
		if(tiempoDisparo > 0) {
			tiempoDisparo -= dt;
		}else {
			tiempoDisparo = INTERVALO_DISPARO;
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion()));
		}
	}
	
	@Override
	public void actualizar(double dt) {
		switch (this.comportamiento) {
			case 0: // se mueve de arriba hacia abajo
				this.moverEnDireccion(0, dt);
				break;
			case 1: // se mueve de izquierda a derecha
				this.moverEnDireccion(dt*0.707, dt*0.707);
				break;
			case 2: // se mueve de derecha a izquierda
				this.moverEnDireccion(-dt*0.707, dt*0.707);
				break;
			}
		
		disparar(dt);
	}
}
