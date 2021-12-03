package ar.edu.unlam.pa.model;

import java.util.HashSet;
import java.util.Set;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.model.estados.avion.AvionNormal;
import ar.edu.unlam.pa.model.estados.avion.EstadoAvion;
import ar.edu.unlam.pa.servicios.MovimientoPlayer;

public class AvionPlayer extends Avion implements MovimientoPlayer {
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 100;
	private static double VELOCIDAD_MOVIMIENTO = 180;
	private static double INTERVALO_DISPARO = 0.1;
	
	private EstadoAvion estado = new AvionNormal();
	private Escenario escenario;
	private Set<Integer> teclasPresionadas = new HashSet<Integer>();
	private int nroJugador = 1;
	private int id;
	private int puntos = 0;
	private double tiempoDisparo = -1;
	private boolean disparando = false;

	public AvionPlayer(Escenario escenario, int nroJugador, int id, double x, double y, String nombreImagen) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), DIRECCION.CENTRO, BANDO.AMERICANO, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, nombreImagen);
		this.escenario = escenario;
		this.nroJugador = nroJugador;
		this.id = id;
	}
	
	public static AvionPlayer crearJugador1(Escenario escenario, int id) {
		return new AvionPlayer(escenario, 1, id,Ventana.ANCHO / 6.0, Ventana.ALTO / 1.2, 
				"jugador1");
	}
	
	public static AvionPlayer crearJugador2(Escenario escenario, int id) {
		return new AvionPlayer(escenario, 2, id,Ventana.ANCHO / 2.9, Ventana.ALTO / 1.2, 
				"jugador2");
	}
	
	public static AvionPlayer crearJugador3(Escenario escenario, int id) {
		return new AvionPlayer(escenario, 3, id,Ventana.ANCHO / 1.7, Ventana.ALTO / 1.2, 
				"jugador3");
	}
	
	public static AvionPlayer crearJugador4(Escenario escenario, int id) {
		return new AvionPlayer(escenario, 4, id,Ventana.ANCHO / 1.3, Ventana.ALTO / 1.2, 
				"jugador4");
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getNroJugador() {
		return this.nroJugador;
	}
	
	@Override
	public void colisiono(Elemento elemento) {
		if(elemento instanceof PowerUp) {
			agarraPowerUP();
			return;
		}
		
		vidaActual -= 10;
		
		if(vidaActual <= 0)
			destruir();
	}

	public int obtenerPuntos() {
		return this.puntos;
	}
	
	public void sumarPuntos(int puntos) {
		this.puntos += puntos;
	}
	
	@Override
	public void disparar(double dt) {
		if(tiempoDisparo > 0) {
			tiempoDisparo -= dt;
		}else {
			tiempoDisparo = INTERVALO_DISPARO;
			estado.disparar(this, escenario);
		}
	}

	public void agarraPowerUP() {
		estado = estado.agarraPowerUp();
	}

	private void pierdePowerUP() {
		estado = estado.terminaPowerUp();
	}

	public void decrementarContadorPowerUP(double dt) {
		if (!estado.tienePowerUp(dt))
			this.pierdePowerUP();
	}

	public String toStringEstado() {
		return estado.toString();
	}

	@Override
	public void moverArriba(double dt) {
		moverEnDireccion(0, -dt);
	}

	@Override
	public void moverAbajo(double dt) {
		moverEnDireccion(0, dt);
	}

	@Override
	public void moverDerecha(double dt) {
		moverEnDireccion(dt, 0);
	}

	@Override
	public void moverIzquierda(double dt) {
		moverEnDireccion(-dt, 0);
	}

	@Override
	public void moverArribaDerecha(double dt) {
		moverEnDireccion(dt, -dt);
	}

	@Override
	public void moverArribaIzquierda(double dt) {
		moverEnDireccion(-dt, -dt);
	}

	@Override
	public void moverAbajoDerecha(double dt) {
		moverEnDireccion(dt, dt);
	}

	@Override
	public void moverAbajoIzquierda(double dt) {
		moverEnDireccion(-dt, dt);
	}

	public EstadoAvion getEstado() {
		return estado;
	}
	
	public void dibujarBarraJugador(Graphics2D g2) {
		double porcentajeVida = vidaActual/vidaMaxima;
		int ancho = (Ventana.ANCHO-32) / 4;
		int posY = 32;
		g2.setColor(Color.WHITE);
		g2.drawString("Jugador " + nroJugador, 16 + (ancho*(nroJugador-1)), 32);
		g2.drawString(this.puntos + "", 16 + (ancho*(nroJugador-1)), 48);
		g2.drawString(toStringEstado(), 16 + (ancho*(nroJugador-1)), Ventana.ALTO-36);
		g2.setColor((porcentajeVida>0.66) ? Color.GREEN : (porcentajeVida>0.33) ? Color.YELLOW : Color.RED);
		g2.fillRect(16+(ancho*(nroJugador-1)), Ventana.ALTO-32, (int)(ancho/1.2*porcentajeVida), 16);
		g2.setColor(Color.BLACK);
		g2.drawRect(16+(ancho*(nroJugador-1)), Ventana.ALTO-32, (int)(ancho/1.2), 16);
	}
	
	@Override
	public void actualizar(double dt) {
		/*
		if(teclasPresionadas.contains(KeyEvent.VK_W)){
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.NOROESTE);
				//moverArribaIzquierda(dt);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.NORESTE);
				//moverArribaDerecha(dt);
			else
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.NORTE);
				//moverArriba(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_S)) {
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.SUROESTE);
				//moverAbajoIzquierda(dt);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.SURESTE);
				//moverAbajoDerecha(dt);
			else
				Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.SUR);
				//moverAbajo(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_A)) {
			Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.OESTE);
			//moverIzquierda(dt);
		}else if(teclasPresionadas.contains(KeyEvent.VK_D)) {
			Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.ESTE);
			//moverDerecha(dt);
		}else
			Client.getInstance().send(NetworkMessageType.MOV, DIRECCION.CENTRO);
		
		if(teclasPresionadas.contains(KeyEvent.VK_CONTROL)) {
			disparar(dt);
		}
		*/
		super.actualizar(dt);
		
		if(disparando)
			disparar(dt);
		
		decrementarContadorPowerUP(dt);
	}
	
	public void dispara(boolean estado) {
		this.disparando = estado;
	}
	
	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		if(puedeMoverEnDireccion(desplazamientoX, desplazamientoY)){
			super.moverEnDireccion(desplazamientoX, desplazamientoY);
		}	
	}
}
