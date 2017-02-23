package Logica;

import java.util.Hashtable;

/*********************************************
 * Descripci�n: 	Clase que contiene los m�todos del diccionario de Objeto
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
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
