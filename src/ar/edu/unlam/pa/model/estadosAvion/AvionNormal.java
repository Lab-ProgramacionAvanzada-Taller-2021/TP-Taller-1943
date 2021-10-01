package ar.edu.unlam.pa.model.estadosAvion;

import ar.edu.unlam.pa.model.EstadoAvion;

public class AvionNormal extends EstadoAvion{
	
	@Override
	public void disparar() {
		//Dispara proyectiles normales.
	}

	@Override
	public EstadoAvion agarraPowerUp() {
		return new SuperAvion();
	}
	
}
