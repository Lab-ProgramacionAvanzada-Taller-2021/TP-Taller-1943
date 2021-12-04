package ar.edu.unlam.pa.servidor;

import com.google.gson.Gson;

import ar.edu.unlam.pa.compartido.Mensaje;
import ar.edu.unlam.pa.compartido.TipoMensaje;
import ar.edu.unlam.pa.model.AvionEnemigo;
import ar.edu.unlam.pa.model.AvionEnemigoJefe;
import ar.edu.unlam.pa.model.AvionEnemigoMedio;
import ar.edu.unlam.pa.model.Elemento;
import ar.edu.unlam.pa.model.Escenario;
import ar.edu.unlam.pa.model.Isla;
import ar.edu.unlam.pa.model.Nube;

public class GameLoopServidor {
	private final long NANO_SECONDS_IN_SECOND = 1_000_000_000;
	private final long TICKS_PER_SECOND = 2000;
	private final long NANO_SECONDS_PER_TICK = NANO_SECONDS_IN_SECOND / TICKS_PER_SECOND;
	private final long ENEMY_PER_SECOND = 5;
	private final long NANO_SECONDS_PER_ENEMY = NANO_SECONDS_IN_SECOND * ENEMY_PER_SECOND;
	private final long LEVEL_PER_SECOND = 30;
	private final long NANO_SECONDS_PER_LEVEL = NANO_SECONDS_IN_SECOND * LEVEL_PER_SECOND;
	private final long LAYER_PER_SECOND = 20;
	private final long NANO_SECONDS_PER_LAYER = NANO_SECONDS_IN_SECOND * LAYER_PER_SECOND;
	
	public static final int ANCHO = 640;
	public static final int ALTO = 640;

	public int nivel = 1;
	
	private long timeStart;

	public void run() {
		timeStart = System.nanoTime();

		long next_game_tick = timeStart;
		long next_game_enemy = timeStart;
		long next_game_level = timeStart;
		long next_game_layer = timeStart;

		while (true) {
			Escenario.getInstance().subirNivelEscenario(nivel);
			if (System.nanoTime() > next_game_tick) {
				next_game_tick += NANO_SECONDS_PER_TICK;
				actualizar(1.0 / TICKS_PER_SECOND);
			}
			
			if (System.nanoTime() > next_game_enemy) {
				next_game_enemy += NANO_SECONDS_PER_ENEMY;
				Elemento elemento;
				
				if(nivel > 2 && nivel < 8) {
					elemento = AvionEnemigo.AvionEnemigoFrontal(Escenario.getInstance());
					Escenario.getInstance().agregarElemento(elemento);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.SMA, elemento.getInfo())));
				}
						
				if(nivel > 4 && nivel < 8) {
					elemento = AvionEnemigo.AvionEnemigoLateralDer(Escenario.getInstance());
					Escenario.getInstance().agregarElemento(elemento);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.SMA, elemento.getInfo())));
					elemento = AvionEnemigo.AvionEnemigoLateralIzq(Escenario.getInstance());
					Escenario.getInstance().agregarElemento(elemento);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.SMA, elemento.getInfo())));
				}
						
				if( nivel > 6 && nivel < 8) {
					elemento = new AvionEnemigoMedio(Escenario.getInstance());
					Escenario.getInstance().agregarElemento(elemento);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.MED, elemento.getInfo())));
				}
				
				if( nivel == 8 ) {
					elemento = new AvionEnemigoJefe(Escenario.getInstance());
					Escenario.getInstance().agregarElemento(elemento);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.BOS, elemento.getInfo())));
					nivel++;
				}
			}
			
			if (System.nanoTime() > next_game_layer) {
				next_game_layer += NANO_SECONDS_PER_LAYER / nivel;
			
				if(nivel<4 && nivel%2==0) {
					Elemento isla = new Isla();
					Escenario.getInstance().agregarElementoCapa1(isla);
					Servidor.broadcast((
						new Gson()).toJson(new Mensaje(TipoMensaje.ISL, isla.getInfo())));
				}
				
				Elemento nube = new Nube();
				Escenario.getInstance().agregarElementoCapa2(nube);
				Servidor.broadcast((
					new Gson()).toJson(new Mensaje(TipoMensaje.NUB, nube.getInfo())));
			}
			
			if (System.nanoTime() > next_game_level) {
				next_game_level += NANO_SECONDS_PER_LEVEL;
				nivel++;
				Escenario.getInstance().subirNivelEscenario(nivel);
			}
		}
	}

	public void actualizar(double dt) {
		Escenario.getInstance().actualizar(dt);
	}

	public long getTimeStart() {
		return timeStart;
	}
}
