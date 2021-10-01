package ar.edu.unlam.pa.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EstadoAvionTest {

	AvionPlayer avion;
	
	@Before
	public void Arrage() {
		avion = new AvionPlayer(new Punto2D(2, 3));
	}
	
	@Test
	public void estadoDeCreacion() {		
		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}
	
	@Test 
	public void estadoPrimerPowerUp() {
		avion.agarraPowerUP();
		
		Assert.assertEquals("SuperAvion", avion.toStringEstado());
	}
	
	@Test 
	public void estadoSegundoPowerUp() {
		avion.agarraPowerUP();
		avion.agarraPowerUP();
		
		Assert.assertEquals("MegaAvion", avion.toStringEstado());
	}
	
	@Test 
	public void estadoDespuesDeLaDuracion() {
		avion.agarraPowerUP();
		
		for(int i=0; i++<32;) {
			avion.decrementarContadorPowerUP();
		}
		
		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}
	
	@Test 
	public void estadoDespuesDeLaDobleDuracion() {
		avion.agarraPowerUP();
		avion.agarraPowerUP();
		
		for(int i=0; i++<64;) {
			avion.decrementarContadorPowerUP();
		}
		
		Assert.assertEquals("AvionNormal", avion.toStringEstado());
	}
	
	@After
	public void Annihilate() {
		avion = null;
	}

}
