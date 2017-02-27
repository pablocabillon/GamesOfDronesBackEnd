package Logica;

import java.util.Hashtable;
import java.util.Iterator;


/*********************************************
 * Descripci�n: 	Clase que contiene los m�todos del diccionario de Equipops
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Equipos {

	private Hashtable<Integer,Equipo> Equipos;
	
	public Equipos()
	{
		Equipos=new Hashtable<>();
	}
	
	public boolean member(Integer vClave)
	{
		return Equipos.containsKey(vClave);
	}
	
	public void insert (Integer vClave,Equipo vObject)
	{
		Equipos.put(vClave,vObject);
	}
	
	public Equipo Find (Integer vClave)
	{
		return Equipos.get(vClave);
	}
	
}
