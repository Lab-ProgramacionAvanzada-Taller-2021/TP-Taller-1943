package ar.edu.unlam.pa.archivos;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class LectorArchivos {
	
	public static BufferedImage leerImagen(String path) {
		BufferedImage imagen = null;
		
		try {
			imagen = ImageIO.read(new File(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return imagen;
	}

}
