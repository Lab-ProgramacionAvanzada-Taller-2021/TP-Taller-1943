package ar.edu.unlam.pa.cliente;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.compartido.Mensaje;
import ar.edu.unlam.pa.compartido.TipoMensaje;
import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;

public class ProtocoloCliente {
	public static void processInput(String input) {
		Mensaje message = (new Gson()).fromJson(input, Mensaje.class);
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

	private static void processNew(Mensaje message) {
		int num = ((Double) message.getMessage()).intValue();
		
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) != null)
			return;
		
		switch(num) {
			case 1:
				Escenario.getInstance().agregarJugador(AvionPlayer.crearJugador1(Escenario.getInstance(), message.getIdClient()));
				break;
			case 2:
				Escenario.getInstance().agregarJugador(AvionPlayer.crearJugador2(Escenario.getInstance(), message.getIdClient()));
				break;
			case 3:
				Escenario.getInstance().agregarJugador(AvionPlayer.crearJugador3(Escenario.getInstance(), message.getIdClient()));
				break;
			default:
				Escenario.getInstance().agregarJugador(AvionPlayer.crearJugador4(Escenario.getInstance(), message.getIdClient()));
				break;
		}
		//Escenario.getInstance().agregarJugador(message.getMessage());
	}

	private static void processMessage(Mensaje message) {
		System.out.println(message.getIdClient() + ": " + (String) message.getMessage());
	}

	private static void processMovement(Mensaje message) {
		Escenario.getInstance().obtenerJugador(message.getIdClient()).cambiarDireccion(DIRECCION.valueOf((String)message.getMessage()));
	}

	private static void processPause(Mensaje message) {

	}

	private static void processQuit(Mensaje message) {
		Escenario.getInstance().eliminarUsuario(message.getIdClient());
	}

	private static void processPing(Mensaje message) {
		Cliente.getInstance().refreshPing();
	}

	private static void processSync(Mensaje message) {
		AvionPlayer player = Escenario.getInstance().obtenerJugador(message.getIdClient());
		
		if(player != null)
			player.setInfo((String) message.getMessage());
		//Double elapsedTime = (Double) message.getMessage();
		//Client.getInstance().setGameTimeStart(elapsedTime.longValue());
	}
}
