package ar.edu.unlam.pa.graficos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Pantalla extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final int CUADRADO = 120;
	private final int ANCHO;
	private final int ALTO;

	public Pantalla(int cuadradoX, int cuadradoY) {
		this.ANCHO = cuadradoX * this.CUADRADO;
		this.ALTO = cuadradoY * this.CUADRADO;
		
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
		
		Dimension currentDimension = getRootPane().getSize();
		g2.scale(currentDimension.getWidth() / WIDTH, currentDimension.getHeight() / HEIGHT);
	}
}
