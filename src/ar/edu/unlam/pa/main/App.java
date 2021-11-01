package ar.edu.unlam.pa.main;

import ar.edu.unlam.pa.graficos.Ventana;


public class App{

	public static void main(String[] args) {
		Ventana juego = new Ventana(); 
		juego.cargar();
		juego.iniciar();
	}
}
