package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.graficos.Ventana;

public class Escenario {
	private final String RUTA_IMAGEN = "Recursos/Imagenes/oceano.png";
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 80;
	private final double INTERVALO_CREAR_ENEMIGO = 1;
	
	private ConcurrentLinkedDeque<AvionPlayer> listaJugadores;
	private ConcurrentLinkedDeque<Elemento> listaElementos;
	private BufferedImage imagen;
	private double desplazamientoY = 0;
	private double tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
	private int maximaPuntuacion = 100;
	
	public Escenario() {
		listaJugadores = new ConcurrentLinkedDeque<AvionPlayer>();
		listaElementos = new ConcurrentLinkedDeque<Elemento>();
		
		this.imagen = LectorArchivos.leerImagen(RUTA_IMAGEN);
	}

	public void agregarJugador(AvionPlayer nuevo) {
		listaJugadores.addLast(nuevo);
	}
	
	public void agregarElemento(Elemento nuevo) {
		listaElementos.add(nuevo);
	}
	
	public void agregarElementos(Elemento[] nuevos) {
		listaElementos.addAll(Arrays.asList(nuevos));
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, 0, (int)(-DESPLAZAMIENTO+this.desplazamientoY), Ventana.ANCHO, Ventana.ALTO+DESPLAZAMIENTO, null);
		
		for(Elemento elemento : this.listaElementos) {
			elemento.dibujar(g2);
		}
		
		for(Elemento jugador : this.listaJugadores) {
			jugador.dibujar(g2);
		}
	
		g2.setColor(Color.WHITE);
		g2.drawString("Jugadores: " + this.listaJugadores.size(), Ventana.ANCHO - 100, 16); 
		g2.drawString("Elementos: " + this.listaElementos.size(), Ventana.ANCHO - 100, 32);
		g2.setColor(Color.YELLOW);
		g2.drawString("Puntuacion Maxima: " + this.maximaPuntuacion, Ventana.ANCHO / 2 - 80, 16);
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;
		
		generarEnemigo(dt);
		
		for(AvionPlayer jugador : this.listaJugadores) {
			jugador.actualizar(dt);
			
			for(Elemento elemento : this.listaElementos) {
				elemento.actualizar(dt);
				
				if(jugador.colisionaCon(elemento)) {
					jugador.colisiono(elemento);
					elemento.colisiono(jugador);
				}
				
				if(elemento.estaFueraDeRango()) {
					eliminarElemento(elemento);
				}
			}
			
			if(jugador.disparo()){
				agregarElementos(jugador.disparar());
			}
		}
		
		for(Elemento elemento : this.listaElementos) {
			for(Elemento elemento2 : this.listaElementos) {
				if(elemento.colisionaCon(elemento2)) {
					elemento.colisiono(elemento2);
					elemento2.colisiono(elemento);
				}
			}
		}
		
		eliminarElementosDestruidos();
	}
	
	public void eliminarElementosDestruidos() {
		for(Iterator<Elemento> it = this.listaElementos.iterator(); it.hasNext();){
			Elemento elemento = it.next();
			
			if(elemento.estaDestruido()) {
				AvionPlayer avion = this.listaJugadores.getLast();
				if(elemento.esEnemigo(avion))
					avion.sumarPuntos(elemento.darPuntos());
				it.remove();
			}
		}
		
		verificarPuntuacionMaxima();
	}
	
	public void verificarPuntuacionMaxima() {
		for(AvionPlayer jugador : this.listaJugadores) {
			int puntos = jugador.obtenerPuntos();
			this.maximaPuntuacion = (puntos > this.maximaPuntuacion) ? puntos : this.maximaPuntuacion;
		}
	}
	
	public void generarEnemigo(double dt) {
		if(tiempoProximoEnemigo < 0) {
			agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
			tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
		}else {
			tiempoProximoEnemigo -= dt;
		}
	}
	
}
