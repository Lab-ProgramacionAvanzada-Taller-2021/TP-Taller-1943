package ar.edu.unlam.pa.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.servicios.Movimiento;

public class Elemento implements Movimiento {
	private Hitbox hitbox;
	private BANDO bando;
	private double velocidad;
	private boolean destruido = false;
	private BufferedImage imagen;

	public static enum BANDO{
		AMERICANO,
		JAPONES,
		NEUTRAL
	}

	public Elemento(Hitbox hitbox, BANDO bando, double velocidad, BufferedImage imagen) {
		this.hitbox = hitbox;
		this.bando = bando;
		this.velocidad = velocidad;
		
		this.imagen = imagen;
	}

	public Punto2D getPosicion() {
		return hitbox.getPosicion();
	}

	public boolean esEnemigo(Elemento otro) {
		return bando != otro.bando;
	}

	public boolean colisionaCon(Elemento otro) {
		return esEnemigo(otro) && hitbox.colisionaCon(otro.hitbox);
	}
	
	public void colisiono(Elemento otro) {
		destruir();
	}
	
	public void destruir() {
		this.destruido = true;
	}
	
	public boolean estaDestruido() {
		return this.destruido;
	}
	
	public int darPuntos() {
		return 0;
	}
	
	public boolean estaFueraDeRango() {
		return (hitbox.getExtremoIzq() > Ventana.ANCHO || 
				hitbox.getExtremoDer() < 0 ||
				hitbox.getExtremoSup() > Ventana.ALTO ||
				hitbox.getExtremoInf() < 0);
	}
	
	protected boolean puedeMoverEnDireccion(double desplazamientoX, double desplazamientoY) {
		if(desplazamientoX != 0 && desplazamientoY != 0) {
			desplazamientoX *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
			desplazamientoY *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
		}
		
		return hitbox.puedeMover(desplazamientoX*velocidad, desplazamientoY*velocidad);
	}

	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		if(desplazamientoX != 0 && desplazamientoY != 0) {
			desplazamientoX *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
			desplazamientoY *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
		}
		
		hitbox.moverPunto(desplazamientoX*velocidad, desplazamientoY*velocidad);
	}

	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, (int)(this.hitbox.getExtremoIzq()), (int)(this.hitbox.getExtremoSup()) , 
				(int)this.hitbox.getDiametro(), (int)this.hitbox.getDiametro(), null);
	}
	
	public void actualizar(double dt) {
		if(estaFueraDeRango())
			destruir();
	}
	

	@Override
	public String toString() {
		return "Posicion= " + hitbox + ", bando=" + bando + "]";
	}

}
