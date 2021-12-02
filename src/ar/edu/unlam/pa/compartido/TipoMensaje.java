package ar.edu.unlam.pa.compartido;

public enum TipoMensaje {
	NEW, // Crea un nuevo usuario en el servidor y lo manda al cliente.
	MSG, 
	MOV, // Cambia la dirección del jugador en el servidor y envia al cliente.
	PAU, 
	BYE, // Elimina el jugador en el servidor y envia al cliente.
	PNG, 
	SNC // Sincroniza la posición del servidor con la del cliente.
}
