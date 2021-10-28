package ar.edu.unlam.pa.model;

import java.util.HashSet;
import java.util.Set;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import ar.edu.unlam.pa.elementos.Bala;
import ar.edu.unlam.pa.model.estados.avion.AvionNormal;
import ar.edu.unlam.pa.model.estados.avion.EstadoAvion;
import ar.edu.unlam.pa.servicios.MovimientoPlayer;


public class AvionPlayer extends Avion implements MovimientoPlayer, KeyListener {

	private EstadoAvion estado = new AvionNormal();
	private static String RUTA = "Recursos/Imagenes/avion.png";
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 100;
	private static double VELOCIDAD_MOVIMIENTO = 150;
	//ArrayList<Bala> bala = new ArrayList<Bala>();
	
	private Set<Integer> teclasPresionadas = new HashSet<Integer>();

	public AvionPlayer(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.AMERICANO, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, RUTA);
	}

	public void disparar() {
		estado.disparar();
	}

	public void agarraPowerUP() {
		estado = estado.agarraPowerUp();
	}

	private void pierdePowerUP() {
		estado = estado.terminaPowerUp();
	}

	public void decrementarContadorPowerUP() {
		if (!estado.tienePowerUp())
			this.pierdePowerUP();
	}

	public String toStringEstado() {
		return estado.toString();
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

	public EstadoAvion getEstado() {
		return estado;
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
		if(teclasPresionadas.contains(KeyEvent.VK_W)) {
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				moverArribaIzquierda(dt);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				moverArribaDerecha(dt);
			else
				moverArriba(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_S)) {
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				moverAbajoIzquierda(dt);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				moverAbajoDerecha(dt);
			else
				moverAbajo(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_A)) {
			moverIzquierda(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_D)) {
			moverDerecha(dt);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		teclasPresionadas.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		teclasPresionadas.remove(e.getKeyCode());
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
