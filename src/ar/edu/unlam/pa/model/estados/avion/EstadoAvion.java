package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.elementos.Bala;

public abstract class EstadoAvion {
	protected int duracion = 32;
	private Bala bala = new Bala();

	public void disparar() {
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

	public Bala getBala() {
		return bala;
	}

	public void setBala(Bala bala) {
		this.bala = bala;
	}

}
