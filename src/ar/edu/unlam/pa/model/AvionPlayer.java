package ar.edu.unlam.pa.model;

import java.util.HashSet;
import java.util.Set;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.model.estados.avion.AvionNormal;
import ar.edu.unlam.pa.model.estados.avion.EstadoAvion;
import ar.edu.unlam.pa.servicios.MovimientoPlayer;

public class AvionPlayer extends Avion implements MovimientoPlayer, KeyListener {
	private static double RADIO_COLISION = 16;
	private static double VIDA_MAXIMA = 100;
	private static double VELOCIDAD_MOVIMIENTO = 180;
	private static double INTERVALO_DISPARO = 0.1;
	
	private EstadoAvion estado = new AvionNormal();
	private Escenario escenario;
	private Set<Integer> teclasPresionadas = new HashSet<Integer>();
	private int[] teclasPrediseniadas;
	private int nroJugador = 1;
	private int puntos = 0;
	private double tiempoDisparo = -1;

	public AvionPlayer(Escenario escenario, int nroJugador, double x, double y, int[] teclas, BufferedImage[] imagen) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), DIRECCION.NORTE, BANDO.AMERICANO, VIDA_MAXIMA, 
				VELOCIDAD_MOVIMIENTO, imagen);
		this.escenario = escenario;
		this.nroJugador = nroJugador;
		this.teclasPrediseniadas = teclas;
	}
	
	public static AvionPlayer crearJugador1(Escenario escenario) {
		return new AvionPlayer(escenario, 1, Ventana.ANCHO / 2.1, Ventana.ALTO / 1.2, 
				new int[] {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S,  KeyEvent.VK_CONTROL},
				Grafico.obtenerGrafico("jugador1"));
	}
	
	public static AvionPlayer crearJugador2(Escenario escenario) {
		return new AvionPlayer(escenario, 2, Ventana.ANCHO / 1.7, Ventana.ALTO / 1.2, 
				new int[] {KeyEvent.VK_UP , KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,  KeyEvent.VK_SHIFT},
				Grafico.obtenerGrafico("jugador2"));
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
		if(teclasPresionadas.contains(teclasPrediseniadas[0])){
			if(teclasPresionadas.contains(teclasPrediseniadas[1]))
				moverArribaIzquierda(dt);
			else if(teclasPresionadas.contains(teclasPrediseniadas[2]))
				moverArribaDerecha(dt);
			else
				moverArriba(dt);
		}else if(teclasPresionadas.contains(teclasPrediseniadas[3])) {
			if(teclasPresionadas.contains(teclasPrediseniadas[1]))
				moverAbajoIzquierda(dt);
			else if(teclasPresionadas.contains(teclasPrediseniadas[2]))
				moverAbajoDerecha(dt);
			else
				moverAbajo(dt);
		}else if(teclasPresionadas.contains(teclasPrediseniadas[1])) {
			moverIzquierda(dt);
		}else if(teclasPresionadas.contains(teclasPrediseniadas[2])) {
			moverDerecha(dt);
		}
		
		if(teclasPresionadas.contains(teclasPrediseniadas[4])) {
			disparar(dt);
		}
		
		decrementarContadorPowerUP(dt);
	}
	
	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		if(puedeMoverEnDireccion(desplazamientoX, desplazamientoY)){
			super.moverEnDireccion(desplazamientoX, desplazamientoY);
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
