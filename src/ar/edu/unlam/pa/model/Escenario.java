package ar.edu.unlam.pa.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlam.pa.graficos.Grafico;
import ar.edu.unlam.pa.graficos.Ventana;

public class Escenario {
	private static Escenario escenario = null;
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 20;
	
	private LinkedList<Elemento> listaElementosCapa1;
	private LinkedList<Elemento> listaElementosCapa2;
	private LinkedList<AvionPlayer> listaJugadores;
	private LinkedList<Elemento> listaElementos;

	private double desplazamientoY = 0;
	private int maximaPuntuacion = 1_000_000;
	private int nivel = 0;
	
	private Escenario() {
		listaElementosCapa1 = new LinkedList<Elemento>();
		listaElementosCapa2 = new LinkedList<Elemento>();
		listaJugadores = new LinkedList<AvionPlayer>();
		listaElementos = new LinkedList<Elemento>();
	}
	
	public static Escenario getInstance() {
		if(escenario == null)
			escenario = new Escenario();
		
		return escenario;
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
		g2.drawImage(Grafico.obtenerGrafico("oceano")[0], 0, (int)(-DESPLAZAMIENTO+this.desplazamientoY), Ventana.ANCHO, Ventana.ALTO+DESPLAZAMIENTO, null);

		Iterator<Elemento> it = this.listaElementosCapa1.iterator();
		while(it.hasNext()){
			it.next().dibujar(g2);
		}
		
		it = this.listaElementosCapa2.iterator();
		while(it.hasNext()){
			it.next().dibujar(g2);
		}
		
		it = this.listaElementos.iterator();
		while(it.hasNext()){
			it.next().dibujar(g2);
		}

		Iterator<AvionPlayer> iter =  this.listaJugadores.iterator();
		while(iter.hasNext()){
			iter.next().dibujarBarraJugador(g2);
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
		
		//generarEnemigos(dt);
		
		for(Elemento elementoCapa1 : listaElementosCapa1) {
			elementoCapa1.actualizar(dt);
		}
		
		for(Elemento elementoCapa2 : listaElementosCapa2) {
			elementoCapa2.actualizar(dt);
		}
		
		for(int i=0; i<listaElementos.size(); i++) {
			listaElementos.get(i).actualizar(dt);
			
			for(int j=i+1; j<listaElementos.size(); j++) {
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
				if(!(elemento instanceof AvionPlayer)) {
					it.remove();
				}
			}
		}
	}
	
	private void verificarPuntuacionMaxima() {
		for(AvionPlayer jugador : this.listaJugadores) {
			int puntos = jugador.obtenerPuntos();
			this.maximaPuntuacion = (puntos > this.maximaPuntuacion) ? puntos : this.maximaPuntuacion;
		}
	}

	public void subirNivelEscenario(int nivel) {
		this.nivel = nivel;
	}
	
	public void agregarUsuario(int id) {
		switch(listaJugadores.size()) {
			case 0:
				agregarJugador(AvionPlayer.crearJugador1(this, id));
				break;
			case 1:
				agregarJugador(AvionPlayer.crearJugador2(this, id));
				break;
			case 2:
				agregarJugador(AvionPlayer.crearJugador3(this, id));
				break;
			default:
				agregarJugador(AvionPlayer.crearJugador4(this, id));
				break;
		}
	}
	
	public void agregarUsuario(int id, String jugador) {
		String[] data = jugador.split("\\|");
		agregarJugador(new AvionPlayer(
			this, Integer.parseInt(data[0]), id, Double.parseDouble(data[1]), Double.parseDouble(data[2]), "jugador"+data[0]));
	}
	
	public void eliminarUsuario(int id) {
		AvionPlayer jugador = obtenerJugador(id);
		
		if(jugador != null) {
			listaJugadores.remove(jugador);
			listaElementos.remove(jugador);
		}
	}
	
	public AvionPlayer obtenerJugador(int id) {
		for(AvionPlayer jugador : listaJugadores) 
			if(id == jugador.getId()) 
				return jugador;
		
		return null;
	}
	
	public List<AvionPlayer> obtenerJugadores(){
		return listaJugadores;
	}
	
	public Punto2D obtenerPosicionJugadorAleatorio() {
		return (listaJugadores.size() > 0)? listaJugadores.get(0).getPosicion() : null;
	}
}
