package Logica;

import java.util.Random;

/*********************************************
 * Descripción: 	Clase que contiene un enumerado con los escenarios posibles
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Escenario {
	
	public static final int Escenario1=1;
	public static final int Escenario2=2;
	public static final int Escenario3=3;
	
	public enum Escenarios{Escenario1,Escenario2,Escenario3};
	
	
	public int EscenarioAleatorio(){
	    return (int)(Math.random()*(3))+1;
	    }

}
