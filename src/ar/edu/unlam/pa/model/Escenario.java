package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.model.AvionEnemigo;

public class Escenario {
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 60;
	private final double INTERVALO_CREAR_ENEMIGO = 5;
	private final double INTERVALO_SUBIR_NIVEL = 50;
	
	private ConcurrentLinkedDeque<Elemento> listaElementosCapa1;
	private ConcurrentLinkedDeque<Elemento> listaElementosCapa2;
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
		listaElementosCapa1 = new ConcurrentLinkedDeque<Elemento>();
		listaElementosCapa2 = new ConcurrentLinkedDeque<Elemento>();
		listaJugadores = new ConcurrentLinkedDeque<AvionPlayer>();
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
	
	public void agregarElementoCapa1(Elemento elemento) {
		listaElementosCapa1.add(elemento);
	}
	
	public void agregarElementoCapa2(Elemento elemento) {
		listaElementosCapa2.add(elemento);
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

		for(Elemento elementoCapa1 : listaElementosCapa1) {
			elementoCapa1.dibujar(g2);
		}
		
		for(Elemento elementoCapa2 : listaElementosCapa2) {
			elementoCapa2.dibujar(g2);
		}
		
		for(Elemento elemento : listaElementos) {
			elemento.dibujar(g2);
		}
		
		for(AvionPlayer jugador : listaJugadores) {
			jugador.dibujarBarraJugador(g2, listaJugadores.size());
		}
	
		dibujarDebug(g2);
		
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
		g2.drawString("Islas: " + this.listaElementosCapa1.size(), Ventana.ANCHO / 2 - 48, 64);
		g2.drawString("Nubes: " + this.listaElementosCapa2.size(), Ventana.ANCHO / 2 - 48, 80);
		g2.drawString("Elementos: " + this.listaElementos.size(), Ventana.ANCHO / 2 - 48, 96);
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;

		subirNivelEscenario(dt);
		
		generarEnemigos(dt);
		
		for(Elemento elementoCapa1 : listaElementosCapa1) {
			elementoCapa1.actualizar(dt);
		}
		
		for(Elemento elementoCapa2 : listaElementosCapa2) {
			elementoCapa2.actualizar(dt);
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
		for(Iterator<Elemento> it = this.listaElementosCapa1.iterator(); it.hasNext();){
			Elemento elemento = it.next();
			
			if(elemento.estaDestruido()) {
				it.remove();
			}
		}
		
		for(Iterator<Elemento> it = this.listaElementosCapa2.iterator(); it.hasNext();){
			Elemento elemento = it.next();
			
			if(elemento.estaDestruido()) {
				it.remove();
			}
		}
		
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
	
	private void generarEnemigos(double dt) {
		if(tiempoProximoEnemigo < 0) {
			switch(nivel) {
				case 1:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					break;
				case 2: 
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 3: 
					agregarElemento(new AvionEnemigoMedio(this));
					break;
				default:
					break;
			}
			
			agregarElementoCapa1(new Isla());
			agregarElementoCapa2(new Nube());
			
			tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
		}else {
			tiempoProximoEnemigo -= dt;
		}
	}

	/*
	private void generarEnemigos() {
		if (System.currentTimeMillis() - intervaloEnemigos >= INTERVALO_CREACION_DE_ENEMIGO) {
			int cant_aviones = aleatorio.nextInt(3) + 2;
			int direccion = aleatorio.nextInt(99);  
			double desp_x = 0;
			double desp_y = 0;
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
				
				switch(nivel) {
					case 1:
						agregarElemento(new AvionEnemigo(aleatorio.nextInt(9) + desp_x, desp_y, aparece, this));
						break;
					case 2:
						agregarElemento(new AvionEnemigo(aleatorio.nextInt(9) + desp_x, desp_y, aparece, this));
						agregarElemento(new AvionEnemigoMedio( Math.random() * (Ventana.ANCHO-8), Ventana.ALTO));
						break;
					default:
						break;
				}
			}
			intervaloEnemigos = System.currentTimeMillis();
		}
	}
	*/
}
