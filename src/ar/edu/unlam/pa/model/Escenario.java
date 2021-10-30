package ar.edu.unlam.pa.model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.graficos.Ventana;
import ar.edu.unlam.pa.model.AvionEnemigo;

public class Escenario {
	private final String RUTA_IMAGEN = "Recursos/Imagenes/oceano.png";
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 80;
	private final double INTERVALO_CREAR_ENEMIGO = 1;
	private final double INTERVALO_SUBIR_NIVEL = 50;
	public static final int INTERVALO_CREACION_DE_ENEMIGO = 4000;
	
	private ConcurrentLinkedDeque<AvionPlayer> listaJugadores;
	private ConcurrentLinkedDeque<Elemento> listaElementos;
	private BufferedImage imagen;
	private double desplazamientoY = 0;
	private double tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
	private double tiempoProximoNivel = INTERVALO_SUBIR_NIVEL;
	private int maximaPuntuacion = 100;
	private int nivel = 1;
	private Random aleatorio;
	private long intervaloEnemigos;
	
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
		g2.drawString("Nivel: " + this.nivel, Ventana.ANCHO - 80, 16); 
		g2.drawString("Jugadores: " + this.listaJugadores.size(), Ventana.ANCHO - 100, 32); 
		g2.drawString("Elementos: " + this.listaElementos.size(), Ventana.ANCHO - 100, 48);
		g2.setColor(Color.YELLOW);
		g2.drawString("Puntuacion Maxima: " + this.maximaPuntuacion, Ventana.ANCHO / 2 - 80, 16);
		
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;

		aleatorio = new Random();
		subirNivelEscenario(dt);
		
		generarEnemigos();
		
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
		verificarPuntuacionMaxima();
	}
	
	private void eliminarElementosDestruidos() {
		for(Iterator<Elemento> it = this.listaElementos.iterator(); it.hasNext();){
			Elemento elemento = it.next();
			
			if(elemento.estaDestruido()) {
				AvionPlayer avion = this.listaJugadores.getLast();
				if(elemento.esEnemigo(avion))
					avion.sumarPuntos(elemento.darPuntos());
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
	
	//private void generarEnemigo(double dt) {
	//	if(tiempoProximoEnemigo < 0) {
	//		switch(nivel) {
	//			case 1:
	//				agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
	//				break;
	//			case 2: 
	//				agregarElemento(new AvionEnemigo( Math.random() * (Ventana.ANCHO-8), -8));
	//				agregarElemento(new AvionEnemigoMedio( Math.random() * (Ventana.ANCHO-8), Ventana.ALTO));
	//				break;
	//			default:
	//				break;
	//		}
	//		tiempoProximoEnemigo = INTERVALO_CREAR_ENEMIGO;
	//	}else {
	//		tiempoProximoEnemigo -= dt;
	//	}
	//}
	
	
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
	
	
	
}
