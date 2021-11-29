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
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NOROESTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORESTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SUR, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SUROESTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.SURESTE, avion));
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
