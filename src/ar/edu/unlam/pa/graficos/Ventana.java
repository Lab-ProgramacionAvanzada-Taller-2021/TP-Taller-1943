package ar.edu.unlam.pa.graficos;

import javax.swing.JFrame;

import ar.edu.unlam.pa.cliente.Client;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.servidor.NetworkMessageType;

public class Ventana extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Constantes_del_GameLoop
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 60;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	
	public static final int ANCHO = 640;
	public static final int ALTO = 640;
	private final String RUTA_IMAGENES = "Recursos/Imagenes/";
	private final String RUTA_ANIMACIONES = "Recursos/Animaciones/";
	
	public Client client;
	private Pantalla pantalla;
	private Thread hilo;
	private boolean enEjecucion;
	
	public Escenario escenario;

	public Ventana(Client client) {
		this.client = client;
		setTitle("1943 Midway");
		
		this.pantalla = new Pantalla(this);
		this.setContentPane(pantalla);
		
		pack();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				cerrar();
				dispose();
			}
		});
	}
	
	public void cargar() {
		Grafico.cargarGraficos(RUTA_IMAGENES);
		Grafico.cargarAnimaciones(RUTA_ANIMACIONES);
		
		escenario = Escenario.getInstance();
		//escenario.iniciar();
		//escenario.cargarTeclasDeLosJugadores(this);
		
		iniciar();
		
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	// GameLoop_de_la_catedra.
	@Override
	public void run() {
		long next_game_tick = System.currentTimeMillis();
		long next_game_frame = System.currentTimeMillis();

		while (enEjecucion) {
			if (System.currentTimeMillis() > next_game_tick) {
				next_game_tick += SKIP_TICKS;
				actualizar();
			}
			if (System.currentTimeMillis() > next_game_frame) {
				next_game_frame += SKIP_FRAMES;
				dibujar();
			}
		}
		
		client.send(NetworkMessageType.BYE);
		System.exit(0);
	}
	
	public void iniciar() {
		this.hilo = new Thread(this);
		this.hilo.start();
		this.enEjecucion = true;
	}
	
	public void cerrar() {
		enEjecucion = false;
	}
	
	public void dibujar() {
		pantalla.repaint();
	}
	
	public void actualizar() {
		escenario.actualizar(1.0 / TICKS_PER_SECOND);
	}
}
