package ar.edu.unlam.pa.model.estados.avion;


import ar.edu.unlam.pa.model.Bala;
import ar.edu.unlam.pa.model.Punto2D;

public class AvionNormal extends EstadoAvion {

	@Override
	public Bala[] disparar(Punto2D posicionOrigen) {
		return new Bala[]{ new Bala(posicionOrigen) };
	}

	@Override
	public EstadoAvion agarraPowerUp() {
		return new SuperAvion();
	}

}
