package ar.edu.unlam.pa.cliente;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.compartido.Mensaje;
import ar.edu.unlam.pa.model.AvionEnemigo;
import ar.edu.unlam.pa.model.AvionEnemigoJefe;
import ar.edu.unlam.pa.model.AvionEnemigoMedio;
import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.model.Isla;
import ar.edu.unlam.pa.model.Nube;

public class ProtocoloCliente {
	public static void processInput(String input) {
		Mensaje message = (new Gson()).fromJson(input, Mensaje.class);
		switch (message.getType()) {
		case NEW:
			processNew(message);
			break;
		case MOV:
			processMovement(message);
			break;
		case BYE:
			processQuit(message);
			break;
		case SNC:
			processSync(message);
			break;
		case ATK:
			processAttack(message);
			break;
		case ISL:
			processCreateIsland(message);
			break;
		case NUB:
			processCreateCloud(message);
			break;
		case SMA:
			processCreateSmall(message);
			break;
		case MED:
			processCreateMedium(message);
			break;
		case BOS:
			processCreateBoss(message);
			break;
		}
		
	}

	private static void processNew(Mensaje message) {
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) != null)
			return;
		
		Escenario.getInstance().agregarUsuario(message.getIdClient(),(String) message.getMessage());
	
	}

	private static void processMovement(Mensaje message) {
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) == null)
			return;
		
		Escenario.getInstance().obtenerJugador(message.getIdClient()).cambiarDireccion(DIRECCION.valueOf((String)message.getMessage()));
	}

	private static void processQuit(Mensaje message) {
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) == null)
			return;
		
		Escenario.getInstance().eliminarUsuario(message.getIdClient());
	}

	private static void processSync(Mensaje message) {
		AvionPlayer jugador = Escenario.getInstance().obtenerJugador(message.getIdClient());
		
		if(jugador != null)
			jugador.setInfo((String) message.getMessage());
	}
	
	private static void processAttack(Mensaje message) {
		if(Escenario.getInstance().obtenerJugador(message.getIdClient()) == null)
			return;
		
		Escenario.getInstance().obtenerJugador(message.getIdClient()).dispara((boolean) message.getMessage());
	}
	
	private static void processCreateIsland(Mensaje message) {
		String[] data = ((String)message.getMessage()).split("\\|");
		Escenario.getInstance().agregarElementoCapa1(
			new Isla(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Integer.parseInt(data[2])));
	}
	
	private static void processCreateCloud(Mensaje message) {
		String[] data = ((String)message.getMessage()).split("\\|");
		Escenario.getInstance().agregarElementoCapa2(
			new Nube(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Integer.parseInt(data[2])));
	}
	
	private static void processCreateSmall(Mensaje message) {
		String[] data = ((String)message.getMessage()).split("\\|");
		Escenario.getInstance().agregarElemento(
			new AvionEnemigo(Escenario.getInstance(),
				Double.parseDouble(data[0]), 
				Double.parseDouble(data[1]), 
				DIRECCION.valueOf(data[2]),
				Integer.parseInt(data[3])));
	}
	
	private static void processCreateMedium(Mensaje message) {
		String[] data = ((String)message.getMessage()).split("\\|");
		Escenario.getInstance().agregarElemento(
				new AvionEnemigoMedio(Escenario.getInstance(),
						Double.parseDouble(data[0]), 
						Double.parseDouble(data[1])));
	}
	
	private static void processCreateBoss(Mensaje message) {
		String[] data = ((String)message.getMessage()).split("\\|");
		Escenario.getInstance().agregarElemento(
				new AvionEnemigoJefe(Escenario.getInstance(),
						Double.parseDouble(data[0]), 
						Double.parseDouble(data[1])));
	}
}
