package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;

public class Animacion extends Elemento{
	private BufferedImage[] imagenes;
	private int index = 0;
	private static double INTERVALO_ITERACCION = 0.001;
	private double tiempoInteraccion = INTERVALO_ITERACCION;

	public Animacion(Hitbox hitbox,  double velocidad, BufferedImage[] imagenes){
		super(hitbox, DIRECCION.SUR, BANDO.JAPONES, velocidad, imagenes);
		this.imagenes = imagenes;
	}

	@Override
	public void actualizar(double dt) {
		if( tiempoInteraccion <= 0) {
			actualizarImagen(++index);
			tiempoInteraccion = INTERVALO_ITERACCION;
			
			if(index >= this.imagenes.length) 
				this.destruir();
		}else {
			tiempoInteraccion -= dt;
		}
	}
}
