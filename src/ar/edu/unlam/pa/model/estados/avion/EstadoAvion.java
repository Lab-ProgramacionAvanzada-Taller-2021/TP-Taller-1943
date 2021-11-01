package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;

public abstract class EstadoAvion {
	protected int duracion = 32;

	public void disparar(AvionPlayer avion, Escenario escenario) {
	}

	public EstadoAvion agarraPowerUp() {
		return this;
	}

	public EstadoAvion terminaPowerUp() {
		return this;
	}

	public boolean tienePowerUp() {
		return (--this.duracion > 0);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
