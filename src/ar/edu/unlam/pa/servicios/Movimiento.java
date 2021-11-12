package ar.edu.unlam.pa.servicios;

public interface Movimiento {
	public final Double DESPLAZAMIENTO_DIAGONAL  = 0.707;
	
	/***
	 * 
	 * @param desplazamientoX_del_elemento.
	 * @param desplazamientoY_del_elemento.
	 * @return void
	 */
	public void moverEnDireccion(double desplazamientoX, double desplazamientoY);
}
