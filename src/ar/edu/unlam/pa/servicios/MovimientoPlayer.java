package ar.edu.unlam.pa.servicios;

public interface MovimientoPlayer {
	public final Double DESPLAZAMIENTO_DIAGONAL  = 0.707;
	
	public void moverArriba(double dt);

	public void moverAbajo(double dt);

	public void moverDerecha(double dt);

	public void moverIzquierda(double dt);

	public void moverArribaDerecha(double dt);

	public void moverArribaIzquierda(double dt);

	public void moverAbajoDerecha(double dt);

	public void moverAbajoIzquierda(double dt);

}
