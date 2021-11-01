package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class Escenario {
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 60;
	private final double INTERVALO_CREAR_ENEMIGO = 5;
	private final double INTERVALO_SUBIR_NIVEL = 50;
	
	private ConcurrentLinkedDeque<Elemento> listaElementosEscenario;
	private ConcurrentLinkedDeque<AvionPlayer> listaJugadores;
	private ConcurrentLinkedDeque<Elemento> listaElementos;
	
	private Ventana ventana;
	private BufferedImage imagen;
	private double desplazamientoY = 0;
	private double tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
	private double tiempoProximoNivel = INTERVALO_SUBIR_NIVEL;
	private int maximaPuntuacion = 100_000;
	private int nivel = 1;
	
	public Escenario(Ventana ventana) {
		listaJugadores = new ConcurrentLinkedDeque<AvionPlayer>();
		listaElementosEscenario = new ConcurrentLinkedDeque<Elemento>();
		listaElementos = new ConcurrentLinkedDeque<Elemento>();
		
		this.ventana = ventana;
		this.imagen = Grafico.obtenerGrafico("oceano");
	}
	
	public void iniciar() {
		agregarJugador(AvionPlayer.crearJugador1(this));
		agregarJugador(AvionPlayer.crearJugador2(this));
		
		cargarTeclasDeLosJugadores();
	}

	public void cargarTeclasDeLosJugadores() {
		for(AvionPlayer jugador : listaJugadores) {
			ventana.addKeyListener(jugador);
		}
	}
	
	public void agregarElementoEscenario(Elemento elemento) {
		listaElementosEscenario.add(elemento);
	}
	
	public void agregarJugador(AvionPlayer nuevo) {
		listaJugadores.add(nuevo);
		listaElementos.add(nuevo);
	}
	
	public void agregarElemento(Elemento nuevo) {
		listaElementos.addFirst(nuevo);
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, 0, (int)(-DESPLAZAMIENTO+this.desplazamientoY), Ventana.ANCHO, Ventana.ALTO+DESPLAZAMIENTO, null);

		for(Elemento elementoEscenario : listaElementosEscenario) {
			elementoEscenario.dibujar(g2);
		}
		
		for(Elemento elemento : listaElementos) {
			elemento.dibujar(g2);
		}
		
		for(AvionPlayer jugador : listaJugadores) {
			jugador.dibujarBarraJugador(g2);
		}
	
		//dibujarDebug(g2);
		
		dibujarPuntuacionMaxima(g2);
	}
	
	private void dibujarPuntuacionMaxima(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		g2.drawString("Puntuacion Maxima: " + this.maximaPuntuacion, Ventana.ANCHO / 2 - 80, 16);	
	}
	
	private void dibujarDebug(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.drawString("Nivel: " + this.nivel, Ventana.ANCHO / 2 - 32, 32); 
		g2.drawString("Jugadores: " + this.listaJugadores.size(), Ventana.ANCHO / 2 - 48, 48); 
		g2.drawString("Elementos: " + this.listaElementos.size(), Ventana.ANCHO / 2 - 48, 64);
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;
		
		subirNivelEscenario(dt);
		
		generarEnemigo(dt);
		
		for(Elemento elementoEscenario : listaElementosEscenario) {
			elementoEscenario.actualizar(dt);
		}
		
		for(Elemento colisionador : listaElementos) {
			colisionador.actualizar(dt);
			
			for(Elemento colisionado : listaElementos) {
				if(colisionador.colisionaCon(colisionado)) {
					colisionador.colisiono(colisionado);
				}
			}
		}
		
		eliminarElementosDestruidos();
		verificarPuntuacionMaxima();
	}
	
	private void eliminarElementosDestruidos() {
		for(Iterator<Elemento> it = this.listaElementos.iterator(); it.hasNext();){
			Elemento elemento = it.next();
			
			if(elemento.estaDestruido()) {
				it.remove();
			}
		}
	}
	
	private void verificarPuntuacionMaxima() {
		for(AvionPlayer jugador : this.listaJugadores) {
			int puntos = jugador.obtenerPuntos();
			this.maximaPuntuacion = (puntos > this.maximaPuntuacion) ? puntos : this.maximaPuntuacion;
		}
	}

	private void subirNivelEscenario(double dt) {
		if(tiempoProximoNivel < 0) {
			this.nivel++;
			tiempoProximoNivel = INTERVALO_SUBIR_NIVEL;
		}else {
			tiempoProximoNivel -= dt;
		}
	}
	
	private void generarEnemigo(double dt) {
		if(tiempoProximoEnemigo < 0) {
			switch(nivel) {
				case 1:
					agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
					agregarElementoEscenario(new Isla());
					break;
				case 2: 
					agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
					agregarElemento(new AvionEnemigoMedio( Math.random() * (Ventana.ANCHO-8), Ventana.ALTO));
					break;
				default:
					break;
			}
			tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
		}else {
			tiempoProximoEnemigo -= dt;
		}
	}
	
}
