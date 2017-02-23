package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del Objeto
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Objeto {
	
	private int IdObjeto;
	private int CoordenadaX;
	private int CoordenadaY;
	private int Altura;
	private int Ancho;
	
	public Objeto()
	{
		super();
	}
	public Objeto(int vIdObjeto,int vCoordenadaX,int vCoordenadaY,int vAltura,int vAncho)
	{
		this.IdObjeto=vIdObjeto;
		this.CoordenadaX=vCoordenadaX;
		this.CoordenadaY=vCoordenadaY;
		this.Altura=vAltura;
		this.Ancho=vAncho;
	}
	
	public int ObtenerGetId()
	{
		return IdObjeto;
	}
	
	public int ObtenerCoordenadaX()
	{
		return CoordenadaX;
	}
	
	public int ObtenerCoordenadaY()
	{
		return CoordenadaY;
	}
	
	public int ObtenerAltura()
	{
		return Altura;
	}

	public int ObtenerAncho()
	{
		return Ancho;
	}
	
	public void SetearCoordenadaX(int vCoordenadaX)
	{
		this.CoordenadaX=vCoordenadaX;
	}
	
	public void SetearCoordenadaY(int vCoordenadaY)
	{
		this.CoordenadaY=vCoordenadaY;
	}
	


}
