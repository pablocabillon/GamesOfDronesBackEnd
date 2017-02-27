package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del Base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Base extends Objeto{
	
	private int VidaZonaPolvorin;
	private int VidaZonaDespegue;
	
	public Base(int vIdObjeto,int vCoordenadaX,int vCoordenadaY,int vAltura,int vAncho,int vRotacion,int vAngulo,String vTipo,int vVidaZonaPolvorin,int vVidaZonaDespegue)
	{
		super(vIdObjeto,vCoordenadaX,vCoordenadaY,vAltura,vAncho,vRotacion,vAngulo,vTipo);
		this.VidaZonaPolvorin=vVidaZonaPolvorin;
		this.VidaZonaDespegue=vVidaZonaDespegue;
	}

	public int ObtenerVidaZonaPolvorin()
	{
		return VidaZonaPolvorin;
	}
	
	public int ObtenerVidaZonaDespegue()
	{
		return VidaZonaDespegue;
	}
	
	public void SetearVidaZonaPolvorin(int vVidaZonaPolvorin)
	{
		this.VidaZonaPolvorin=vVidaZonaPolvorin;
	}
	
	public void SetearVidaZonaDespegue(int vVidaZonaDespuegue)
	{
		this.VidaZonaDespegue=vVidaZonaDespuegue;
	}
	

	
}
