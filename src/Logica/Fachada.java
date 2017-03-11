package Logica;


/*********************************************
 * Descripción: 	Clase que contiene los métodos de la Fachada que se conectan con el webSocket
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Persistencia.AccesoBase;
import Persistencia.DAOBase;
import Persistencia.DAODron;
import Persistencia.DAOEquipo;
import Persistencia.DAOJugador;
import Persistencia.DAOObjeto;
import Persistencia.DAOPartida;
import server.Conversor;



public class Fachada {
	
	
	private static AccesoBase AccesoBAse = new AccesoBase();
	
	private static Partida  vPartida;
	
	private static int vIdPArtidaActual=0;
	
	private static int vCantJugadores;
	
	private static int vCantJugadoresPartidaActual=0;
	
	private static int vCantJugadoresAereo;
	
	private static int vCantJugadoresTerrestre;
	
	private static int vIdJugadorAereo=0;
	
	private static int vIdJugadorTerrestre=0;
	
	private static int vIdObjeto=0;
	
	private static int vIdObjetoAereo=1;
	
	private static int vIdObjetoTerrestre=1;
	
	private static int vIdEscenarioPArtidaActual;
	
	private static ArrayList<Jugador> vListaJugadores=new ArrayList<>();
	
	private static ArrayList<String> vListaJugadoresConectados=null;
	
	public Fachada () {

		
	}
	
	public void AccesoDatos(){
		
		try{
			Properties propiedades = new Properties();
			String nomArch ="C:\\config.properties";
			propiedades.load(new FileInputStream(nomArch));
			AccesoBAse.SetearUrl(propiedades.getProperty("url"));
			AccesoBAse.SetearUsuario(propiedades.getProperty("user"));
			AccesoBAse.SetearPassword(propiedades.getProperty("password"));
		}catch (FileNotFoundException e){ 	
		   System.out.println("Error, el archivo de configuración no existe!");
		}
		 catch (IOException e){ 
		   System.out.println("Error, no se puede leer el archivo de configuración!");
		 }
	}
	
	public JsonObject CrearPartida(int vCantidadJugadores) throws IOException{
		JsonObject vRespuesta = new JsonObject();
		
		if(vIdPArtidaActual>0){
			vRespuesta.addProperty("tipo","crearPartida");
			vRespuesta.addProperty("retorno","Ya hay una partida creada.");
		}
		else
		{
			Equipos vEquipos=new Equipos();
			Equipo vEquipo1=new Equipo(1, "EquipoAereo", new Jugadores());
			Equipo vEquipo2=new Equipo(2, "EquipoTerrestre", new Jugadores());
			vEquipos.insert(1, vEquipo1);
			vEquipos.insert(2, vEquipo2);
			Objetos vObjetos=new Objetos();
			Objeto vBase=new Objeto().GenerarObjeto(0, "Base");
			Objeto vBase1=new Objeto().GenerarObjeto(1, "Base");;
			vBase1.SetearCoordenadaY(vBase.ObtenerCoordenadaY()+150);
			vBase1.SetearCoordenadaX(vBase.ObtenerCoordenadaX());
			vObjetos.insert(vBase.ObtenerIdObjeto(), vBase);
			vObjetos.insert(vBase1.ObtenerIdObjeto(), vBase1);
			int vEscenario=new Escenario().EscenarioAleatorio();
			vIdEscenarioPArtidaActual=vEscenario;
			vPartida=new Partida(vEquipos, vEscenario, vObjetos, vCantidadJugadores);
			vIdPArtidaActual=vIdPArtidaActual+1;
			vCantJugadores=vCantidadJugadores;
			vCantJugadoresPartidaActual=vCantidadJugadores;
			if(vCantidadJugadores==4){
				vCantJugadoresAereo=2;
				vCantJugadoresTerrestre=2;
			}
			else
			{
				vCantJugadoresAereo=1;
				vCantJugadoresTerrestre=1;
			}
			vRespuesta.addProperty("tipo","crearPartida");
			vRespuesta.addProperty("retorno","ok");	
		}
		
		return vRespuesta;
	}
	
	public JsonObject UnirsePartidaAereo(String vNombreJugador){
		
		JsonObject vRespuesta = new JsonObject();
		Drones vDrones=new Drones();
		Dron vDron=null;
		if(vIdPArtidaActual==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "No hay una partida creada.");
			return vRespuesta;
		}
		if(vCantJugadores==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores de la partida.");
			return vRespuesta;
			
		}else if(vCantJugadoresAereo==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores del grupo.");
			return vRespuesta;
		}
	   else if(vCantJugadoresPartidaActual==4){
				vIdJugadorAereo=vIdJugadorAereo+1;
				if(vIdObjetoAereo==1){
					vIdObjetoAereo=vIdObjetoAereo+1;
					vDron=(Dron) new Objeto().GenerarObjeto(2, "Aereo");
					vDrones.insert(2, vDron);
					vPartida.getObjetos().insert(2, vDron);
				}
				else
				{
					vDron=(Dron) new Objeto().GenerarObjeto(3, "Aereo");
					vDrones.insert(3, vDron);
					vPartida.getObjetos().insert(3, vDron);
				}
				

				vCantJugadoresAereo--;
				Jugador vJugador=new Jugador(vIdJugadorAereo, vNombreJugador, vDrones);
				vPartida.getEquipos().Find(1).ObtenerJugadores().insert(vJugador);
				vCantJugadores--;
		   }else{
			   
				vIdJugadorAereo=vIdJugadorAereo+1;
				vDron=(Dron) new Objeto().GenerarObjeto(2, "Aereo");
				vDrones.insert(2, vDron);
				vPartida.getObjetos().insert(2, vDron);
				Dron vDron1=(Dron) new Objeto().GenerarObjeto(3, "Aereo");
				vDrones.insert(3, vDron1);
				vPartida.getObjetos().insert(3, vDron1);
				vCantJugadoresAereo=0;
				Jugador vJugador=new Jugador(vIdJugadorAereo, vNombreJugador, vDrones);
				vPartida.getEquipos().Find(1).ObtenerJugadores().insert(vJugador);
				vCantJugadores--;
		   }

			if(vCantJugadores==0){ 
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
			else{
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
						
		return vRespuesta;
	}

	public JsonObject UnirsePartidaTerrestre(String vNombreJugador){
		
		JsonObject vRespuesta = new JsonObject();
		Drones vDrones=new Drones();
		Dron vDron=null;
		if(vIdPArtidaActual==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "No hay una partida creada.");
			return vRespuesta;
		}
		if(vCantJugadores==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores de la partida.");
			return vRespuesta;
		}else if(vCantJugadoresTerrestre==0){
			vRespuesta.addProperty("tipo", "UnirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores del grupo.");
			return vRespuesta;
		}
	   else if(vCantJugadoresPartidaActual==4){
		    vIdJugadorTerrestre=vIdJugadorTerrestre+1;
			if(vIdObjetoTerrestre==1){
				vIdObjetoTerrestre=vIdObjetoTerrestre+1;
				vDron=(Dron) new Objeto().GenerarObjeto(4, "Terrestre");
				vDrones.insert(4, vDron);
				vPartida.getObjetos().insert(4, vDron);
			}
			else
			{
				vDron=(Dron) new Objeto().GenerarObjeto(5, "Terrestre");
				vDrones.insert(5, vDron);
				vPartida.getObjetos().insert(5, vDron);
			}
			vCantJugadoresTerrestre--;
			Jugador vJugador=new Jugador(vIdJugadorTerrestre, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(2).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
	   }else{
		    
		    vIdJugadorTerrestre=vIdJugadorTerrestre+1;
			vDron=(Dron) new Objeto().GenerarObjeto(4, "Terrestre");
			vDrones.insert(4, vDron);
			vPartida.getObjetos().insert(4, vDron);
			Dron vDron1=(Dron) new Objeto().GenerarObjeto(5, "Terrestre");
			vDrones.insert(5, vDron1);
			vPartida.getObjetos().insert(5, vDron1);
			vCantJugadoresTerrestre=0;
			Jugador vJugador=new Jugador(vIdJugadorTerrestre, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(2).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
			}

			if(vCantJugadores==0){
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
			else{
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
		
		return vRespuesta;
	}
	
	public JsonObject IniciarPartida(){
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "IniciarPartida");
		vRespuesta.addProperty("XBase1", vPartida.getObjetos().Find(0).ObtenerCoordenadaX());
		vRespuesta.addProperty("YBase1", vPartida.getObjetos().Find(0).ObtenerCoordenadaY());
		vRespuesta.addProperty("XBase2", vPartida.getObjetos().Find(1).ObtenerCoordenadaX());
		vRespuesta.addProperty("YBase2", vPartida.getObjetos().Find(1).ObtenerCoordenadaY());
		int vIndice=0;
		for(int i=0;i<6;i++)
		{
			
			if(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i)!=null){
				if(vIndice==0){
				vRespuesta.addProperty("IdAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
				vIndice++;
				}else{
				vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
				}
			}
		}
		vIndice=0;
		for(int i=0;i<6;i++)
		{
			if(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i)!=null){
				if(vIndice==0){
				vRespuesta.addProperty("IdTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
				vIndice++;
				}else{
				vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
					
				}
			}
	
		}
		if(vCantJugadoresPartidaActual==4){
			for(int i=0;i<6;i++)
			{
				if(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i)!=null){
					vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
					vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
					vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
				}
		
			}
			for(int i=0;i<6;i++)
			{
				if(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i)!=null){
					vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
					vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
					vRespuesta.addProperty("YTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
				}
		
			}
			
		}
		vRespuesta.addProperty("CantidadJugadores",vCantJugadoresPartidaActual);
		
		return vRespuesta;
		
	}

	public JsonObject GuardarPartida(JsonElement vDatos) 
	{
		JsonObject vRespuesta = new JsonObject();
		try{
			
			vRespuesta.addProperty("tipo", "guardarPartida");
			AccesoDatos();
			Partida vPartidaAux = new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).DevolverPartida(1);
			if(vPartidaAux!=null)
				BorrarDatos(vPartidaAux);
			long time = System.currentTimeMillis();
			java.sql.Date fecha_guardar = new java.sql.Date(time);
			new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarPartida(vIdPArtidaActual, fecha_guardar, vCantJugadoresPartidaActual, vPartida.getEscenario());
			new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarEquipo(vPartida.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerNombre());
			new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarEquipo(vPartida.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerNombre());
			
			int vCantJugadoresEquipo1=vPartida.getEquipos().Find(1).ObtenerJugadores().CantJugadores();
			int vCantJugadoresEquipo2=vPartida.getEquipos().Find(2).ObtenerJugadores().CantJugadores();
				new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(1,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerNombre());
				new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(1,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerNombre());
				
			if(vCantJugadoresEquipo1==2)
				new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(2,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerNombre());
				
			if(vCantJugadoresEquipo2==2)
				new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(2,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerNombre());
			
			GuardarDrones(vCantJugadoresEquipo1,vCantJugadoresEquipo2,vDatos);
			
			InsertarBase();
			
			vRespuesta.addProperty("retorno", "ok");
		}
		
		catch (Exception e){ 
				vRespuesta.addProperty("retorno", e.getMessage());
			 }
		
		return vRespuesta;

	}
	
	public void BorrarDatos(Partida vPArtidaAux){
		new DAOBase(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarBase(0, vIdPArtidaActual);
		new DAOBase(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarBase(1, vIdPArtidaActual);
		new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(0, vIdPArtidaActual);
		new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(1, vIdPArtidaActual);
		new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronAereo(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2), vIdPArtidaActual);
		new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronTerrestre(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4), vIdPArtidaActual);
		new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2), vIdPArtidaActual);
		new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4), vIdPArtidaActual);
		new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(2, vIdPArtidaActual);
		new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(4, vIdPArtidaActual);
		if(vPArtidaAux.getCantJugadores()==4){
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronAereo(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronTerrestre(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5), vIdPArtidaActual);
			new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(3, vIdPArtidaActual);
			new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(5, vIdPArtidaActual);
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarJugador(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerIdJugador(), vPArtidaAux.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual);
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarJugador(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerIdJugador(), vPArtidaAux.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual);
		}else{
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronAereo(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDronTerrestre(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3), vIdPArtidaActual);
			new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarDron(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5), vIdPArtidaActual);
			new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(3, vIdPArtidaActual);
			new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarObjeto(5, vIdPArtidaActual);
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarJugador(vPArtidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerIdJugador(), vPArtidaAux.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual);
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarJugador(vPArtidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerIdJugador(), vPArtidaAux.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual);
			
		}
		
		new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarEquipo(vPartida.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual);
		new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarEquipo(vPartida.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual);
		new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).EliminarPartida(vIdPArtidaActual);
	}
	
	public void GuardarDrones(int vCantJugadoresEquipo1,int vCantJugadoresEquipo2,JsonElement vDatos){
	
			if(vDatos.getAsJsonObject().get("aereo1X").toString()!=null){
				vPartida.getObjetos().Find(2).SetearCoordenadaX(vDatos.getAsJsonObject().get("aereo1X").getAsInt());
				vPartida.getObjetos().Find(2).SetearCoordenadaY(vDatos.getAsJsonObject().get("aereo1Y").getAsInt());
				new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarObjeto(vPartida.getObjetos().Find(2), vIdPArtidaActual);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerIdJugador());
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronAereo((DronAereo)vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerIdJugador());
			}
			if(vDatos.getAsJsonObject().get("terrestre1X").toString()!=null){
				vPartida.getObjetos().Find(4).SetearCoordenadaX(vDatos.getAsJsonObject().get("terrestre1X").getAsInt());
				vPartida.getObjetos().Find(4).SetearCoordenadaY(vDatos.getAsJsonObject().get("terrestre1Y").getAsInt());
				new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarObjeto(vPartida.getObjetos().Find(4), vIdPArtidaActual);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerIdJugador());
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronTerrestre((DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerIdJugador());
				
			}
			if(vDatos.getAsJsonObject().get("aereo2X").toString()!=null){
				vPartida.getObjetos().Find(3).SetearCoordenadaX(vDatos.getAsJsonObject().get("aereo2X").getAsInt());
				vPartida.getObjetos().Find(3).SetearCoordenadaY(vDatos.getAsJsonObject().get("aereo2Y").getAsInt());
				new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarObjeto(vPartida.getObjetos().Find(3), vIdPArtidaActual);
				if(vCantJugadoresEquipo1==1){
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerIdJugador());
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronAereo((DronAereo)vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerIdJugador());
				}else{
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerIdJugador());
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronAereo((DronAereo)vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3),vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerIdJugador());
				}
			}

			if(vDatos.getAsJsonObject().get("terrestre2X").toString()!=null){
				vPartida.getObjetos().Find(5).SetearCoordenadaX(vDatos.getAsJsonObject().get("terrestre2X").getAsInt());
				vPartida.getObjetos().Find(5).SetearCoordenadaY(vDatos.getAsJsonObject().get("terrestre2Y").getAsInt());
				new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarObjeto(vPartida.getObjetos().Find(5), vIdPArtidaActual);
				if(vCantJugadoresEquipo2==1){
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerIdJugador());
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronTerrestre((DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerIdJugador());
				}else{
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerIdJugador());
					new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronTerrestre((DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerIdJugador());
				}
				
			}
		
	}

	public void ResetearDatos(){
		
		Partida  vPartida;
		
		vIdPArtidaActual=0;
		
		vCantJugadores=0;
		
		vCantJugadoresPartidaActual=0;
		
		vCantJugadoresAereo=0;
		
		vCantJugadoresTerrestre=0;
		
		vIdJugadorAereo=0;
		
		vIdJugadorTerrestre=0;
		
		vIdObjeto=0;
		
		vIdObjetoAereo=1;
		
		vIdObjetoTerrestre=1;
		
		vIdEscenarioPArtidaActual=0;
		
		vListaJugadores=new ArrayList<>();
		
		vListaJugadoresConectados=null;
		
		vPartida=null;
		
	}
	
	public void InsertarBase(){
	
	int vIdObjeto;
	int vCoordenadaX;
	int vCoordenadaY;
	int vAltura;
	int vAncho;
	int vRotacion;
	int vAngulo;
	String vTipo;
	int vVidaZonaPolvorin;
	int vVidaZonaDespegue;
	for(int i=0;i<2;i++){
		
		vIdObjeto=vPartida.getObjetos().Find(i).ObtenerIdObjeto();
		vCoordenadaX=vPartida.getObjetos().Find(i).ObtenerCoordenadaX();
		vCoordenadaY=vPartida.getObjetos().Find(i).ObtenerCoordenadaY();
		vAltura=vPartida.getObjetos().Find(i).ObtenerAltura();
		vRotacion=vPartida.getObjetos().Find(i).ObtenerRotacion();
		vAngulo=vPartida.getObjetos().Find(i).ObtenerAngulo();
		vTipo=vPartida.getObjetos().Find(i).ObtenerTipo();
		vAncho=vPartida.getObjetos().Find(i).ObtenerAncho();
		Base vBase=(Base) vPartida.getObjetos().Find(i);
		vVidaZonaPolvorin=vBase.ObtenerVidaZonaPolvorin();
		vVidaZonaDespegue=vBase.ObtenerVidaZonaDespegue();
		Base vBaseInsert=new Base(vIdObjeto, vCoordenadaX, vCoordenadaY, vAltura, vAncho, vRotacion, vAngulo, vTipo, vVidaZonaPolvorin, vVidaZonaDespegue);
		new DAOObjeto(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarObjeto(vBase, vIdPArtidaActual);
		new DAOBase(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarBase(vBaseInsert,vIdPArtidaActual);
	}


		
	}
	
	public JsonObject ReanudarPartida(String vNombre){
		
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "reanudarPartida");
		boolean existe=false;
		AccesoDatos();
		Partida vPartidaAux = new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).DevolverPartida(1);
		vCantJugadoresPartidaActual=vPartidaAux.getCantJugadores();
		if(vListaJugadoresConectados==null){
			vListaJugadoresConectados=new ArrayList<>();
			vListaJugadoresConectados.add(vPartidaAux.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerNombre());
			vListaJugadoresConectados.add(vPartidaAux.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerNombre());
			if(vCantJugadoresPartidaActual==4){
				vListaJugadoresConectados.add(vPartidaAux.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerNombre());
				vListaJugadoresConectados.add(vPartidaAux.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerNombre());
			}
		}
			if(vListaJugadoresConectados.contains(vNombre)){
				existe=true;
				vListaJugadoresConectados.remove(vNombre);
			}
			
			if(!existe)
				vRespuesta.addProperty("retorno", "No existe Jugador.");
			else if(vListaJugadoresConectados.size()==0)
					vRespuesta.addProperty("retorno", "iniciar");
			else
				vRespuesta.addProperty("retorno", "esperar");
			
		return vRespuesta;
	}
	
	public JsonObject IniciarPartidaReanudada(){
		
		ResetearDatos();
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "iniciarPartidaReanudar");
		AccesoDatos();
		vPartida = new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).DevolverPartida(1);
		vRespuesta.addProperty("XBase1",vPartida.getObjetos().Find(0).ObtenerCoordenadaX());
		vRespuesta.addProperty("YBase1", vPartida.getObjetos().Find(0).ObtenerCoordenadaY());
		vRespuesta.addProperty("XBase2", vPartida.getObjetos().Find(1).ObtenerCoordenadaX());
		vRespuesta.addProperty("YBase2", vPartida.getObjetos().Find(1).ObtenerCoordenadaY());
		vRespuesta.addProperty("CantidadJugadores", vPartida.getCantJugadores());
		vRespuesta.addProperty("escenario", vPartida.getEscenario());
		int vIndice=0;
		for(int i=0;i<6;i++)
		{
			
			if(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i)!=null){
				if(vIndice==0){
				vRespuesta.addProperty("IdAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
				vRespuesta.addProperty("camaraAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCamara());
				vRespuesta.addProperty("canonAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCanon());
				vIndice++;
				}else{
				vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
				vRespuesta.addProperty("camaraAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCamara());
				vRespuesta.addProperty("canonAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCanon());
				}
			}
		}
		vIndice=0;
		for(int i=0;i<6;i++)
		{
			if(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i)!=null){
				if(vIndice==0){
				vRespuesta.addProperty("IdTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
				vRespuesta.addProperty("camaraTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCamara());
				vRespuesta.addProperty("canonTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCanon());
				DronTerrestre vDronAux=(DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i);
				vRespuesta.addProperty("blindajeTerrestre1",vDronAux.ObtenerBlindaje());
				vIndice++;
				}else{
				vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
				vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
				vRespuesta.addProperty("YTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
				vRespuesta.addProperty("camaraTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCamara());	
				vRespuesta.addProperty("canonTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i).ObtenerCanon());
				DronTerrestre vDronAux=(DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(i);
				vRespuesta.addProperty("blindajeTerrestre2",vDronAux.ObtenerBlindaje());
				}
			}
	
		}
		if(vPartida.getEquipos().Find(1).ObtenerJugadores().CantJugadores()==2){
			for(int i=0;i<6;i++)
			{
				if(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i)!=null){
					vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
					vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
					vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());
					vRespuesta.addProperty("camaraAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCamara());
					vRespuesta.addProperty("canonAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCanon());
				}
		
			}
		}
		if(vPartida.getEquipos().Find(2).ObtenerJugadores().CantJugadores()==2){
			for(int i=0;i<6;i++)
			{
				if(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i)!=null){
					vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerIdObjeto());
					vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaX());
					vRespuesta.addProperty("YTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCoordenadaY());	
					vRespuesta.addProperty("camaraTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCamara());	
					vRespuesta.addProperty("canonTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i).ObtenerCanon());
					DronTerrestre vDronAux=(DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(i);
					vRespuesta.addProperty("blindajeTerrestre2",vDronAux.ObtenerBlindaje());
				}
		
			}
			
		}
		return vRespuesta;
	}
	
	public JsonObject DisparaBase(int  vIdObjeto,String Sector){
		
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "disparoBAse");
		Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
			switch (Sector) {
	    	case "\"polvorin\"":
	    		((Base) vObjeto).SetearVidaZonaPolvorin(((Base) vObjeto).ObtenerVidaZonaPolvorin()-1);
	    		break;
	    	case "\"despegue\"":
	    		((Base) vObjeto).SetearVidaZonaDespegue(((Base) vObjeto).ObtenerVidaZonaDespegue()-1);
	    		break;
		     default:
		  }
			
			if(((Base) vObjeto).ObtenerVidaZonaPolvorin()==0 ||((Base) vObjeto).ObtenerVidaZonaDespegue()==0)
				vRespuesta.addProperty("destruida", true);
			else
				vRespuesta.addProperty("destruida", false);
			
			return vRespuesta;
		
	}
	
	public JsonObject GolpeDronAereo(int  vIdObjeto){
		
		JsonObject vRespuesta = new  JsonObject();
		vRespuesta.addProperty("tipo","disparoTerrestre");
		DronAereo vObjeto=(DronAereo)vPartida.getObjetos().Find(vIdObjeto);
		
		vRespuesta.addProperty("IdDronAereo",vObjeto.ObtenerIdObjeto());
		String vGolpe;
			vGolpe=new Conversor().DronAereo((int)(Math.random()*(4))+1);
			switch (vGolpe) {
	    	case "Camara":
	    		((DronAereo) vObjeto).SetearCamara(false);
	    		vRespuesta.addProperty("golpe","Camara");
	    		break;
	    	case "Canon":
	    		((DronAereo) vObjeto).SetearCanon(false);
	    		vRespuesta.addProperty("golpe","Canon");
	    		break;
	    	case "Motores":
	    		if(((DronAereo) vObjeto).ObtenerMotorActivo()>0)
	    			((DronAereo) vObjeto).SetearMotorActivo(((DronAereo) vObjeto).ObtenerMotorActivo()-1);
	    		vRespuesta.addProperty("golpe","Motores");
	    		break;
	    	case "Bomba":
	    		((DronAereo) vObjeto).SetearBombaRota(true);	
	    		vRespuesta.addProperty("golpe","Bomba");
	    		break;
	     default:
	  }
			if(((DronAereo) vObjeto).ObtenerCamara()==false && ((DronAereo) vObjeto).ObtenerCanon()==false 
					&& ((DronAereo) vObjeto).ObtenerMotorActivo()==0) 
					vRespuesta.addProperty("destruida",true);
			else if(((DronAereo) vObjeto).ObtenerBombaRota()==true)
					vRespuesta.addProperty("destruida",true);
			else
				vRespuesta.addProperty("destruida",false);
			return vRespuesta;
		
	}
	
	public JsonObject GolpeDronTerrestre(int  vIdObjetoAereo,int vIdobjetoTerrestre,String vTipoDisparo){
	
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo","disparoAereo");
	Objeto vObjeto=vPartida.getObjetos().Find(vIdobjetoTerrestre);
	
	vRespuesta.addProperty("IdDronAereo",vIdObjetoAereo);
	vRespuesta.addProperty("IdDronTerrestre",vObjeto.ObtenerIdObjeto());
	if(vTipoDisparo.equals("\"bala\""))
		vTipoDisparo="bala";
	else{
		vTipoDisparo="bomba";
		Objeto vObjetoAereo=vPartida.getObjetos().Find(vIdObjetoAereo);
		((DronAereo) vObjetoAereo).SetearTieneBomba(false);
	}
	vRespuesta.addProperty("TipoDisparo",vTipoDisparo);
	String vGolpe;
			vGolpe=new Conversor().DronTerrestre((int)(Math.random()*(3))+1);
				switch (vGolpe) {
		    	case "Camara":
		    		((DronTerrestre) vObjeto).SetearCamara(false);
		    		vRespuesta.addProperty("golpe","Camara");
		    		break;
		    	case "Canon":
		    		((DronTerrestre) vObjeto).SetearCanon(false);
		    		vRespuesta.addProperty("golpe","Canon");
		    		break;
		    	case "Blindaje":
		    		if(((DronTerrestre) vObjeto).ObtenerBlindaje()>0)
		    			((DronTerrestre) vObjeto).SetearBlindaje(((DronTerrestre) vObjeto).ObtenerBlindaje()-1);
		    		vRespuesta.addProperty("golpe","Blindaje");
		    		break;
		     default:
		  }
				if(((DronTerrestre) vObjeto).ObtenerCamara()==false && ((DronTerrestre) vObjeto).ObtenerCanon()==false 
						&& ((DronTerrestre) vObjeto).ObtenerBlindaje()==0) 
						vRespuesta.addProperty("destruida",true);
				else if (vTipoDisparo.equals("bomba"))
					vRespuesta.addProperty("destruida",true);
				else
					vRespuesta.addProperty("destruida",false);
				return vRespuesta;
	
}

	public JsonObject TirarBomba(int vIdObjeto){
	
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo","TiraBomba");
		Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
		vRespuesta.addProperty("IdDronAereo",vObjeto.ObtenerIdObjeto());
		((DronAereo) vObjeto).SetearTieneBomba(false);
		vRespuesta.addProperty("Bomba",false);
	
	return vRespuesta;
	
}
}
