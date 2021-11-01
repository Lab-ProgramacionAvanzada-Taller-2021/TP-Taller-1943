package ar.edu.unlam.pa.graficos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Pantalla extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Ventana ventana;

	public Pantalla(Ventana ventana) {
		this.ventana = ventana;
		
		setLayout(null);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Ventana.ANCHO, Ventana.ALTO);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
	
		ventana.escenario.dibujar(g2);;
	}
}
