package Logica;

import Logica.Escenario.Escenarios;

/*********************************************
 * Descripción: 	Clase que contiene los métodos de la partida
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Partida {
	
	private Equipos Equipos;
	private int Escenario;
	private Objetos Objetos;
	private int CantJugadores;

	public Partida(Equipos vEquipos,int vEscenario,Objetos vObjetos,int vCantJugadores)
	{
		this.Equipos=vEquipos;
		this.Escenario=vEscenario;
		this.Objetos=vObjetos;
		this.CantJugadores=vCantJugadores;
	}
	
	public void IniciarPartida(int vIdPartida,int vCantJugadores)
	{
		
	}
	
	public void GuardarPartida(/*llega todo*/)
	{
		
	}
	
	public void  FinalizarPartida(int vIdPartida)
	{
		
	}

	public Equipos getEquipos() {
		return Equipos;
	}

	public void setEquipos(Equipos equipos) {
		Equipos = equipos;
	}

	public int getEscenario() {
		return Escenario;
	}

	public void setEscenario(int escenario) {
		Escenario = escenario;
	}

	public Objetos getObjetos() {
		return Objetos;
	}

	public void setObjetos(Objetos objetos) {
		Objetos = objetos;
	}

	public int getCantJugadores() {
		return CantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		CantJugadores = cantJugadores;
	}
	
}
