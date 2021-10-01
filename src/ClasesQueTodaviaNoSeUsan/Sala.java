package ClasesQueTodaviaNoSeUsan;

import java.util.LinkedList;

public class Sala {
 
	String nombreDeSala;
	private int idSala;
	private LinkedList<Usuario> jugadoresEnSala = new LinkedList<Usuario>();
	
	public void crearPartida(Sala s1) {
		
	}
	public void quitarJugadorDeSala(Usuario s1) {
	
	}
	public void mostrarJugadores() {
		
	}
	
	public LinkedList<Usuario> getJugadoresEnSala() {
		return jugadoresEnSala;
	}


}