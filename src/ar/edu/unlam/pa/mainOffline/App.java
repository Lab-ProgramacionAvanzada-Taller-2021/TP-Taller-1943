package ar.edu.unlam.pa.mainOffline;

import ar.edu.unlam.pa.graficos.Ventana;

public class App{
	public static void main(String[] args) {
		Ventana juego = new Ventana(null); 
		juego.cargar();
 	    juego.iniciar();
	}
}
