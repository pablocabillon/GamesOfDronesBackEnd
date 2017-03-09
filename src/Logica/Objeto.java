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
	
	
	private static final  int CoordinadaXMinAereo=10;
	private static final  int CoordinadaXMaxAereo=450;
	private static final  int CoordinadaYMaxAereo=450;
	private static final  int CoordinadaYMinAereo=10;
	
	private static final  int CoordinadaXMinTerrestre=2000;
	private static final  int CoordinadaXMaxTerrestre=2500;
	private static final  int CoordinadaYMaxTerrestre=2000;
	private static final  int CoordinadaYMinTerrestre=2500;
	
	
	private static final  int CoordinadaXMinBase=10;
	private static final  int CoordinadaXMaxBase=450;
	private static final  int CoordinadaYMaxBase=450;
	private static final  int CoordinadaYMinBase=10;
	
	
	public Objeto(){
		
	}
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
	
	public void SetearID(int vIdObjeto)
	{
		this.IdObjeto=vIdObjeto;
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

	public Objeto GenerarObjeto(int vIdObjeto,String vTipo){
		
		int vCoordenadaX;
		int vCoordenadaY;
		int vVidaZonaPolvorin=2;
		int vVidaZonaDespegue=10;
		Objeto vObjeto=null;
		int vVelocidad=4;
		Boolean vCamara=true;
		Boolean vCanon=true;
		int vVision=1;
		int vMotoresActivos=2;
		Boolean vTieneBomba=true;
		Boolean vBombaRota=false;
		int vBlindajeActivo=3;
		if(vTipo=="Base"){
			vCoordenadaX= (int)(Math.random()*(CoordinadaXMaxBase-CoordinadaXMinBase+1))+CoordinadaXMinBase;
			vCoordenadaY= (int)(Math.random()*(CoordinadaYMaxBase-CoordinadaYMinBase+1))+CoordinadaYMinBase;
			vObjeto=new Base(vIdObjeto, vCoordenadaX, vCoordenadaY, 0, 0, 0, 0, "Base", vVidaZonaPolvorin, vVidaZonaDespegue);
		}
		if(vTipo=="Aereo"){
			vCoordenadaX= (int)(Math.random()*(CoordinadaXMaxAereo-CoordinadaXMinAereo+1))+CoordinadaXMinAereo;
			vCoordenadaY= (int)(Math.random()*(CoordinadaYMaxAereo-CoordinadaYMinAereo+1))+CoordinadaYMinAereo;
			vObjeto=new DronAereo(vIdObjeto, vCoordenadaX, vCoordenadaY, 0, 0, 0, 0,  "Aereo", vVelocidad, vCamara, vCanon, vVision, vMotoresActivos, vTieneBomba, vBombaRota);
		}
		if(vTipo=="Terrestre"){
			vCoordenadaX= (int)(Math.random()*(CoordinadaXMaxTerrestre-CoordinadaXMinTerrestre+1))+CoordinadaXMinTerrestre;
			vCoordenadaY= (int)(Math.random()*(CoordinadaYMaxTerrestre-CoordinadaYMinTerrestre+1))+CoordinadaYMinTerrestre;
		vObjeto=new DronTerrestre(vIdObjeto, vCoordenadaX, vCoordenadaY, 0, 0, 0, 0,  "Terrestre", vVelocidad, vCamara, vCanon, vVision, vBlindajeActivo);
		}
		
		
		return vObjeto;
		
	}
}
