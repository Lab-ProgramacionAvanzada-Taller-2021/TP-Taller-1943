package ar.edu.unlam.pa.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.servicios.Movimiento;


public class Elemento implements Movimiento {
	private Hitbox hitbox;
	private BANDO bando;
	private double velocidad;
	
	private BufferedImage imagen;

	public static enum BANDO{
		AMERICANO,
		JAPONES,
	}

	public Elemento(Hitbox hitbox, BANDO bando, double velocidad, String ruta) {
		this.hitbox = hitbox;
		this.bando = bando;
		this.velocidad = velocidad;
		
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
		return hitbox.puedeMover(desplazamientoX*velocidad, desplazamientoY*velocidad);
	}

	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		hitbox.moverPunto(desplazamientoX*velocidad, desplazamientoY*velocidad);
	}

	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, (int)(this.hitbox.getExtremoIzq()), (int)(this.hitbox.getExtremoSup()) , 
				(int)this.hitbox.getDiametro(), (int)this.hitbox.getDiametro(), null);
	}
	
	public void actualizar(double dt) {}

}
