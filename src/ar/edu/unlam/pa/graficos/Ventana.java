package ar.edu.unlam.pa.graficos;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import ar.edu.unlam.pa.cliente.Cliente;
import ar.edu.unlam.pa.compartido.TipoMensaje;
import ar.edu.unlam.pa.model.Elemento.DIRECCION;
import ar.edu.unlam.pa.model.Escenario;

public class Ventana extends JFrame implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	//Constantes_del_GameLoop
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 500;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	
	public static final int ANCHO = 640;
	public static final int ALTO = 640;
	private final String RUTA_IMAGENES = "Recursos/Imagenes/";
	private final String RUTA_ANIMACIONES = "Recursos/Animaciones/";
	
	private Cliente client;
	private Pantalla pantalla;
	private boolean enEjecucion;
	
	private Set<Integer> teclasPresionadas = new HashSet<Integer>();
	
	public Escenario escenario;

	public Ventana(Cliente client) {
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
		
		addKeyListener(this);
		
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
				actualizar(1.0 / TICKS_PER_SECOND);
			}
			if (System.currentTimeMillis() > next_game_frame) {
				next_game_frame += SKIP_FRAMES;
				dibujar();
			}
		}
		
		client.send(TipoMensaje.BYE);
		System.exit(0);
		
		
	}
	
	public void cerrar() {
		client.send(TipoMensaje.BYE);
		System.exit(0);
		enEjecucion = false;
	}
	
	public synchronized void dibujar() {
		pantalla.repaint();
	}
	
	public synchronized void actualizar(double dt) {
		escenario.actualizar(dt);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) 
			client.send(TipoMensaje.ATK, true);
		else {
			teclasPresionadas.add(e.getKeyCode());
			
			verificarTeclaPresionada(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			client.send(TipoMensaje.ATK, false);
		else {
			teclasPresionadas.remove(e.getKeyCode());
			
			verificarTeclaPresionada(false);
		}
	}
	
	private void verificarTeclaPresionada(boolean presionando) {
		if(teclasPresionadas.contains(KeyEvent.VK_W)){
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				client.send(TipoMensaje.MOV, DIRECCION.NOROESTE);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				client.send(TipoMensaje.MOV, DIRECCION.NORESTE);
			else
				client.send(TipoMensaje.MOV, DIRECCION.NORTE);
		}else if(teclasPresionadas.contains(KeyEvent.VK_S)) {
			if(teclasPresionadas.contains(KeyEvent.VK_A))
				client.send(TipoMensaje.MOV, DIRECCION.SUROESTE);
			else if(teclasPresionadas.contains(KeyEvent.VK_D))
				client.send(TipoMensaje.MOV, DIRECCION.SURESTE);
			else
				client.send(TipoMensaje.MOV, DIRECCION.SUR);
		}else if(teclasPresionadas.contains(KeyEvent.VK_A)) {
			client.send(TipoMensaje.MOV, DIRECCION.OESTE);
		}else if(teclasPresionadas.contains(KeyEvent.VK_D)) {
			client.send(TipoMensaje.MOV, DIRECCION.ESTE);
		}else {
			if(!presionando)
				client.send(TipoMensaje.MOV, DIRECCION.CENTRO);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
