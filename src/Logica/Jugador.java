package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los métodos del Jugador
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Jugador {
	
	private int IdJugador;
	private String  Nombre;
	private Drones Drones;
	
	public Jugador()
	{
		
	}
	public Jugador(int vIdJugador,String vNombre,Drones vDrones)
	{
		this.IdJugador=vIdJugador;
		this.Nombre=vNombre;
		this.Drones=vDrones;
	}
	
	public int ObtenerIdJugador()
	{
		return IdJugador;
	}
	
	public String ObtenerNombre()
	{
		return Nombre;
	}
	
	public Drones ObtenerColeccionDrones()
	{
		return Drones;
	}
	
	public void SetearColeccionDrones(Drones vDrones)
	{
		this.Drones=vDrones;
	}
}
