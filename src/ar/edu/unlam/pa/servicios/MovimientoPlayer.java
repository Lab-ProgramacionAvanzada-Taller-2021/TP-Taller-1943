package ar.edu.unlam.pa.servicios;

public interface MovimientoPlayer {
	public final Double DESPLAZAMIENTO_DIAGONAL  = 0.707;
	public final Double DESPLAZAMIENTO_LINEAL = 1.000;
	
	public boolean moverArriba();

	public boolean moverAbajo();

	public boolean moverDerecha();

	public boolean moverIzquierda();

	public boolean moverArribaDerecha();

	public boolean moverArribaIzquierda();

	public boolean moverAbajoDerecha();

	public boolean moverAbajoIzquierda();

}
