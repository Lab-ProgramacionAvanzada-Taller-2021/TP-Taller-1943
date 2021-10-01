package ar.edu.unlam.pa.model.estados.avion;

public class MegaAvion extends EstadoAvion {

	@Override
	public void disparar() {
		getBala().setCantidadTiros(4);
	}

	@Override
	public EstadoAvion terminaPowerUp() {

		return new SuperAvion();
	}

}
