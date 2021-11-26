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
	protected DIRECCION direccion;
	protected BufferedImage[] imagenes;
	protected BufferedImage imagen;

	public enum DIRECCION{
		NORTE,
		NORESTE,
		ESTE,
		SURESTE,
		SUR,
		SUROESTE,
		OESTE,
		NOROESTE
	}
	
	public static enum BANDO{
		AMERICANO,
		JAPONES,
		NEUTRAL
	}

	public Elemento(Hitbox hitbox, DIRECCION direccion, BANDO bando, double velocidad, BufferedImage[] imagenes) {
		this.hitbox = hitbox;
		this.bando = bando;
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.imagenes = imagenes;
		this.imagen = imagenes[0];
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
		switch(direccion) {
			case NORTE:
				moverEnDireccion(0, -dt);
				break;
			case NOROESTE:
				moverEnDireccion(-dt, -dt);
				break;
			case NORESTE:
				moverEnDireccion(dt, -dt);
				break;
			case SUR:
				moverEnDireccion(0, dt);
				break;
			case SUROESTE:
				moverEnDireccion(-dt, dt);
				break;
			case SURESTE:
				moverEnDireccion(dt, dt);
				break;
			default:
				break;	
		}
			
		if(estaFueraDeRango())
			destruir();
	}
	
	protected void actualizarImagen() {
		switch(direccion) {
			case NORTE:
				this.imagen = this.imagenes[0];
				break;
			case NORESTE:
				this.imagen = this.imagenes[1];
				break;
			case ESTE:
				this.imagen = this.imagenes[2];
				break;
			case SURESTE:
				this.imagen = this.imagenes[3];
				break;
			case SUR:
				this.imagen = this.imagenes[4];
				break;
			case SUROESTE:
				this.imagen = this.imagenes[5];
				break;
			case OESTE:
				this.imagen = this.imagenes[6];
				break;
			case NOROESTE:
				this.imagen = this.imagenes[7];
				break;
			default:
				break;	
		}	
	}

	@Override
	public String toString() {
		return "Posicion= " + hitbox + ", bando=" + bando + "]";
	}

}
