package ar.edu.unlam.pa.model.estadosAvion;

import ar.edu.unlam.pa.model.EstadoAvion;

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
