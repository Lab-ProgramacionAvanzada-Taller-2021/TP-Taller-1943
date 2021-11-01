package ar.edu.unlam.pa.graficos;

import javax.swing.JFrame;

import ar.edu.unlam.pa.model.Escenario;

public class Ventana extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Constantes_del_GameLoop
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 120;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	
	public static final int ANCHO = 512;
	public static final int ALTO = 640;
	private final String RUTA_GRAFICOS = "Recursos/Imagenes/";
	
	private Pantalla pantalla;
	private Thread hilo;
	private boolean enEjecucion;
	
	Escenario escenario;
	
	public Ventana() {
		setTitle("1943 Midway");
		
		this.pantalla = new Pantalla(this);
		this.setContentPane(pantalla);
		
		setResizable(false);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void cargar() {
		Grafico.cargarGraficos(RUTA_GRAFICOS);
		
		escenario = new Escenario(this);
		escenario.iniciar();
		
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
	}
	
	public void iniciar() {
		this.hilo = new Thread(this);
		this.hilo.start();
		this.enEjecucion = true;
	}
	
	public void parar() {
		this.enEjecucion = false;
	}
	
	public void dibujar() {
		pantalla.repaint();
	}
	
	public void actualizar() {
		escenario.actualizar(1.0 / TICKS_PER_SECOND);
	}
}
