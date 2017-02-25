package Logica;

import java.util.HashMap;
import java.util.Map;

/*********************************************
 * Descripci�n: 	Clase que contiene los m�todos del diccionario de drones
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class Drones {
	
	private Map<Integer,Dron> Drones;
	
	public Drones() 
	{
		Drones = new HashMap<Integer, Dron>();
	}
	
	public boolean member(Integer clave)
	{
		return Drones.containsKey(clave);
	}
	
	public void insert(Integer clave, Dron obj)
	{
		Drones.put(clave, obj);
	}

	public Dron find(int clave)
	{
		return Drones.get(clave);
	}
	
	public void delete(Integer clave){
		Drones.remove(clave);
	}
	
	public int cantElementos()
	{
		return Drones.size();
	}
	

}
