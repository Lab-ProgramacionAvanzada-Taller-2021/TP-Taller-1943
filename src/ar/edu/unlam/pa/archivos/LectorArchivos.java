package ar.edu.unlam.pa.archivos;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class LectorArchivos {
	
	public static BufferedImage leerImagen(File archivo) {
		try {
			return ImageIO.read(archivo);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}

}
