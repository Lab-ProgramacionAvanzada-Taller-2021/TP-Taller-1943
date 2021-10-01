package ar.edu.unlam.pa.model.estados.avion;

public class SuperAvion extends EstadoAvion {

	@Override
	public void disparar() {
		//Dispara super proyectiles.
	}
	
	@Override
	public EstadoAvion agarraPowerUp() {
		return new MegaAvion();
	}
	
	@Override
	public EstadoAvion terminaPowerUp() {
		return new AvionNormal();
	}
	

}
