package client;

import ar.edu.unlam.pa.graficos.Ventana;
import server.Global;

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
