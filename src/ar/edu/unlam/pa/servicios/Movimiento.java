package ar.edu.unlam.pa.servicios;

public interface Movimiento {
	/*
	 * Movimiento generico del cualquier elemento , hay que tener en cuenta la
	 * velocidad
	 * 
	 * @param angulo en decimal
	 * 
	 * @return true porque no sabemos el tamaño del mapa
	 */
	public boolean moverEnDireccion(double angulo);
}
