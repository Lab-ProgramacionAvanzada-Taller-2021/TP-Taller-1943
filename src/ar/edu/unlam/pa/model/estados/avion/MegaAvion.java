package ar.edu.unlam.pa.model.estados.avion;

public class MegaAvion extends EstadoAvion {

	@Override
	public void disparar() {
		//Dispara mega proyectiles.
	}

	@Override
	public EstadoAvion terminaPowerUp() {
		return new SuperAvion();
	}

}
