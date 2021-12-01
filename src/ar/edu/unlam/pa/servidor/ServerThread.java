package ar.edu.unlam.pa.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;

//import shared.BallList;
//import shared.NetworkMessage;
//import shared.NetworkMessageType;

public class ServerThread extends Thread {
	private BufferedReader input;
	private PrintWriter output;
	public int id;

	public ServerThread(Socket clientSocket, int id) {
		try {
			this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.output = new PrintWriter(clientSocket.getOutputStream(), true);
			this.id = id;
			this.send("" + id);
			Escenario.getInstance().agregarUsuario(id);
			for (AvionPlayer jugador : Escenario.getInstance().obtenerJugadores()) {
				this.send((new Gson()).toJson(new NetworkMessage(NetworkMessageType.NEW, jugador.getId(), jugador.getInfo())));
			}
		} catch (IOException e) {
			System.out.println("Se desconecto el cliente " + this.id + " cuando estaba iniciando");
			e.printStackTrace();
			this.close();
		}
	}

	@Override
	public void run() {
		try {
			String inputLine;
			while ((inputLine = input.readLine()) != null) {
				ServerProtocol.processInput(this, inputLine);
			}
		} catch (IOException e) {
			if (!this.isInterrupted()) {
				System.out.println("Se desconecto incorrectamente el cliente " + this.id);
				e.printStackTrace();
				this.close();
			}
		}
	}

	public void send(String message) {
		try {
			output.println(message);
		} catch (Exception e) {
			System.out.println("Se desconecto incorrectamente el cliente " + this.id + ".");
			e.printStackTrace();
			this.close();
		}
	}

	public void close() {
		this.interrupt();
		Escenario.getInstance().eliminarUsuario(id);
		Server.broadcast((new Gson()).toJson(new NetworkMessage(NetworkMessageType.BYE, id)));
	}
}
