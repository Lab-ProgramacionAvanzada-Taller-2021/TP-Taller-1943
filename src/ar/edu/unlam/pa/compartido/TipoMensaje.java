package ar.edu.unlam.pa.compartido;

public enum TipoMensaje {
	NEW, // Crea un nuevo usuario en el servidor y envia a los cliente.
	MSG, 
	MOV, // Cambia la dirección del jugador en el servidor y envia a los cliente.
	PAU, 
	BYE, // Elimina el jugador en el servidor y envia al cliente.
	PNG, 
	SNC, // Sincroniza la posición del servidor con la del cliente.
	ATK, // Dispara el jugador y envia a los clientes.
}
