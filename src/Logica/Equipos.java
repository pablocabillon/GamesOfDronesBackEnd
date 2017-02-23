package Logica;

import java.util.Hashtable;

/*********************************************
 * Descripción: 	Clase que contiene los métodos del diccionario de Equipops
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Equipos {

	private Hashtable<Integer,Objeto> Equipos;
	
	public Equipos()
	{
		Equipos=new Hashtable<>();
	}
	
	public boolean member(Integer vClave)
	{
		return Equipos.containsKey(vClave);
	}
	
	public void insert (Integer vClave,Objeto vObject)
	{
		Equipos.put(vClave,vObject);
	}
	
	public Objeto Find (Integer vClave)
	{
		return Equipos.get(vClave);
	}
}
