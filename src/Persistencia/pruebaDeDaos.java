package Persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import Logica.Base;
import Logica.Dron;
import Logica.DronAereo;
import Logica.DronTerrestre;
import Logica.Drones;
import Logica.Equipo;
import Logica.Jugador;
import Logica.Jugadores;
import Logica.Objeto;
import Logica.Objetos;
import Logica.Partida;


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
//				DAOEquipo daoEquipo = new DAOEquipo(url,user,password);
//				Equipo equipo = daoEquipo.DevolverEquipo(2,1);
				
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
				//Jugador jugador = daoJugador.DevolverJugador(2,2,1);
//				Jugadores jugadores = daoJugador.DevolverJugadoresEquipo(2,1);
//				daoJugador.InsertarJugador(1,1,1,"Carlos");
//				daoJugador.EliminarJugador(1,1,1);
//				
//				
//				if (daoJugador.ExisteJugador(1,1,1))
//					System.out.println("Existe jugador");
//				else
//					System.out.println("No existe jugador");
				
				DAOPartida daoPartida = new DAOPartida(url,user,password);
				Partida partida = daoPartida.DevolverPartida(1);
//
//				daoPartida.EliminarPartida(1);
//				
//				if(daoPartida.ExistePartida(1))
//					System.out.println("Existe partida");
//				else 
//					System.out.println("No existe partida");
				
				
				
//				DAOBase daoBase = new DAOBase(url,user,password);
//				Base base = new Base(1,10,15,90,90,60,69,"Base",2,3);
//				daoBase.InsertarBase(base,1);
//				daoBase.EliminarBase(1,1);
//				//Base base = daoBase.DevolverBase(1,1);
//				String id = Integer.toString(base.ObtenerIdObjeto());
//				String coordX = Integer.toString(base.ObtenerCoordenadaX());
//				String coordY = Integer.toString(base.ObtenerCoordenadaY());
//				String altura = Integer.toString(base.ObtenerAltura());
//				String ancho = Integer.toString(base.ObtenerAncho());
//				String rotacion = Integer.toString(base.ObtenerRotacion());
//				String angulo = Integer.toString(base.ObtenerAngulo());
//				String tipo = base.ObtenerTipo();
//				
//				System.out.println(id);
//				System.out.println("Coordena X: "+coordX);
//				System.out.println("Coordena Y: "+coordY);
//				System.out.println("Altura : "+altura);
//				System.out.println("Ancho : "+ancho);
//				System.out.println("Rotacion: "+rotacion);
//				System.out.println("Angulo: " + angulo);
//				System.out.println("Tipo: " + tipo);
//				
//				if(daoBase.ExisteBase(1,1))
//					System.out.println("Existe base");
//				else
//					System.out.println("No existe base");
				
				//DAODron daoDron = new DAODron(url,user,password);
				//DronAereo DronAereo = new DronAereo(2,89,60,50,10,50,45,"Dron",3,false,true,2,3,false,true);
				//daoDron.InsertarDronAereo(DronAereo,1,1);
				//DronTerrestre dron = new DronTerrestre(3,56,60,70,60,80,20,"dron",5,true,true,1,3);
				//daoDron.InsertarDronTerrestre(dron,1,2);
				
				//Dron dron = new DronTerrestre(1,10,15,90,90,60,69,"Dron",3,true,true,2,4);
				//daoDron.InsertarDron(dron,1,2);
				
				}
				catch (IOException e) {
				e.printStackTrace();
				}		
	}
}