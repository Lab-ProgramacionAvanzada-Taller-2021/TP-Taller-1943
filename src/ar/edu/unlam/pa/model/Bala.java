package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.ColorConvertOp;

import ar.edu.unlam.pa.graficos.Grafico;

import java.awt.color.ColorSpace;

public class Bala extends Elemento{
	
	public Bala(Punto2D posicion, BANDO bando, double velocidad, double radio, DIRECCION direccion, String nombreImagen) {
		super(new Hitbox(posicion ,radio), 
				direccion,
				bando, 
				velocidad, 
				nombreImagen
			);
		
		actualizarImagen();
	}
	
	public void colisiono(Elemento otro) {
		if(otro instanceof Bala)
			return;
		
		destruir();
	}
	
	@Override
	public String toString() {
		return "Bala";
	}

}
