package Logica;

import java.util.ArrayList;
import java.util.Iterator;

/*********************************************
 * Descripci�n: 	Clase que contiene los m{etodos de los Jugadores
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class Jugadores {
	
	private ArrayList<Jugador> Jugadores;
	
	
	public Jugadores()
	{
		Jugadores=new ArrayList<>();
	}
	
	public boolean member(Jugador vJugador)
	{
		return Jugadores.contains(vJugador);
	}
	
	public void insert (Jugador vJugador)
	{
		Jugadores.add(vJugador);
	}
	
	public Jugador Find (int vIdJugador )
	{
		return Jugadores.get(vIdJugador);
	}
	
	public int CantJugadores(){
		
		return Jugadores.size();
	}

}
