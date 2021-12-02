package ar.edu.unlam.pa.compartido;

public class Mensaje {
	private long time;
	private TipoMensaje type;
	private int idClient;
	private Object message;

	public Mensaje(TipoMensaje type, int idClient, Object message) {
		this.time = System.nanoTime();
		this.type = type;
		this.idClient = idClient;
		this.message = message;
	}

	public Mensaje(TipoMensaje type, Object message) {
		this(type, 0, message);
	}

	public Mensaje(TipoMensaje type, int idClient) {
		this(type, idClient, null);
	}

	public Mensaje(TipoMensaje type) {
		this(type, 0, null);
	}

	public TipoMensaje getType() {
		return type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setType(TipoMensaje type) {
		this.type = type;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "NetworkMessage [time=" + time + ", type=" + type + ", idClient=" + idClient + ", message=" + message
				+ "]";
	}

}
