package ar.edu.unlam.pa.model;

import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.List;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.graficos.Ventana;

import java.awt.Graphics2D;

public class Escenario {
	private List<Elemento> listaElementos;
	private final String RUTA_IMAGEN = "Recursos/Imagenes/oceano.png";
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 80;
	private final double TIEMPO_CREAR_ENEMIGO = 3;
	
	private BufferedImage imagen;
	private double desplazamientoY;
	private double timerProximoEnemigo = TIEMPO_CREAR_ENEMIGO;
	
	public Escenario() {
		listaElementos = new LinkedList<Elemento>();
		
		this.desplazamientoY = 0;
		
		this.imagen = LectorArchivos.leerImagen(RUTA_IMAGEN);
	}

	public void agregarElemento(Elemento nuevo) {
		listaElementos.add(0, nuevo);
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public String mostrarTodosLosElementos() {
		String lista = "";
		int cantElementos = listaElementos.size();
		
		for(int i=0; i<cantElementos; i++) {
			lista = lista.concat(listaElementos.get(i).toString());
			if(i < cantElementos-1)
				lista = lista.concat(System.lineSeparator());
		}
		
		return lista;
	}
	
	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, 0, (int)(-DESPLAZAMIENTO+this.desplazamientoY), Ventana.ANCHO, Ventana.ALTO+DESPLAZAMIENTO, null);
		
		for(Elemento elemento : this.listaElementos) {
			elemento.dibujar(g2);
		}
		
		String canEnemigos = (this.listaElementos.size()-1) + "";
		
		g2.setColor(Color.WHITE);
		g2.drawString("Enemigos: " + canEnemigos, Ventana.ANCHO - 100, 16); 
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;
		
		generarEnemigo(dt);
		
		for(Elemento elemento : this.listaElementos) {
			elemento.actualizar(dt);
			
			if(elemento.estaFueraDeRango())
				eliminarElemento(elemento);
		}
	}
	
	public void generarEnemigo(double dt) {
		if(timerProximoEnemigo < 0) {
			this.agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
			timerProximoEnemigo = TIEMPO_CREAR_ENEMIGO;
		}else {
			timerProximoEnemigo -= dt;
		}
	}
	
}
