package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigoMedio extends Avion{
	private static double VELOCIDAD_MOVIMIENTO = 40;
	private static double RADIO_COLISION = 32;
	private static String NOMBRE_IMAGEN = "medio";
	private static double VIDA_MAXIMA = 20;
	private static int PUNTOS = 100;
	private static double INTERVALO_DISPARO = 2;
	private double tiempoDisparo = 1;
	private Escenario escenario;
	
	public AvionEnemigoMedio(Escenario escenario) {
		super(new Hitbox(new Punto2D(Math.random() * (Ventana.ANCHO-RADIO_COLISION), Ventana.ALTO), RADIO_COLISION), 
				DIRECCION.NORTE,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				NOMBRE_IMAGEN
				);
		this.escenario = escenario;
	}
	
	public AvionEnemigoMedio(Escenario escenario, double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), 
				DIRECCION.NORTE,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				NOMBRE_IMAGEN
				);
		this.escenario = escenario;
	}
	
	public void colisiono(Elemento elemento) {
		vidaActual -= 10;
		
		if(vidaActual <= 0) {
			this.destruir();
			
			escenario.agregarElementoCapa2(new Animacion(new Hitbox(getPosicion(), RADIO_COLISION*2), 
					VELOCIDAD_MOVIMIENTO));
			
			if(elemento instanceof BalaAliada) {
				 ((BalaAliada) elemento).sumarPuntosAvion(PUNTOS);
			}
		}
	}
	
	@Override
	public void actualizar(double dt) {
		super.actualizar(dt);
		
		disparar(dt);
	}
	
	@Override
	public void disparar(double dt) {
		if(tiempoDisparo > 0) {
			tiempoDisparo -= dt;
		}else {
			Punto2D posicion = escenario.obtenerPosicionJugadorAleatorio();
			
			if(posicion != null) {
				DIRECCION direccion = calcularDireccion(posicion);
				escenario.agregarElemento(new BalaEnemiga(getPosicion(), direccion));
				tiempoDisparo = INTERVALO_DISPARO;
			}
		}
	}
}
