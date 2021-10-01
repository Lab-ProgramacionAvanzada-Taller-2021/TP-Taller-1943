package ar.edu.unlam.pa.model.estados.avion;

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
