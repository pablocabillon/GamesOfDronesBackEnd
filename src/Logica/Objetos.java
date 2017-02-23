package Logica;

import java.util.Hashtable;

/*********************************************
 * Descripción: 	Clase que contiene los métodos del diccionario de Objeto
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Objetos {

	
	private Hashtable<Integer,Objeto> Objetos;
	
	public Objetos()
	{
		Objetos=new Hashtable<>();
	}
	
	public boolean member(Integer vClave)
	{
		return Objetos.containsKey(vClave);
	}
	
	public void insert (Integer vClave,Objeto vObject)
	{
		Objetos.put(vClave,vObject);
	}
	
	public Objeto Find (Integer vClave)
	{
		return Objetos.get(vClave);
	}
	
}
