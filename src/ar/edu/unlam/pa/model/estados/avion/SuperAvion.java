package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.Bala;
import ar.edu.unlam.pa.model.BalaAliada;
import ar.edu.unlam.pa.model.Punto2D;

public class SuperAvion extends EstadoAvion {

	@Override
	public Bala[] disparar(Punto2D posicionOrigen) {
		return new Bala[]{ new BalaAliada(posicionOrigen) };
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
