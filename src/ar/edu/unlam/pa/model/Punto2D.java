package ar.edu.unlam.pa.model;

import static java.lang.Math.hypot;

import ar.edu.unlam.pa.graficos.Ventana;

public class Punto2D {

	private double x;
	private double y;

	public Punto2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double distancia(Punto2D otro) {
		return hypot(this.x - otro.x, this.y - otro.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Punto2D other = (Punto2D) obj;

		return distancia(other) < 0.00001;
	}

	@Override
	public String toString() {
		return "(" + x + " , " + y + ")";
	}
	
	public boolean esPosicionDesbordada(double minX, double maxX, double minY, double maxY) {
		return (this.x < minX || this.x > maxX || this.y < minY || this.y > maxY);
	}
	
	public void desplazar(Double desplazamientoX, Double desplazamientoY) {
		
		this.x += desplazamientoX;
		this.y += desplazamientoY;
	}

}
