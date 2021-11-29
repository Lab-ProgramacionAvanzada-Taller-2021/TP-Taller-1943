package ar.edu.unlam.pa.model.estados.avion;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.BalaAliada;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.model.Elemento.DIRECCION;

public class SuperAvion extends EstadoAvion {

	public SuperAvion(){
		duracion = DURACION_EFECTO;
	}
	
	@Override
	public void disparar(AvionPlayer avion, Escenario escenario) {
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NOROESTE, avion));
		escenario.agregarElemento(new BalaAliada(avion.getPosicion(), DIRECCION.NORESTE, avion));
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
