package ar.edu.unlam.pa.model;

import java.util.ArrayList;
import java.util.List;

public class Escenario {

	private List<Elemento> listaElementos;
	
	public Escenario() {
		listaElementos = new ArrayList<Elemento>();
	}

	public void agregarElemento(Elemento nuevo) {
		listaElementos.add(nuevo);
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public String mostrarTodosLosElementos() {
		String lista = "";
		int cantElementos = listaElementos.size();
		
		for(int i=0; i<cantElementos; i++) {
			lista = lista.concat(listaElementos.get(i).toString());
			if(i < cantElementos-1)
				lista = lista.concat(System.lineSeparator());
		}
		
		return lista;
	}
	
}
