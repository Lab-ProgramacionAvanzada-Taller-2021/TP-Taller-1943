package ar.edu.unlam.pa.graficos;

import javax.swing.JFrame;

public class Ventana extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	/***
	 * Constantes_de_la_pantalla.
	 */
	private final int CUADRADO_X = 4; //16
	private final int CUADRADO_Y = 3; //9
	
	/***
	 * Constantes_del_GameLoop
	 */
	private final int SECOND = 1000;
	private final int FRAMES_PER_SECOND = 60;
	private final int SKIP_FRAMES = SECOND / FRAMES_PER_SECOND;
	private final int TICKS_PER_SECOND = 120;
	private final int SKIP_TICKS = SECOND / TICKS_PER_SECOND;
	
	private Pantalla pantalla;
	private boolean enEjecucion;

	public Ventana() {
		setTitle("1943 Midway");
		
		this.pantalla = new Pantalla(CUADRADO_X, CUADRADO_Y);
		
		this.setContentPane(this.pantalla);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		setFocusable(true);
		requestFocusInWindow();
		
		this.enEjecucion = true;
	}
	
	/***
	 * GameLoop_de_la_catedra.
	 */
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
		
	}
	
}
