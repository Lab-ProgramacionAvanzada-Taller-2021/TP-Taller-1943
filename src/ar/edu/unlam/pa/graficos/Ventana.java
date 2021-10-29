package ar.edu.unlam.pa.graficos;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.model.AvionEnemigo;

public class Ventana extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Constantes_del_GameLoop
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 60;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	
	public static int ANCHO = 512;
	public static int ALTO = 512;
	
	private Pantalla pantalla;
	private boolean enEjecucion;
	
	private Escenario escenario;
	private AvionPlayer avion;
	private AvionEnemigo avionEnemigo;

	public Ventana() {
		setTitle("1943 Midway");
		
		this.pantalla = new Pantalla();
		this.setContentPane(this.pantalla);
		
		setResizable(false);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init() {
		this.avion = new AvionPlayer(ANCHO / 2.1, ALTO / 1.2);
		this.avionEnemigo = new AvionEnemigo(100, 10);
		this.escenario = new Escenario();
		this.escenario.agregarElemento(avion);
		this.escenario.agregarElemento(avionEnemigo);
		
		addKeyListener(avion);
		this.enEjecucion = true;
		
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
	
	public void dibujar() {
		this.pantalla.repaint();
	}
	
	public void actualizar() {
		this.escenario.actualizar(1.0 / TICKS_PER_SECOND);
		this.avion.actualizar(1.0 / TICKS_PER_SECOND);
		this.avionEnemigo.actualizar(20.0 / TICKS_PER_SECOND);
		
	}
	
	private class Pantalla extends JPanel{
		private static final long serialVersionUID = 1L;
		
		public Pantalla() {
			setLayout(null);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(ANCHO, ALTO);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;
		
			escenario.dibujar(g2);;
		}
	}
}
