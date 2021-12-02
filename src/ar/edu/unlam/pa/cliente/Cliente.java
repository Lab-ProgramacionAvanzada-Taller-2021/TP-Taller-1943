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
	private static final String IP = "localhost";
	private static Cliente INSTANCE = null;

	private Socket client;
	private PrintWriter output;
	private BufferedReader input;

	private int id;
	private long timeAsked;
	private long ping;
	private long elapsedTime;
	private long elapsedTimeSince;

	public Cliente() {
		INSTANCE = this;
	}

	public void connect() {
		try {
			client = new Socket(IP, PUERTO);
			output = new PrintWriter(client.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.id = Integer.parseInt(input.readLine());
/*
			// Get ping each second
			ScheduledThreadPoolExecutor executorServicePing = new ScheduledThreadPoolExecutor(1);
			Runnable processPing = () -> {
				askPing();
			};
			executorServicePing.scheduleWithFixedDelay(processPing, 0, 1, TimeUnit.SECONDS);
*/
			// Get sync every ten seconds
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

	public void askPing() {
		this.timeAsked = System.nanoTime();
		this.send(TipoMensaje.PNG);
	}

	public void synchronize() {
		this.send(TipoMensaje.SNC);
	}

	public void refreshPing() {
		this.ping = (System.nanoTime() - Cliente.getInstance().getTimeAsked()) / 1_000_000;
	}

	public long getTimeAsked() {
		return timeAsked;
	}

	public long getPing() {
		return ping;
	}

	public long getId() {
		return id;
	}

	public long getGameTimeStart() {
		return System.nanoTime() - elapsedTimeSince + elapsedTime;
	}

	public void setGameTimeStart(long elapsedTime) {
		this.elapsedTime = elapsedTime;
		this.elapsedTimeSince = System.nanoTime();
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
