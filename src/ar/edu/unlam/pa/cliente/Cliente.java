package ar.edu.unlam.pa.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import ar.edu.unlam.pa.compartido.Mensaje;
import ar.edu.unlam.pa.compartido.TipoMensaje;
import ar.edu.unlam.pa.graficos.Ventana;

public class Cliente {
	private static final short PUERTO = 7900;
//	private static final String IP = "0.0.0.0";
//	private static final String IP ="192.168.0.77";
	private static final String IP = "localhost";
	private static Cliente INSTANCE = null;

	private Socket client;
	private PrintWriter output;
	private BufferedReader input;

	private int id;

	public Cliente() {
		INSTANCE = this;
	}

	public void connect() {
		try {
			client = new Socket(IP, PUERTO);
			System.out.println("Conectado a :" + client.getInetAddress().getHostName());
			output = new PrintWriter(client.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.id = Integer.parseInt(input.readLine());

			ScheduledThreadPoolExecutor executorServiceSync = new ScheduledThreadPoolExecutor(1);
			Runnable processSync = () -> {
				synchronize();
			};
			executorServiceSync.scheduleWithFixedDelay(processSync, 0, 20, TimeUnit.SECONDS);

			this.send(TipoMensaje.NEW);
		} catch (Exception ex) {
			System.out.println("Fallo al recibir del servidor");
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public BufferedReader getBufferedReader() throws IOException {
		return this.input;
	}

	public void send(TipoMensaje type, Object message) {
		output.println((new Gson()).toJson(new Mensaje(type, message)));
	}

	public void send(TipoMensaje type) {
		this.send(type, null);
	}

	public void synchronize() {
		this.send(TipoMensaje.SNC);
	}


	public long getId() {
		return id;
	}

	public static Cliente getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) throws Exception {
		Cliente client = new Cliente();
		client.connect();
		Thread serverListener = new ServerListener(client);
		serverListener.start();
		Ventana juego = new Ventana(client);
		juego.cargar();
		juego.run();
	}
}
