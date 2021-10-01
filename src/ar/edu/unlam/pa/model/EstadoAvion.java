package ar.edu.unlam.pa.model;

public abstract class EstadoAvion {
	private int duracion = 32;

	public void disparar() {}
	
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
