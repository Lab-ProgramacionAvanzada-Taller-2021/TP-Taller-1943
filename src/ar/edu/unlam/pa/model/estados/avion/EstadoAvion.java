package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;

public abstract class EstadoAvion {
	public static int DURACION_EFECTO = 3600;
	protected int duracion = 0;

	public void disparar(AvionPlayer avion, Escenario escenario) {
	}

	public EstadoAvion agarraPowerUp() {
		return this;
	}

	public EstadoAvion terminaPowerUp() {
		return this;
	}

	public boolean tienePowerUp(double dt) {
		return ((duracion -= dt) > 0);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "   " + (int)(duracion/100);
	}
}
