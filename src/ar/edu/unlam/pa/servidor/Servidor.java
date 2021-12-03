package ar.edu.unlam.pa.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Servidor extends Thread {
	private static final short PUERTO = 7900;
	private ServerSocket serverSocket = null;
	private static List<ServerThread> serverThreads;
	private static GameLoopServidor juego;

	public Servidor() {
		serverThreads = new ArrayList<>();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(PUERTO);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Socket clientSocket = null;
		int id = 1;
		try {
			while (true) {
				clientSocket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(clientSocket, id++);
				serverThread.start();
				serverThreads.add(serverThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.stopServer();
		}
	}

	public void stopServer() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
				serverSocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void broadcast(String message) {
		serverThreads.removeIf(serverThread -> !serverThread.isAlive());
		serverThreads.forEach(serverThread -> serverThread.send(message));
	}

	public static long getGameTimeStart() {
		return juego.getTimeStart();
	}

	public static void main(String[] args) throws InterruptedException {
		Servidor servidor = new Servidor();
		servidor.start();
		juego = new GameLoopServidor();
		juego.run();
	}
}
