package ar.edu.unlam.pa.graficos;

import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import ar.edu.unlam.pa.archivos.LectorArchivos;
import ar.edu.unlam.pa.model.Elemento.DIRECCION;

public class Grafico {
	private static HashMap<String, BufferedImage[]> graficos = new HashMap<String, BufferedImage[]>();
	
	public static void cargarGraficos(String path) {
		File directorio = new File(path);
		
		for(File archivo : directorio.listFiles()) {
			if (archivo.isFile()) {
				String nombreArchivo = archivo.getName().replaceFirst("[.][^.]+$", "");
				BufferedImage imagenArchivo = LectorArchivos.leerImagen(archivo);
				agregarImagenEnDirecciones(nombreArchivo, imagenArchivo);
			}else {
				cargarGraficos(archivo.getPath());
			}
		}
	}
	
	public static void cargarAnimaciones(String path) {
		File directorio = new File(path);
		int index = 0;
		BufferedImage[] imagenes = new BufferedImage[(int)directorio.length()];
		
		File[] archivos = directorio.listFiles();
		Arrays.sort(archivos);
		
		for(File archivo : archivos) {
			if (archivo.isFile()) {
				BufferedImage imagenArchivo = LectorArchivos.leerImagen(archivo);
				imagenes[index++] = imagenArchivo;
			}else {
				cargarAnimaciones(archivo.getPath());
			}
			
			graficos.put(directorio.getName(), imagenes);
		}
	}
	
	public static void agregarImagenEnDirecciones(String nombre, BufferedImage imagen) {
		int tamaño = DIRECCION.values().length;
		BufferedImage[] imagenes = new BufferedImage[tamaño];
		
		for(int i=0; i<tamaño; i++) {
			AffineTransform affineTransform = new AffineTransform();
			affineTransform.rotate(Math.toRadians(i*45), imagen.getWidth()/2, imagen.getHeight()/2);
			AffineTransformOp affineTransformImagen = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			imagenes[i] = affineTransformImagen.filter(imagen,null);
		}

		graficos.put(nombre, imagenes);
		/*
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp opa = new ColorConvertOp(cs, null);  
		*/
		
		//return op.filter(opa.filter(imagen, null), null);
	}
	
	public static BufferedImage[] obtenerGrafico(String nombre) {
		return graficos.get(nombre);
	}
}
