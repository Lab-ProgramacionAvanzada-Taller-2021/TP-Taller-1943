package ar.edu.unlam.pa.cliente;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.servidor.ServerThread;
import ar.edu.unlam.pa.servidor.Servidor;
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
		case ATK:
			processAttack(message);
		}
	}

	private static void processNew(Mensaje message) {
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) != null)
			return;
		
		Escenario.getInstance().agregarUsuario(message.getIdClient(),(String) message.getMessage());
	
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
		AvionPlayer jugador = Escenario.getInstance().obtenerJugador(message.getIdClient());
		
		if(jugador != null)
			jugador.setInfo((String) message.getMessage());
	}
	
	private static void processAttack(Mensaje message) {
		Escenario.getInstance().obtenerJugador(message.getIdClient()).dispara((boolean) message.getMessage());
	}
}
