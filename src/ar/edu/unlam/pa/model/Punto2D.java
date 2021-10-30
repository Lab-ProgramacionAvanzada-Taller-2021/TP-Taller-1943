package ar.edu.unlam.pa.model;

import static java.lang.Math.hypot;

public class Punto2D {

	private double x;
	private double y;

	public Punto2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Punto2D(Punto2D punto) {
		this.x = punto.x;
		this.y = punto.y;
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
	
	public void desplazar(Double desplazamientoX, Double desplazamientoY) {
		this.x += desplazamientoX;
		this.y += desplazamientoY;
	}

}
