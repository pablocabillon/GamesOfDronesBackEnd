package Logica;


/*********************************************
 * Descripci�n: 	Clase que contiene los m�todos de la partida
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Partida {
	
	private Equipos Equipos;
	private Escenario Escenario;
	private Objetos Objetos;
	private int CantJugadores;

	public Partida(Equipos vEquipos,Escenario vEscenario,Objetos vObjetos,int vCantJugadores)
	{
		this.Equipos=vEquipos;
		this.Escenario=vEscenario;
		this.Objetos=vObjetos;
		this.CantJugadores=vCantJugadores;
	}
	
	public void IniciarPartida(int vIdPartida,int vCantJugadores)
	{
		
	}
	
	public void GuardarPartida(/*llega todod*/)
	{
		
	}
	
	public void  FinalizarPartida(int vIdPartida)
	{
		
	}
}