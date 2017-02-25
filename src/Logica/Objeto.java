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
	private int Rotacion;
	private int Angulo;
	private String Tipo;
	

	public Objeto(int vIdObjeto,int vCoordenadaX,int vCoordenadaY,int vAltura,int vAncho,int vRotacion,int vAngulo,String vTipo)
	{
		this.IdObjeto=vIdObjeto;
		this.CoordenadaX=vCoordenadaX;
		this.CoordenadaY=vCoordenadaY;
		this.Altura=vAltura;
		this.Ancho=vAncho;
		this.Rotacion=vRotacion;
		this.Angulo=vAngulo;
		this.Tipo=vTipo;
	}
	
	public int ObtenerIdObjeto()
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
	public int ObtenerRotacion()
	{
		return Rotacion;
	}
	public int ObtenerAngulo()
	{
		return Angulo;
	}
	
	public String ObtenerTipo()
	{
		return Tipo;
	}
	public void SetearCoordenadaX(int vCoordenadaX)
	{
		this.CoordenadaX=vCoordenadaX;
	}
	
	public void SetearCoordenadaY(int vCoordenadaY)
	{
		this.CoordenadaY=vCoordenadaY;
	}
	
	public void SetearAltura(int vAltura)
	{
		this.Altura=vAltura;
	}
	
	public void SetearAncho(int vAncho)
	{
		this.Ancho=vAncho;
	}
	public void SetearRotacion(int vRotacion)
	{
		this.Rotacion=vRotacion;
	}
	public void SetearAngulo(int vAngulo)
	{
		this.Angulo=vAngulo;
	}
	public void SetearTipo(String vTipo)
	{
		this.Tipo=vTipo;
	}

}
