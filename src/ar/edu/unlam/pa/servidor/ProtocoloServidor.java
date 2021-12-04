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
			case MOV:
				processMovement(caller, message);
				break;
			case BYE:
				processQuit(caller, message);
				break;
			case SNC:
				processSync(caller, message);
				break;
			case ATK:
				processAttack(caller, message);
				break;
			case ISL:
				break;
			case NUB:
				break;
			case SMA:
				break;
			case BOS:
				break;
			case MED:
				break;
			default:
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
				new Mensaje(TipoMensaje.NEW, jugador.getId(), jugador.getNroJugador()+"|"+jugador.getInfo())));
	}

	private static void processMovement(ServerThread caller, Mensaje message) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Runnable processMovementDelay = () -> {
			Escenario.getInstance().obtenerJugador(caller.id).cambiarDireccion(
					DIRECCION.valueOf((String) message.getMessage()));
			Servidor.broadcast((
					new Gson()).toJson(new Mensaje(TipoMensaje.MOV, caller.id, message.getMessage())));
		};

		executorService.schedule(processMovementDelay, 10, TimeUnit.MILLISECONDS);
		executorService.shutdown();
	}

	private static void processQuit(ServerThread caller, Mensaje message) {
		caller.close();
	}

	private static void processSync(ServerThread caller, Mensaje message) {
		for (AvionPlayer jugador : Escenario.getInstance().obtenerJugadores()) 
			caller.send((
				new Gson()).toJson(new Mensaje(TipoMensaje.SNC, jugador.getId(), jugador.getInfo())));
	}
	
	private static void processAttack(ServerThread caller, Mensaje message) {
		Escenario.getInstance().obtenerJugador(caller.id).dispara((boolean) message.getMessage());
		
		Servidor.broadcast((
			new Gson()).toJson(new Mensaje(TipoMensaje.ATK, caller.id, message.getMessage())));
	}
}
