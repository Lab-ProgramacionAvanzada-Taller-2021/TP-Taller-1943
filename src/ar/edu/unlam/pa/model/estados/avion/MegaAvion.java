package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.model.BalaAliada;
import ar.edu.unlam.pa.model.Escenario;


public class MegaAvion extends EstadoAvion {
	
	public MegaAvion() {
		duracion = DURACION_EFECTO;
	}

	@Override
	public void disparar(AvionPlayer avion, Escenario escenario) {
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORTE));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NOROESTE));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORESTE));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SUR));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SUROESTE));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SURESTE));
	}
	
	@Override
	public EstadoAvion agarraPowerUp() {
		duracion = DURACION_EFECTO;
		return this;
	}

	@Override
	public EstadoAvion terminaPowerUp() {
		return new SuperAvion();
	}

}
