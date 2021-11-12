package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Bala extends Elemento{
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
	
	private DIRECCION direccion;
	
	public Bala(Punto2D posicion, BANDO bando, double velocidad, double radio, DIRECCION direccion, BufferedImage imagen) {
		super(new Hitbox(posicion ,radio), 
				bando, 
				velocidad, 
				imagen
			);
		this.direccion = direccion;
	}
	
	@Override
	public void actualizar(double dt) {
		
		switch(this.direccion) {
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
		
		super.actualizar(dt);
	}
	
	@Override
	public String toString() {
		return "Bala";
	}

}
