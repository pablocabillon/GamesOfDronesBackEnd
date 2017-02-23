package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del dronTerrestre que extiende de Dron
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DronTerrestre extends Dron {

	private int BlindajeActivo;
	
	public DronTerrestre(int vVelocidad,boolean vCamara,boolean vCanon,int vVision,int vBlindajeActivo)
	{
		super(vVelocidad,vCamara,vCanon,vVision);
		this.BlindajeActivo=vBlindajeActivo;
	}
	
	public int ObtenerBlindaje()
	{
		return BlindajeActivo;
	}
	
	public void SetearBlindaje(int vBlindajeActivo)
	{
		this.BlindajeActivo=vBlindajeActivo;
	}
}
