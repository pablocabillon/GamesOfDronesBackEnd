package Logica;

/*********************************************
 * Descripción: 	Clase que contiene los atributos y funciones del Equipo
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Equipo {

	private int IdEquipo;
	private String Nombre;
	private Jugadores Jugadores;
	
	public Equipo(int vIdEquipo,String vNombre,Jugadores vJugadores)
	{
		this.IdEquipo=vIdEquipo;
		this.Nombre=vNombre;
		this.Jugadores=vJugadores;
	}
	
	public int ObtenerIdEquipo()
	{
		return IdEquipo;
	}
	
	public String ObtenerNombre()
	{
		return Nombre;
	}
	
	public Jugadores ObtenerJugadores()
	{
		return Jugadores;
	}
	
	public void SetearJugadores(Jugadores vJugadores)
	{
		this.Jugadores=vJugadores;
	}
	
}
