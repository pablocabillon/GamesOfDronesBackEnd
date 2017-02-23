package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del dron
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Dron {

	
	private int Velocidad;
	private boolean Camara;
	private boolean Canon;
	private int Vision;
	
	public Dron()
	{
		super();
	}
	
	public Dron(int vVelocidad,boolean vCamara,boolean vCanon,int vVision)
	{
		this.Velocidad=vVelocidad;
		this.Camara=vCamara;
		this.Canon=vCanon;
		this.Vision=vVision;
	}
	
	public int ObtenerVelocidad()
	{
		return Velocidad;
	}
	
	public boolean ObtenerCamara()
	{
		return Camara;
	}
	
	public boolean ObtenerCanon()
	{
		return Canon;
	}
	
	public int ObtenerVision()
	{
		return Vision;
	}
	
	public void SetearVelocidad(int vVelocidad)
	{
		this.Velocidad=vVelocidad;
	}
	
	public void SetearCamara(boolean vCamara)
	{
		this.Camara=vCamara;
	}
	
	public void SetearCanon(boolean vCanon)
	{
		this.Canon=vCanon;
	}
	
	public void SetearVision(int vVision)
	{
		this.Vision=vVision;
	}
	
}
