package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del dronTerrestre que extiende de Dron
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DronTerrestre extends Dron {

	private int BlindajeActivo;
	
	public DronTerrestre(int vIdObjeto,int vCoordenadaX,int vCoordenadaY,int vAltura,int vAncho,int vRotacion,int vAngulo,String vTipo,int vVelocidad,boolean vCamara,boolean vCanon,int vVision,int vBlindajeActivo)
	{
		super(vIdObjeto,vCoordenadaX,vCoordenadaY,vAltura,vAncho,vRotacion,vAngulo, vTipo,vVelocidad,vCamara,vCanon,vVision);
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
