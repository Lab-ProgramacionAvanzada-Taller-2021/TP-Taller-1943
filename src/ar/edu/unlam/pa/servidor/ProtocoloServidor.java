package ar.edu.unlam.pa.servidor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.compartido.Mensaje;
import ar.edu.unlam.pa.compartido.TipoMensaje;
import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Elemento.DIRECCION;



public class ProtocoloServidor {
	public static void processInput(ServerThread caller, String input) {
		try {
			Mensaje message = (new Gson()).fromJson(input, Mensaje.class);
			switch (message.getType()) {
			case NEW:
				processNew(caller, message);
				break;
			case MSG:
				processMessage(caller, message);
				break;
			case MOV:
				processMovement(caller, message);
				break;
			case PAU:
				processPause(caller, message);
				break;
			case BYE:
				processQuit(caller, message);
				break;
			case PNG:
				processPing(caller, message);
				break;
			case SNC:
				processSync(caller, message);
				break;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private static void processNew(ServerThread caller, Mensaje message) {
		Escenario.getInstance().agregarUsuario(caller.id);
		
		for(AvionPlayer jugador : Escenario.getInstance().obtenerJugadores())
			Servidor.broadcast((new Gson()).toJson(
					new Mensaje(TipoMensaje.NEW, jugador.getId(), jugador.getNroJugador())));
		
		
		/*Servidor.broadcast((new Gson()).toJson(
			new Mensaje(TipoMensaje.NEW, caller.id, message.getMessage())));*/
	}

	private static void processMessage(ServerThread caller, Mensaje message) {
		Servidor.broadcast((new Gson()).toJson(
			new Mensaje(TipoMensaje.MSG, caller.id, message.getMessage())));
	}

	// This method generate a delay of 20 ms to balance the movements against high
	// values of ping
	private static void processMovement(ServerThread caller, Mensaje message) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Runnable processMovementDelay = () -> {
			DIRECCION direccion = DIRECCION.valueOf((String) message.getMessage());
			AvionPlayer jugador = Escenario.getInstance().obtenerJugador(caller.id);
			jugador.cambiarDireccion(direccion);
			Servidor.broadcast(
					(new Gson()).toJson(new Mensaje(TipoMensaje.MOV, caller.id, message.getMessage())));
		};

		executorService.schedule(processMovementDelay, 10, TimeUnit.MILLISECONDS);
		executorService.shutdown();
	}

	private static void processPause(ServerThread caller, Mensaje message) {
		// TODO Game pause
	}

	private static void processQuit(ServerThread caller, Mensaje message) {
		caller.close();
	}

	private static void processPing(ServerThread caller, Mensaje message) {
		caller.send((new Gson()).toJson(new Mensaje(TipoMensaje.PNG)));
	}

	private static void processSync(ServerThread caller, Mensaje message) {
		for (AvionPlayer jugador : Escenario.getInstance().obtenerJugadores()) {
			Servidor.broadcast((new Gson()).toJson(new Mensaje(TipoMensaje.SNC, jugador.getId(), jugador.getInfo())));
		}
		
		// This could be used to synchronize everything, such as new players and players
		// who left the game
		//long gameTime = System.nanoTime() - Server.getGameTimeStart();
		//caller.send((new Gson()).toJson(new NetworkMessage(NetworkMessageType.SNC, gameTime)));
	}

}
