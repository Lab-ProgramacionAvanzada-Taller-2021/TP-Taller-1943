package ar.edu.unlam.pa.graficos;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ar.edu.unlam.pa.model.AvionPlayer;
import ar.edu.unlam.pa.model.Escenario;
//import main.java.servidor.partida.objetos.nave.EnemigoServer;
//import main.java.conexion.Constante;
//import main.java.servidor.partida.objetos.nave.EnemigoServer;
import ar.edu.unlam.pa.model.AvionEnemigo;

public class Ventana extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Constantes_del_GameLoop
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 60;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	public static final int INTERVALO_CREACION_DE_ENEMIGO = 4000;

	
	public static int ANCHO = 512;
	public static int ALTO = 512;
	
	//private ArrayList<AvionEnemigo> enemigos;
	private Random aleatorio;
	private long intervaloEnemigos;
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
		aleatorio = new Random();
		
		
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
		generarEnemigos();
	}
	
	public void actualizar() {
		this.escenario.actualizar(1.0 / TICKS_PER_SECOND);
		//this.avion.actualizar(1.0 / TICKS_PER_SECOND);
		//this.avionEnemigo.actualizar(20.0 / TICKS_PER_SECOND);
		//this.avionEnemigo.actualizar(10.0 / TICKS_PER_SECOND);
		
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
	
	private void generarEnemigos() {
		if (System.currentTimeMillis() - intervaloEnemigos >= INTERVALO_CREACION_DE_ENEMIGO) {
			int cant_aviones = aleatorio.nextInt(3) + 2;
			int direccion = aleatorio.nextInt(99);  
			int desp_x = 0;
			int desp_y = 0;
			int aparece = 0;
			int dist_aviones = 50;
			for(int i = 0; i < cant_aviones; i ++) {
				
				if (direccion < 33) { // aparece de izquierda
					desp_x = 0; 
					desp_y = i*dist_aviones;
					aparece = 0;
				}else if (direccion < 66) {  // aparece de centro
					desp_x = 181 + i*dist_aviones; 
					desp_y = 0;
					aparece = 1;
				} else { // aparece de derecha
					desp_x = 512; 
					desp_y = i*dist_aviones;
					aparece = 2;
				}
				
				this.escenario.agregarElemento(new AvionEnemigo(aleatorio.nextInt(9) + desp_x, desp_y, aparece));
			}
			
			intervaloEnemigos = System.currentTimeMillis();
		}
	}
	
	//private void moverEnemigos() {
		//Iterator<AvionEnemigo> i = enemigos.iterator();
	//	Iterator<AvionEnemigo> i = escenario.mostrarTodosLosElementos();
	//	while (i.hasNext()) {
	//		EnemigoServer enemigo = i.next();
	//		if (enemigo.estaDentroDePantalla(false, true, false, false)) {
	//			enemigo.mover();
	//		} else {
	//			i.remove();
	//		}
	//	}
	//}
}
