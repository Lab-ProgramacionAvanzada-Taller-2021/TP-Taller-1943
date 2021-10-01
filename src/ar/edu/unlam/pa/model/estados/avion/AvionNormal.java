package ar.edu.unlam.pa.model.estados.avion;

public class AvionNormal extends EstadoAvion {

	@Override
	public void disparar() {
		getBala().setCantidadTiros(2);
	}

	@Override
	public EstadoAvion agarraPowerUp() {

		return new SuperAvion();
	}

}
