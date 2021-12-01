package ar.edu.unlam.pa.servidor;

import ar.edu.unlam.pa.model.Escenario;

public class Game {
	private final long NANO_SECONDS_IN_SECOND = 1_000_000_000;
	private final long TICKS_PER_SECOND = 60;
	private final long NANO_SECONDS_PER_TICK = NANO_SECONDS_IN_SECOND / TICKS_PER_SECOND;

	private long timeStart;
	
	private Escenario escenario;
	
	public Game() {
		escenario = Escenario.getInstance();
	}

	public void run() {
		timeStart = System.nanoTime();

		long next_game_tick = timeStart;

		while (true) {
			if (System.nanoTime() > next_game_tick) {
				next_game_tick += NANO_SECONDS_PER_TICK;
				actualizar(next_game_tick);
			}
		}
	}

	public void actualizar(double deltaTime) {
		escenario.actualizar(1.0 / TICKS_PER_SECOND);
	}

	public long getTimeStart() {
		return timeStart;
	}
}
