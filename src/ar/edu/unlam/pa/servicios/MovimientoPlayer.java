package ar.edu.unlam.pa.servicios;

public interface MovimientoPlayer {
	/*
	 * Son los unicos movientos posibles para los juagadores
	 */
	public boolean moverArriba();

	public boolean moverAbajo();

	public boolean moverDerecha();

	public boolean moverIzquierda();

	public boolean moverArribaDerecha();

	public boolean moverArribaIzquierda();

	public boolean moverAbajoDerecha();

	public boolean moverAbajoIzquierda();

}
