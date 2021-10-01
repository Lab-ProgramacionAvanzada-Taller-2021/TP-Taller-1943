package ar.edu.unlam.pa.elementos;

public class Bala {
	private int cantidadTiros;

	public Bala() {
		this.cantidadTiros = 0;
	}

	public Bala(int cantidadTiros) {
		super();
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
