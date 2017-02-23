package Logica;

/*********************************************
 * Descripci�n: 	Clase que contiene los atributos y funciones del dron A�reo que extiende de Dron
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DronAereo extends Dron {
	
	private int MotoresActivos;
	private boolean TieneBomba;
	private boolean BombaRota;
	
	public DronAereo (int vVelocidad,boolean vCamara,boolean vCanon,int vVision,int vMotoresActivos,boolean vTieneBomba,boolean vBombaRota)
	{
		super(vVelocidad,vCamara,vCanon,vVision);
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
