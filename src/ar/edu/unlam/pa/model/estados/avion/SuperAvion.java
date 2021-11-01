package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Bala;
import ar.edu.unlam.pa.model.Escenario;

public class SuperAvion extends EstadoAvion {

	@Override

	public void disparar(AvionPlayer avion, Escenario escenario) {
		escenario.agregarElemento(new Bala(avion.getPosicion()));
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
