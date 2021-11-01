package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Bala;
import ar.edu.unlam.pa.model.Elemento;
import ar.edu.unlam.pa.model.Escenario;


public class MegaAvion extends EstadoAvion {

	@Override
	public void disparar(AvionPlayer avion, Escenario escenario) {
		escenario.agregarElemento(new Bala(avion.getPosicion(), Elemento.BANDO.AMERICANO));
	}

	@Override
	public EstadoAvion terminaPowerUp() {
		return new SuperAvion();
	}

}
