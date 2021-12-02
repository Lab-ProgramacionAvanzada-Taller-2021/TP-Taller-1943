package ar.edu.unlam.pa.servidor;

import ar.edu.unlam.pa.model.Escenario;

public class GameLoopServidor {
	private final long NANO_SECONDS_IN_SECOND = 1_000_000_000;
	private final long TICKS_PER_SECOND = 2000;
	private final long NANO_SECONDS_PER_TICK = NANO_SECONDS_IN_SECOND / TICKS_PER_SECOND;

	private long timeStart;

	public void run() {
		timeStart = System.nanoTime();

		long next_game_tick = timeStart;

		while (true) {
			if (System.nanoTime() > next_game_tick) {
				next_game_tick += NANO_SECONDS_PER_TICK;
				actualizar(1.0 / TICKS_PER_SECOND);
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
