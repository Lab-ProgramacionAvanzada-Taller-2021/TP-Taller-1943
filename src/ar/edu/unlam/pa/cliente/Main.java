package ar.edu.unlam.pa.cliente;

import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.servidor.Global;

public class Main {

	public static void main(String[] args) throws Exception {
		Client client = new Client(Global.IP, Global.PORT);
		client.connect();
		Thread serverListener = new ServerListener(client);
		serverListener.start();
		Ventana game = new Ventana(client);
		game.iniciar();
		game.run();
	}
}
