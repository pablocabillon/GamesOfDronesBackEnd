package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del dron Aéreo que extiende de Dron
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DronAereo extends Dron {
	
	private int MotoresActivos;
	private boolean TieneBomba;
	private boolean BombaRota;
	
	public DronAereo (int vIdObjeto,int vCoordenadaX,int vCoordenadaY,int vAltura,int vAncho,int vRotacion,int vAngulo,String vTipo,int vVelocidad,boolean vCamara,boolean vCanon,int vVision,int vMotoresActivos,boolean vTieneBomba,boolean vBombaRota)
	{
		super(vIdObjeto,vCoordenadaX,vCoordenadaY,vAltura,vAncho,vRotacion,vAngulo, vTipo,vVelocidad,vCamara,vCanon,vVision);
		this.MotoresActivos=vMotoresActivos;
		this.TieneBomba=vTieneBomba;
		this.BombaRota=vBombaRota;
	}
	
	public int ObtenerMotorActivo()
	{
		return MotoresActivos;
	}
	
	public boolean ObtenerTieneBomba()
	{
		return TieneBomba;
	}
	
	public boolean ObtenerBombaRota()
	{
		return BombaRota;
	}
	
	public void SetearMotorActivo(int vMotorActivo)
	{
		this.MotoresActivos=vMotorActivo;
	}
	
	public void SetearTieneBomba(boolean vTieneBomba)
	{
		this.TieneBomba=vTieneBomba;
	}
	
	public void SetearBombaRota(boolean vBombaRota)
	{
		this.BombaRota=vBombaRota;
	}
}
