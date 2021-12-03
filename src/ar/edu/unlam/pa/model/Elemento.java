package ar.edu.unlam.pa.model;

import java.awt.Graphics2D;
import java.io.Serializable;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.servicios.Movimiento;

public class Elemento implements Movimiento {
	private Hitbox hitbox;
	private BANDO bando;
	protected double velocidad;
	private boolean destruido = false;
	protected DIRECCION direccion;
	protected String nombreImagen;
	private int index = 0;

	public enum DIRECCION{
		NORTE,
		NORESTE,
		ESTE,
		SURESTE,
		SUR,
		SUROESTE,
		OESTE,
		NOROESTE,
		CENTRO
	}
	
	public static enum BANDO{
		AMERICANO,
		JAPONES,
		NEUTRAL
	}

	public Elemento(Hitbox hitbox, DIRECCION direccion, BANDO bando, double velocidad, String nombreImagen) {
		this.hitbox = hitbox;
		this.bando = bando;
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.nombreImagen = nombreImagen;
	}

	public Punto2D getPosicion() {
		return hitbox.getPosicion();
	}

	public boolean esEnemigo(Elemento otro) {
		return bando != otro.bando;
	}

	public boolean colisionaCon(Elemento otro) {
		return esEnemigo(otro) && hitbox.colisionaCon(otro.hitbox) && !destruido;
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

	public void actualizarPosicion(double x, double y) {
		hitbox.actualizarPunto(x, y);
	}
	
	@Override
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY) {
		if(desplazamientoX != 0 && desplazamientoY != 0) {
			desplazamientoX *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
			desplazamientoY *= Movimiento.DESPLAZAMIENTO_DIAGONAL;
		}
		
		hitbox.moverPunto(desplazamientoX*velocidad*2, desplazamientoY*velocidad*2);
	}

	public void dibujar(Graphics2D g2) {
		g2.drawImage(Grafico.obtenerGrafico(nombreImagen)[index], (int)(this.hitbox.getExtremoIzq()), (int)(this.hitbox.getExtremoSup()) , 
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
			case ESTE:
				moverEnDireccion(dt, 0);
				break;
			case OESTE:
				moverEnDireccion(-dt, 0);
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
				index = 0;
				break;
			case NORESTE:
				index = 1;
				break;
			case ESTE:
				index = 2;
				break;
			case SURESTE:
				index = 3;
				break;
			case SUR:
				index = 4;
				break;
			case SUROESTE:
				index = 5;
				break;
			case OESTE:
				index = 6;
				break;
			case NOROESTE:
				index = 7;
				break;
			default:
				break;	
		}	
	}
	
	public void cambiarDireccion(DIRECCION direccion) {
		this.direccion = direccion;
	}
	
	protected void actualizarImagen(int index) {
		this.index = index;
	}
	
	public DIRECCION calcularDireccion(Punto2D otroPos) {
		Punto2D pos = hitbox.getPosicion();
		int x = (int)(otroPos.getX() - pos.getX());
		int y = (int)(otroPos.getY() - pos.getY());
		
		if(y > 0 && x > 0) 
			return DIRECCION.SURESTE;
		if(y > 0 && x < 0)
			return DIRECCION.SUROESTE;
		if(y < 0 && x > 0) 
			return DIRECCION.NORESTE;
		else
			return DIRECCION.NOROESTE;
	}

	public String getInfo() {
		return getPosicion().getX() + "|" + getPosicion().getY() + "|" + direccion;
	}

	public void setInfo(String info) {
		String[] data = info.split("\\|");
		actualizarPosicion(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		
		if(data[2] != null)
			cambiarDireccion(DIRECCION.valueOf(data[2]));
	}
	
	@Override
	public String toString() {
		return "Posicion= " + hitbox + ", bando=" + bando + "]";
	}

}
