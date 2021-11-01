package ar.edu.unlam.pa.graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import ar.edu.unlam.pa.archivos.LectorArchivos;

public class Grafico {
	private static HashMap<String, BufferedImage> graficos = new HashMap<String, BufferedImage>();
	
	public static void cargarGraficos(String path) {
		File directorio = new File(path);
		
		for(File archivo : directorio.listFiles()) {
			if (archivo.isFile())
				graficos.put(archivo.getName().replaceFirst("[.][^.]+$", ""), LectorArchivos.leerImagen(archivo));
			else {
				cargarGraficos(archivo.getPath());
			}
		}
	}
	
	public static BufferedImage obtenerGrafico(String nombre) {
		return graficos.get(nombre);
	}
}
