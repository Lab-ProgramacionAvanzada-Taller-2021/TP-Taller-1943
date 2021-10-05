package ar.edu.unlam.pa.servicios;

public interface Movimiento {
	/***
	 * 
	 * @param desplazamientoX_del_elemento.
	 * @param desplazamientoY_del_elemento.
	 * @return Si_se_movio_o_no
	 */
	public boolean moverEnDireccion(double desplazamientoX, double desplazamientoY);
}
