package ar.edu.unlam.pa.elementos;

//import ar.edu.unlam.pa.model.Elemento;
//import ar.edu.unlam.pa.model.Hitbox;

//public class Bala extends Elemento{
public class Bala{
	private int cantidadTiros;
	//private static String RUTA = "Recursos/Imagenes/enemigo.png";

	//public Bala(Hitbox hitbox, int bando, double vidaMaxima, double velocidad, String ruta) {
	//	super(hitbox, bando, velocidad, ruta);
	//	this.cantidadTiros = 0;
	//}

	public Bala() {
		//super();
		this.cantidadTiros = 0;
	}
	
	public Bala(int cantidadTiros) {
		//super();
		this.cantidadTiros = cantidadTiros;
	}

	public int getCantidadTiros() {
		return cantidadTiros;
	}

	public void setCantidadTiros(int cantidadTiros) {
		this.cantidadTiros = cantidadTiros;
	}

	@Override
	public String toString() {
		return "Bala [cantidadTiros=" + cantidadTiros + "]";
	}
	
	

}
