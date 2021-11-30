package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class Escenario {
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 20;
	private final double INTERVALO_CREAR_ENEMIGO = 10;
	private final double INTERVALO_SUBIR_NIVEL = 100;
	
	private LinkedList<Elemento> listaElementosCapa1;
	private LinkedList<Elemento> listaElementosCapa2;
	private LinkedList<AvionPlayer> listaJugadores;
	private LinkedList<Elemento> listaElementos;
	
	private Ventana ventana;
	private BufferedImage imagen;
	private double desplazamientoY = 0;
	private double tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
	private double tiempoProximoNivel = INTERVALO_SUBIR_NIVEL;
	private int maximaPuntuacion = 1_000_000;
	private int nivel = 1;
	
	public Escenario(Ventana ventana) {
		listaElementosCapa1 = new LinkedList<Elemento>();
		listaElementosCapa2 = new LinkedList<Elemento>();
		listaJugadores = new LinkedList<AvionPlayer>();
		listaElementos = new LinkedList<Elemento>();
		
		this.ventana = ventana;
		this.imagen = Grafico.obtenerGrafico("oceano")[0];
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
		listaElementos.add(0, nuevo);
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public synchronized void dibujar(Graphics2D g2) {
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
			jugador.dibujarBarraJugador(g2);
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
	
	public synchronized void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;

		subirNivelEscenario(dt);
		
		generarEnemigos(dt);
		
		for(Elemento elementoCapa1 : listaElementosCapa1) {
			elementoCapa1.actualizar(dt);
		}
		
		for(Elemento elementoCapa2 : listaElementosCapa2) {
			elementoCapa2.actualizar(dt);
		}
		
		for(int i=0; i<listaElementos.size(); i++) {
			listaElementos.get(i).actualizar(dt);
			
			for(int j=i; j<listaElementos.size(); j++) {
				if(listaElementos.get(i).colisionaCon(listaElementos.get(j))) {
					listaElementos.get(i).colisiono(listaElementos.get(j));
					listaElementos.get(j).colisiono(listaElementos.get(i));
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
				if(elemento instanceof AvionPlayer) {
					this.listaJugadores.remove(elemento);
				}
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
				case 4:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					break;
				case 5:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 6:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 7:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(new AvionEnemigoMedio(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 8:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(new AvionEnemigoMedio(this));
					agregarElemento(new AvionEnemigoMedio(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 9:
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(AvionEnemigo.AvionEnemigoFrontal(this));
					agregarElemento(new AvionEnemigoMedio(this));
					agregarElemento(new AvionEnemigoMedio(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralIzq(this));
					agregarElemento(AvionEnemigo.AvionEnemigoLateralDer(this));
					break;
				case 10:
					agregarElemento(new AvionEnemigoJefe(this));
					nivel++;
				default:
					break;
			}
			
			if(nivel % 2 == 0)
				agregarElementoCapa1(new Isla());
			
			agregarElementoCapa2(new Nube());
			
			tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO / listaJugadores.size();
		}else {
			tiempoProximoEnemigo -= dt;
		}
	}
	
	public Punto2D obtenerPosicionJugadorAleatorio() {
		return listaJugadores.get((int)(Math.random() * listaJugadores.size())).getPosicion();
	}
}
