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
	ISL, // Agrega una isla y envia a los clientes.
	NUB, // Agrega una nube y envia a los clientes.
	SMA, // Agrega avion enemigo chico y envia a los clientes.
	MED  // Agrega avion enemigo mediano y envia a los clientes.
}
