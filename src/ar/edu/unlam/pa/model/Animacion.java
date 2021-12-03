package ar.edu.unlam.pa.model;

public class Animacion extends Elemento{
	private static String NOMBRE_IMAGEN = "Explosion"; //Temporal
	private int index = 0;
	private static double INTERVALO_ITERACCION = 0.001;
	private double tiempoInteraccion = INTERVALO_ITERACCION;

	public Animacion(Hitbox hitbox,  double velocidad){
		super(hitbox, DIRECCION.SUR, BANDO.JAPONES, velocidad, NOMBRE_IMAGEN);
	}

	@Override
	public void actualizar(double dt) {
		if( tiempoInteraccion <= 0) {
			actualizarImagen(++index);
			tiempoInteraccion = INTERVALO_ITERACCION;
			
			if(index >= 37) //Num Explosión
				destruir();
			
		}else {
			tiempoInteraccion -= dt;
		}
	}
}
