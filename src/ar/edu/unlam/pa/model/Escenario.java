package ar.edu.unlam.pa.model;

import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Escenario {
	private List<Elemento> listaElementos;
	private final String RUTA_IMAGEN = "Recursos/Imagenes/fondo.png";
	private final int DESPLAZAMIENTO = 64;
	private final int VELOCIDAD_Y = 100;
	
	private BufferedImage imagen;
	private double desplazamientoY;
	
	public Escenario() {
		listaElementos = new LinkedList<Elemento>();
		
		this.desplazamientoY = 0;
		
		this.cargarImagen();
	}
	
	public void cargarImagen() {
		try {
			this.imagen = ImageIO.read(new File(RUTA_IMAGEN));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void agregarElemento(Elemento nuevo) {
		listaElementos.add(nuevo);
	}
	
	public void eliminarElemento(Elemento destruido) {
		listaElementos.remove(destruido);
	}
	
	public String mostrarTodosLosElementos() {
		String lista = "";
		int cantElementos = listaElementos.size();
		
		for(int i=0; i<cantElementos; i++) {
			lista = lista.concat(listaElementos.get(i).toString());
			if(i < cantElementos-1)
				lista = lista.concat(System.lineSeparator());
		}
		
		return lista;
	}
	
	public void dibujar(Graphics2D g2) {
		g2.drawImage(this.imagen, 0, (int)(-DESPLAZAMIENTO+this.desplazamientoY), 640, 512+DESPLAZAMIENTO, null);
		
		for(Elemento elemento : this.listaElementos) {
			elemento.dibujar(g2);
		}
	}
	
	public void actualizar(double dt) {
		this.desplazamientoY = (this.desplazamientoY+dt*VELOCIDAD_Y)%DESPLAZAMIENTO;
		
		//for(Elemento elemento : this.listaElementos) {
		//	elemento.actualizar(dt);
		//}
	}
	
	
	
}
