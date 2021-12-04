package ar.edu.unlam.pa.model;

import ar.edu.unlam.pa.graficos.Ventana;

public class AvionEnemigo extends Avion {
	private static double VELOCIDAD_MOVIMIENTO = 80;
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 50;
	private int daPowerUP = 0;
	private static double INTERVALO_DISPARO = 2;
	private static double PROBABILIDAD_POWERUP = 15;
	private double tiempoDisparo = INTERVALO_DISPARO - 1;
	private static String NOMBRE_IMAGEN = "chico";
	private Escenario escenario;

	public AvionEnemigo(Escenario escenario, double x, double y, DIRECCION direccion, int daPowerUP) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), direccion, BANDO.JAPONES, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, NOMBRE_IMAGEN);
		this.escenario = escenario;
		this.daPowerUP = daPowerUP;
	}
	
	public static AvionEnemigo AvionEnemigoFrontal(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								Math.random() * (Ventana.ANCHO-RADIO_COLISION), 
								-RADIO_COLISION, 
								DIRECCION.SUR,
								( Math.random()*100 < PROBABILIDAD_POWERUP) ? 1 : 0);
	}
	
	public static AvionEnemigo AvionEnemigoLateralIzq(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								-RADIO_COLISION, 
								Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 
								DIRECCION.SURESTE,
								( Math.random()*100 < PROBABILIDAD_POWERUP) ? 1 : 0);
	}
	
	public static AvionEnemigo AvionEnemigoLateralDer(Escenario escenario) {
		return new AvionEnemigo(escenario, 
								Ventana.ANCHO+RADIO_COLISION, Math.random() * (Ventana.ALTO/4-RADIO_COLISION), 
								DIRECCION.SUROESTE,
								( Math.random()*100 < PROBABILIDAD_POWERUP) ? 1 : 0);
	}
	
	@Override
	public synchronized void colisiono(Elemento otro) {
		vidaActual -= 10;
		
		if(vidaActual <= 0) {
			this.destruir();
			
			escenario.agregarElementoCapa2(new Animacion(new Hitbox(getPosicion(), RADIO_COLISION*2), 
					VELOCIDAD_MOVIMIENTO));
			
			if(otro instanceof BalaAliada) {
				((BalaAliada) otro).sumarPuntosAvion(PUNTOS);
			}
			
			if( daPowerUP == 1) {
				Elemento elemento = new PowerUp(getPosicion());
				escenario.agregarElemento(elemento);
			}
		}		
	}
		
	@Override
	public synchronized void disparar(double dt) {
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
	
	public String getInfo() {
		return getPosicion().getX() + "|" + getPosicion().getY() + "|" + direccion + "|" + daPowerUP;
	}

	public void setInfo(String info) {
		String[] data = info.split("\\|");
		actualizarPosicion(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		
		if(data[2] != null)
			cambiarDireccion(DIRECCION.valueOf(data[2]));
		
		if(data[3] != null) {
			daPowerUP = Integer.parseInt(data[3]);
		}
	}
}
