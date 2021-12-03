package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigoJefe extends Avion{
	private static double VELOCIDAD_MOVIMIENTO = 40;
	private static double RADIO_COLISION = 80;
	private static double VIDA_MAXIMA = 2000;
	private static int PUNTOS = 10000;
	private static int INTERVALO_MOVIMIENTO = 5;
	private static String NOMBRE_IMAGEN = "jefe";
	private double intervaloDisparo = 0.5;
	private double tiempoMovimiento = INTERVALO_MOVIMIENTO/2;
	private double tiempoDisparo = intervaloDisparo;
	private int signo = 1;
	private Escenario escenario;
	
	public AvionEnemigoJefe(Escenario escenario) {
		super(new Hitbox(new Punto2D((Ventana.ANCHO/2), -RADIO_COLISION), RADIO_COLISION), 
				DIRECCION.SUR,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				NOMBRE_IMAGEN
				);
		this.escenario = escenario;
	}
	
	public AvionEnemigoJefe(Escenario escenario, double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), 
				DIRECCION.SUR,
				BANDO.JAPONES, 
				VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, 
				NOMBRE_IMAGEN
				);
		this.escenario = escenario;
	}
	
	@Override
	public void actualizar(double dt) {
		if(getPosicion().getY() < RADIO_COLISION*2) {
			moverEnDireccion(0, dt);
		}else {
			if(tiempoMovimiento < 0) {
				signo *= -1;
				tiempoMovimiento = INTERVALO_MOVIMIENTO;
			}else {
				tiempoMovimiento -= dt;
			}
			
			moverEnDireccion(dt*signo,0);
			disparar(dt);
		}
	}
	
	@Override
	public void disparar(double dt) {
		if(tiempoDisparo > 0) {
			tiempoDisparo -= dt;
		}else {
			tiempoDisparo = intervaloDisparo;
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.NORTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.NORESTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.ESTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.SURESTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.SUR));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.SUROESTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.OESTE));
			escenario.agregarElemento(new BalaEnemiga(this.getPosicion(), DIRECCION.NOROESTE));
		}
	}
	
	@Override
	public void colisiono(Elemento elemento) {
		vidaActual -= 5;
		
		if(vidaActual <= 0) {
			this.destruir();
		
			escenario.agregarElementoCapa2(new Animacion(new Hitbox(getPosicion(), RADIO_COLISION*2), 
				VELOCIDAD_MOVIMIENTO));
			
			if(elemento instanceof BalaAliada) {
				 ((BalaAliada) elemento).sumarPuntosAvion(PUNTOS);
			}
		}
	}
	
	
}
