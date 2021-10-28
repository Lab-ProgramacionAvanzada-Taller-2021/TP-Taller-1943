package ar.edu.unlam.pa.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.servicios.Movimiento;

/*
 * Esta clase emula cualquier objeto que se este en mapa del juego y que puede interactuar con otros.
 * */
public class Elemento implements Movimiento {
	private Hitbox hitbox;
	private int bando;
	private double velocidad;
	
	private BufferedImage imagen;

	public static final int AMERICANO = 1;
	public static final int JAPONES = 2;
	public static final int VELOCIDAD_UNO = 1;

	public Elemento(Hitbox hitbox, int bando, double velocidad, String ruta) {
		this.hitbox = hitbox;
		this.bando = bando;
		// En futuro se multiplicara con la velocidad dependiendo de cada avion
		this.velocidad = VELOCIDAD_UNO;
		
		this.imagen = LectorArchivos.leerImagen(ruta);
	}

	public Punto2D getPosicion() {
		return hitbox.getPosicion();
	}

	private boolean esEnemigo(Elemento otro) {
		return bando != otro.bando;
	}

	public boolean colisionaCon(Elemento otro) {
		return esEnemigo(otro) && hitbox.colisionaCon(otro.hitbox);
	}

	@Override
	public String toString() {
		return "Posicion= " + hitbox + ", bando=" + bando + "]";
	}
	
	protected boolean puedeMoverEnDireccion(double desplazamientoX, double desplazamientoY) {
		return hitbox.puedeMover(desplazamientoX, desplazamientoY);
	}

	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		hitbox.moverPunto(desplazamientoX, desplazamientoY);
	}

	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, (int)(this.hitbox.getExtremoIzq()), (int)(this.hitbox.getExtremoSup()) , 
				(int)this.hitbox.getDiametro(), (int)this.hitbox.getDiametro(), null);
	}
	
	public void actualizar(double dt) {}

}
