package Persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import Logica.Base;
import Logica.Dron;
import Logica.Drones;
import Logica.Equipo;
import Logica.Objeto;
import Logica.Objetos;


public class pruebaDeDaos {

	
	public static void main(String[] args) {
	
		Properties p = new Properties();
		String nomArch = "config/config.properties";
		try {
				p.load(new FileInputStream(nomArch));
				String url = p.getProperty("url");
				String password = p.getProperty("password");
				String user = p.getProperty("user");

				
				//Prueba de insertar objeto
//				DAOObjeto daoObjeto = new DAOObjeto(url,user,password);
//				int idObjeto =2 ;
//				int coordX = 1;
//				int coordY = 1;
//				int altura = 10;
//				int ancho = 10;
//				int rotacion = 10;
//				int angulo = 90;
//				String tipo ="Dron";
//				Objeto objeto = new Objeto(idObjeto,coordX,coordY,altura,ancho,rotacion,angulo,tipo);
//				daoObjeto.InsertarObjeto(objeto,1);
//				
				//Prueba de existeObjeto
//				if(daoObjeto.ExisteObjeto(1,1))
//					System.out.println("Existe objeto");
//				else
//					System.out.println("No existe objeto");
				
				//Prueba de eliminar objeto
//				daoObjeto.EliminarObjeto(2,1);
				
				//Prueba de Devolver objeto
//				Objeto vObjeto= daoObjeto.DevolverObjeto(2,1);
//				System.out.println(vObjeto.ObtenerTipo());
				
				//Prueba de Devolver objetos partida
//				Objetos objetos = daoObjeto.DevolverObjetosPartida(1);
//				Objeto objeto = objetos.Find(4);
//				String id = Integer.toString(objeto.ObtenerIdObjeto());
//				String coordenadaX = Integer.toString(objeto.ObtenerCoordenadaX());
//				String coordenadaY = Integer.toString(objeto.ObtenerCoordenadaY());
//				String altura = Integer.toString(objeto.ObtenerAltura());
//				String ancho = Integer.toString(objeto.ObtenerAncho());
//				String rotacion = Integer.toString(objeto.ObtenerRotacion());
//				String angulo = Integer.toString(objeto.ObtenerAngulo());
//				
//				System.out.println("id: "+ id);
//				System.out.println("coordenadaX:" + coordenadaX);
//				System.out.println("coordenadaY:"+ coordenadaY);
//				System.out.println("altura: " + altura);
//				System.out.println("ancho: " + ancho);
//				System.out.println("rotacion: " + rotacion);
//				System.out.println("angulo: " + angulo);
//				System.out.println("tipo: "+objeto.ObtenerTipo());
				//Prueba del DAO equipo
////				DAOEquipo daoEquipo = new DAOEquipo(url,user,password);
//				daoEquipo.InsertarEquipo(1,1,"aereo");
				
//				if(daoEquipo.ExisteEquipo(2,1))
//					System.out.println("Existe equipo");
//				else
//					System.out.print("No existe equipo");
				
				//daoEquipo.EliminarEquipo(1,1);
				
//				Equipo equipo = daoEquipo.DevolverEquipo(1,1);
//				String idJugador = Integer.toString(equipo.ObtenerJugadores().Find(2).ObtenerIdJugador());
//				String nombre = equipo.ObtenerJugadores().Find(2).ObtenerNombre();
//				
//				Drones drones = equipo.ObtenerJugadores().Find(2).ObtenerColeccionDrones();
//				
//				Dron dron = drones.find(4);
//				
//				String idDron  = Integer.toString(dron.ObtenerIdObjeto());
//				String ancho = Integer.toString(dron.ObtenerAncho());
//				Boolean camara = dron.toString(dron.ObtenerCamara());
//				
//				System.out.println(idDron);
//				System.out.println(ancho);
				//Prueba del DAOJugador
//				DAOJugador daoJugador = new DAOJugador(url,user,password);
//				//daoJugador.InsertarJugador(1,1,1,"Carlos");
//				daoJugador.EliminarJugador(1,1,1);
//				
//				
//				if (daoJugador.ExisteJugador(1,1,1))
//					System.out.println("Existe jugador");
//				else
//					System.out.println("No existe jugador");
				
//				DAOPartida daoPartida = new DAOPartida(url,user,password);
//
//				daoPartida.EliminarPartida(1);
//				
//				if(daoPartida.ExistePartida(1))
//					System.out.println("Existe partida");
//				else 
//					System.out.println("No existe partida");
				
				DAOBase daoBase = new DAOBase(url,user,password);
				Base base = new Base(1,10,15,90,90,60,69,"Base",2,3);
				daoBase.InsertarBase(base,1);
				
				}
				catch (IOException e) {
				e.printStackTrace();
				}		
	}
	

	
}