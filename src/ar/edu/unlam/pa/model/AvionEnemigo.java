package ar.edu.unlam.pa.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ar.edu.unlam.pa.servicios.MovimientoPlayer;

//import ar.edu.unlam.pa.model.estados.avion.AvionNormal;
//import ar.edu.unlam.pa.model.estados.avion.EstadoAvion;

public class AvionEnemigo extends Avion implements MovimientoPlayer {

	//private static String RUTA = "";
	private static String RUTA = "Recursos/Imagenes/enemigo.png";
	private static double VELOCIDAD_MOVIMIENTO = 2;
	private static double RADIO_COLISION = 8;
	private static double VIDA_MAXIMA = 10;
	private static int PUNTOS = 10;
	
	//private static double RADIO_COLISION = 16;
	//private static double VIDA_MAXIMA = 100;
	//private static double VELOCIDAD_MOVIMIENTO = 150;

	public AvionEnemigo(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, RUTA);
	}
	
	
	@Override
	public void moverArriba(double dt) {
		moverEnDireccion(0, -dt*VELOCIDAD_MOVIMIENTO);
	}

	@Override
	public void moverAbajo(double dt) {
		moverEnDireccion(0, dt*VELOCIDAD_MOVIMIENTO);
	}

	@Override
	public void moverDerecha(double dt) {
		moverEnDireccion(dt*VELOCIDAD_MOVIMIENTO, 0);
	}

	@Override
	public void moverIzquierda(double dt) {
		moverEnDireccion(-dt*VELOCIDAD_MOVIMIENTO, 0);
	}

	@Override
	public void moverArribaDerecha(double dt) {
		moverEnDireccion(MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO, 
				-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO);
	}

	@Override
	public void moverArribaIzquierda(double dt) {
		moverEnDireccion(-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO, 
				-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO);
	}

	@Override
	public void moverAbajoDerecha(double dt) {
		moverEnDireccion(MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO, 
				MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO);
	}

	@Override
	public void moverAbajoIzquierda(double dt) {
		moverEnDireccion(-MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO, 
				MovimientoPlayer.DESPLAZAMIENTO_DIAGONAL*dt*VELOCIDAD_MOVIMIENTO);
	}
	
	@Override
	public void dibujar(Graphics2D g2) {
		super.dibujar(g2);
		
		double porcentajeVida = super.getVidaActual()/super.getVidaMaxima();
		g2.setColor((porcentajeVida>0.66) ? Color.GREEN : (porcentajeVida>0.33) ? Color.YELLOW : Color.RED);
		g2.fillRect(32, 480, (int)(128*porcentajeVida), 16);
		g2.setColor(Color.BLACK);
		g2.drawRect(32, 480, 128, 16);
	}
	
	@Override
	public void actualizar(double dt) {
			//moverAbajo(dt);
		moverAbajoDerecha(dt);
	}

}
