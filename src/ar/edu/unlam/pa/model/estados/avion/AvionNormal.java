package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Bala;

import ar.edu.unlam.pa.model.Escenario;


public class AvionNormal extends EstadoAvion {

	@Override

	public void disparar(AvionPlayer avion, Escenario escenario) {
		escenario.agregarElemento(new Bala(avion.getPosicion()));
	}

	@Override
	public EstadoAvion agarraPowerUp() {
		return new SuperAvion();
	}

}
