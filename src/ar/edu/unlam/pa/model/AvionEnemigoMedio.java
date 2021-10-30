package ar.edu.unlam.pa.model;

public class AvionEnemigoMedio extends Avion{
	private static String RUTA = "Recursos/Imagenes/enemigoMedio.png";
	private static double VELOCIDAD_MOVIMIENTO = 140;
	private static double RADIO_COLISION = 22;
	private static double VIDA_MAXIMA = 20;
	private static int PUNTOS = 100;

	public AvionEnemigoMedio(double x, double y) {
		super(new Hitbox(new Punto2D(x, y), RADIO_COLISION), Elemento.BANDO.JAPONES, VIDA_MAXIMA, VELOCIDAD_MOVIMIENTO, RUTA);
	}
	
	public void colisiono(Elemento elemento) {
		vidaActual -= 5;
		
		if(vidaActual <= 0)
			this.destruir();
	}
	
	@Override
	public int darPuntos() {
		return PUNTOS;
	}
	
	@Override
	public void actualizar(double dt) {
		this.moverEnDireccion(0, -dt);
	}
}
