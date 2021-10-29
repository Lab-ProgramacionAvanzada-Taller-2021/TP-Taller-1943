package ar.edu.unlam.pa.model;

public class Bala extends Elemento{
	private static String RUTA_IMAGEN = "Recursos/Imagenes/balaAliada.png";
	private static double VELOCIDAD_MOVIMIENTO = 550;
	private static double RADIO_COLISION = 8;
	
	public Bala(Punto2D posicion) {
		super(new Hitbox(posicion ,RADIO_COLISION), BANDO.AMERICANO, VELOCIDAD_MOVIMIENTO, RUTA_IMAGEN);
	}

	@Override
	public void actualizar(double dt) {
		moverEnDireccion(0, -dt);
	}
	
	@Override
	public String toString() {
		return "Bala";
	}

}
