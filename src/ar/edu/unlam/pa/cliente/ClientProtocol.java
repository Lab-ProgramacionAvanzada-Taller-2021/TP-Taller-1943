package ar.edu.unlam.pa.cliente;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.servidor.NetworkMessage;
import ar.edu.unlam.pa.servidor.NetworkMessageType;

public class ClientProtocol {
	public static void processInput(String input) {
		NetworkMessage message = (new Gson()).fromJson(input, NetworkMessage.class);
		switch (message.getType()) {
		case NEW:
			processNew(message);
			break;
		case MSG:
			processMessage(message);
			break;
		case MOV:
			processMovement(message);
			break;
		case PAU:
			processPause(message);
			break;
		case BYE:
			processQuit(message);
			break;
		case PNG:
			processPing(message);
			break;
		case SNC:
			processSync(message);
			break;
		}
	}

	private static void processNew(NetworkMessage message) {
		if((String) message.getMessage() != null)
			Escenario.getInstance().agregarUsuario(message.getIdClient(), (String) message.getMessage());
		else
			Escenario.getInstance().agregarUsuario(message.getIdClient());
	}

	private static void processMessage(NetworkMessage message) {
		System.out.println(message.getIdClient() + ": " + (String) message.getMessage());
	}

	private static void processMovement(NetworkMessage message) {
		Escenario.getInstance().obtenerJugador(message.getIdClient()).cambiarDireccion(DIRECCION.valueOf((String)message.getMessage()));
	}

	private static void processPause(NetworkMessage message) {

	}

	private static void processQuit(NetworkMessage message) {
		Escenario.getInstance().eliminarUsuario(message.getIdClient());
	}

	private static void processPing(NetworkMessage message) {
		Client.getInstance().refreshPing();
	}

	private static void processSync(NetworkMessage message) {
		AvionPlayer player = Escenario.getInstance().obtenerJugador(message.getIdClient());
		
		if(player != null)
			player.setInfo((String) message.getMessage());
		//Double elapsedTime = (Double) message.getMessage();
		//Client.getInstance().setGameTimeStart(elapsedTime.longValue());
	}
}
