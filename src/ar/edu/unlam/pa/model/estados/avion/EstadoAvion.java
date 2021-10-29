package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.Bala;
import ar.edu.unlam.pa.model.Punto2D;

public abstract class EstadoAvion {
	protected int duracion = 32;

	public Bala[] disparar(Punto2D posicionOrigen) {
		return null;
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
