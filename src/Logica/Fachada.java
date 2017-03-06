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
	
	
	
	public Fachada () {

		/*try{
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("../applications/DronWebSocket/server.properties"));
			AccesoBAse.SetearDriver(propiedades.getProperty("driver"));
			AccesoBAse.SetearUrl(propiedades.getProperty("url"));
			AccesoBAse.SetearUrl(propiedades.getProperty("user"));
			AccesoBAse.SetearPassword(propiedades.getProperty("password"));
		}catch (FileNotFoundException e){ 	
		   System.out.println("Error, el archivo de configuración no existe!");
		}
		 catch (IOException e){ 
		   System.out.println("Error, no se puede leer el archivo de configuración!");
		 }*/
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
			Equipo vEquipo2=new Equipo(1, "EquipoTerrestre", new Jugadores());
			vEquipos.insert(1, vEquipo1);
			vEquipos.insert(2, vEquipo2);
			Objetos vObjetos=new Objetos();
			vIdObjeto=vIdObjeto+1;
			Objeto vBase=new Objeto().GenerarObjeto(vIdObjeto, "Base");
			vObjetos.insert(vBase.ObtenerIdObjeto(), vBase);//Generarlo con IdObjeto 1
			int vEscenario=new Escenario().EscenarioAleatorio();//verificar
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
			vRespuesta.addProperty("BaseX",vBase.ObtenerCoordenadaX());
			vRespuesta.addProperty("BaseY",vBase.ObtenerCoordenadaY());
			vRespuesta.addProperty("ID",vBase.ObtenerIdObjeto());
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
				vIdObjeto=vIdObjeto+1;
				if(vIdObjetoAereo==1){
					vIdObjetoAereo=vIdObjetoAereo+1;
					vDron=(Dron) new Objeto().GenerarObjeto(2, "Aereo");
					vDrones.insert(vIdObjeto, vDron);
					vPartida.getObjetos().insert(2, vDron);
				}
				else
				{
					vDron=(Dron) new Objeto().GenerarObjeto(3, "Aereo");
					vDrones.insert(vIdObjeto, vDron);
					vPartida.getObjetos().insert(3, vDron);
				}
				

				vCantJugadoresAereo--;
				Jugador vJugador=new Jugador(vIdJugadorAereo, vNombreJugador, vDrones);
				vPartida.getEquipos().Find(1).ObtenerJugadores().insert(vJugador);
				vCantJugadores--;
		   }else{
			   
				vIdJugadorAereo=vIdJugadorAereo+1;
				vIdObjeto=vIdObjeto+1;
				vDron=(Dron) new Objeto().GenerarObjeto(2, "Aereo");
				vDrones.insert(vIdObjeto, vDron);
				vPartida.getObjetos().insert(2, vDron);
				vIdObjeto=vIdObjeto+1;
				Dron vDron1=(Dron) new Objeto().GenerarObjeto(3, "Aereo");
				vDrones.insert(vIdObjeto, vDron1);
				vPartida.getObjetos().insert(3, vDron1);
				vCantJugadoresAereo=0;
				Jugador vJugador=new Jugador(vIdJugadorAereo, vNombreJugador, vDrones);
				vPartida.getEquipos().Find(1).ObtenerJugadores().insert(vJugador);
				vCantJugadores--;
		   }

			if(vCantJugadores==0){ 
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
			else{
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
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
			vIdObjeto=vIdObjeto+1;
			if(vIdObjetoTerrestre==1){
				vIdObjetoTerrestre=vIdObjetoTerrestre+1;
				vDron=(Dron) new Objeto().GenerarObjeto(4, "Terrestre");
				vDrones.insert(vIdObjeto, vDron);
				vPartida.getObjetos().insert(4, vDron);
			}
			else
			{
				vDron=(Dron) new Objeto().GenerarObjeto(5, "Terrestre");
				vDrones.insert(vIdObjeto, vDron);
				vPartida.getObjetos().insert(5, vDron);
			}
			vCantJugadoresTerrestre--;
			Jugador vJugador=new Jugador(vIdJugadorTerrestre, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(2).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
	   }else{
		    
		    vIdJugadorTerrestre=vIdJugadorTerrestre+1;
			vIdObjeto=vIdObjeto+1;
			vDron=(Dron) new Objeto().GenerarObjeto(4, "Terrestre");
			vDrones.insert(vIdObjeto, vDron);
			vPartida.getObjetos().insert(4, vDron);
			vIdObjeto=vIdObjeto+1;
			Dron vDron1=(Dron) new Objeto().GenerarObjeto(5, "Terrestre");
			vDrones.insert(vIdObjeto, vDron1);
			vPartida.getObjetos().insert(5, vDron1);
			vCantJugadoresTerrestre=0;
			Jugador vJugador=new Jugador(vIdJugadorTerrestre, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(2).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
			}

			if(vCantJugadores==0){
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
			else{
				vRespuesta.addProperty("tipo", "UnirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				vRespuesta.addProperty("Escenario",vIdEscenarioPArtidaActual);	
				
			}
		
		return vRespuesta;
	}
	
	
	public JsonObject IniciarPartida(){
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "IniciarPartida");
		vRespuesta.addProperty("IdAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2).ObtenerIdObjeto());
		vRespuesta.addProperty("XAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2).ObtenerCoordenadaX());
		vRespuesta.addProperty("YAereo1", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(2).ObtenerCoordenadaY());
		vRespuesta.addProperty("IdTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4).ObtenerIdObjeto());
		vRespuesta.addProperty("XTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4).ObtenerCoordenadaX());
		vRespuesta.addProperty("YTerrestre1", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(4).ObtenerCoordenadaY());
		if(vCantJugadoresPartidaActual==4){
			vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3).ObtenerIdObjeto());
			vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3).ObtenerCoordenadaX());
			vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(3).ObtenerCoordenadaY());
			vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5).ObtenerIdObjeto());
			vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5).ObtenerCoordenadaX());
			vRespuesta.addProperty("YTerrestre2",  vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(5).ObtenerCoordenadaY());
			
		}else{
			vRespuesta.addProperty("IdAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3).ObtenerIdObjeto());
			vRespuesta.addProperty("XAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3).ObtenerCoordenadaX());
			vRespuesta.addProperty("YAereo2", vPartida.getEquipos().Find(1).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(3).ObtenerCoordenadaY());
			vRespuesta.addProperty("IdTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5).ObtenerIdObjeto());
			vRespuesta.addProperty("XTerrestre2", vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5).ObtenerCoordenadaX());
			vRespuesta.addProperty("YTerrestre2",  vPartida.getEquipos().Find(2).ObtenerJugadores().Find(0).ObtenerColeccionDrones().find(5).ObtenerCoordenadaY());
		}

		return vRespuesta;
		
	}

	public void GuardarPartida() throws IOException,SQLException 
	{
		long time = System.currentTimeMillis();
		java.sql.Date fecha_guardar = new java.sql.Date(time);
		new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarPartida(vIdPArtidaActual, fecha_guardar, vCantJugadores, vPartida.getEscenario());
		new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarEquipo(vPartida.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerNombre());
		new DAOEquipo(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarEquipo(vPartida.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerNombre());
		
		int vCantJugadoresEquipo1=vPartida.getEquipos().Find(1).ObtenerJugadores().CantJugadores();
		int vCantJugadoresEquipo2=vPartida.getEquipos().Find(2).ObtenerJugadores().CantJugadores();
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(1,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerNombre());
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(1,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerNombre());
			
		if(vCantJugadoresEquipo1==2)
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(2,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(2).ObtenerNombre());
			
		if(vCantJugadoresEquipo2==1)
			new DAOJugador(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarJugador(2,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(2).ObtenerNombre());
		
		GuardarDrones(vCantJugadoresEquipo1,vCantJugadoresEquipo2);
		
		InsertarBase();
		
		ResetearDatos();
		
	}
	
	public void GuardarDrones(int vCantJugadoresEquipo1,int vCantJugadoresEquipo2){
		
		
		for (int i=1;i<=vCantJugadoresEquipo1;i++){
			
			int vCantidadDronesJugador=vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().cantElementos();
			
			for(int j=1;j<=vCantidadDronesJugador;j++){
				
				int vIdObjeto=vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j).ObtenerIdObjeto();
				int vIdJugador=vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerIdJugador();
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j),vIdPArtidaActual, vIdJugador);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronAereo((DronAereo)vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j),vIdPArtidaActual, vIdJugador);
			}
		}
		
		for (int i=1;i<=vCantJugadoresEquipo2;i++){
			
			int vCantidadDronesJugador=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().cantElementos();
			
			for(int j=1;j<=vCantidadDronesJugador;j++){
				
				int vIdObjeto=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j).ObtenerIdObjeto();
				int vIdJugador=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerIdJugador();

				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdPArtidaActual, vIdJugador);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronTerrestre((DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdPArtidaActual, vIdJugador);


			}
		}
		
	}

	public void ResetearDatos(){
		
		vIdPArtidaActual=0;
		
		vIdJugadorAereo=0;
		
		vIdJugadorTerrestre=0;
		
		vIdObjeto=0;
		
		vIdObjetoAereo=1;
		
		vIdObjetoTerrestre=1;
		
		ArrayList<Jugador> vListaJugadores=new ArrayList<>();
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
	
	vIdObjeto=vPartida.getObjetos().Find(1).ObtenerIdObjeto();
	vCoordenadaX=vPartida.getObjetos().Find(1).ObtenerCoordenadaX();
	vCoordenadaY=vPartida.getObjetos().Find(1).ObtenerCoordenadaY();
	vAltura=vPartida.getObjetos().Find(1).ObtenerAltura();
	vRotacion=vPartida.getObjetos().Find(1).ObtenerRotacion();
	vAngulo=vPartida.getObjetos().Find(1).ObtenerAngulo();
	vTipo=vPartida.getObjetos().Find(1).ObtenerTipo();
	vAncho=vPartida.getObjetos().Find(1).ObtenerAncho();
	Base vBase=(Base) vPartida.getObjetos().Find(1);
	vVidaZonaPolvorin=vBase.ObtenerVidaZonaPolvorin();
	vVidaZonaDespegue=vBase.ObtenerVidaZonaDespegue();
	Base vBaseInsert=new Base(vIdObjeto, vCoordenadaX, vCoordenadaY, vAltura, vAncho, vRotacion, vAngulo, vTipo, vVidaZonaPolvorin, vVidaZonaDespegue);
	new DAOBase(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarBase(vBaseInsert,vIdPArtidaActual);
		
	}
	
	public Partida ReanudarPartida(int vIdPartida){
		
		return vPartida = new DAOPartida(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).DevolverPartida(vIdPartida);
		 
	}
	
	public JsonObject DisparaBase(int  vIdObjeto,String Sector){
		
		JsonObject vRespuesta = new JsonObject();
		vRespuesta.addProperty("tipo", "disparoBAse");
		Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
			switch (Sector) {
	    	case "\"polvorin\"":
	    		((Base) vObjeto).SetearVidaZonaPolvorin(((Base) vObjeto).ObtenerVidaZonaPolvorin()-1);
	    		break;
	    	case "\"despuegue\"":
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
			vGolpe=new Conversor().DronAereo((int)(Math.random()*(6))+1);
			switch (vGolpe) {
	    	case "Velocidad":
	    		if(((DronAereo) vObjeto).ObtenerVelocidad()>0)
	    			((DronAereo) vObjeto).SetearVelocidad(((DronAereo) vObjeto).ObtenerVelocidad()-1);
	    		vRespuesta.addProperty("golpe","Velocidad");
	    		break;
	    	case "Camara":
	    		((DronAereo) vObjeto).SetearCamara(false);
	    		vRespuesta.addProperty("golpe","Camara");
	    		break;
	    	case "Canon":
	    		((DronAereo) vObjeto).SetearCanon(false);
	    		vRespuesta.addProperty("golpe","Canon");
	    		break;
	    	case "Vision":
	    		if(((DronAereo) vObjeto).ObtenerVision()>0)
	    			((DronAereo) vObjeto).SetearVision(((DronAereo) vObjeto).ObtenerVision()-1);
	    		vRespuesta.addProperty("golpe","Vision");
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
			if(((DronAereo) vObjeto).ObtenerVelocidad()==0 && ((DronAereo) vObjeto).ObtenerCamara()==false && ((DronAereo) vObjeto).ObtenerCanon()==false 
					&& ((DronAereo) vObjeto).ObtenerMotorActivo()==0 && ((DronAereo) vObjeto).ObtenerVision()==0) 
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
			vGolpe=new Conversor().DronTerrestre((int)(Math.random()*(5))+1);
				switch (vGolpe) {
				case "Velocidad":
		    		if(((DronTerrestre) vObjeto).ObtenerVelocidad()>0)
		    			((DronTerrestre) vObjeto).SetearVelocidad(((DronTerrestre) vObjeto).ObtenerVelocidad()-1);
		    		vRespuesta.addProperty("golpe","Velocidad");
		    		break;
		    	case "Camara":
		    		((DronTerrestre) vObjeto).SetearCamara(false);
		    		vRespuesta.addProperty("golpe","Camara");
		    		break;
		    	case "Canon":
		    		((DronTerrestre) vObjeto).SetearCanon(false);
		    		vRespuesta.addProperty("golpe","Canon");
		    		break;
		    	case "Vision":
		    		if(((DronTerrestre) vObjeto).ObtenerVision()>0)
		    			((DronTerrestre) vObjeto).SetearVision(((DronTerrestre) vObjeto).ObtenerVision()-1);
		    		vRespuesta.addProperty("golpe","Vision");
		    		break;
		    	case "Blindaje":
		    		if(((DronTerrestre) vObjeto).ObtenerBlindaje()>0)
		    			((DronTerrestre) vObjeto).SetearBlindaje(((DronTerrestre) vObjeto).ObtenerBlindaje()-1);
		    		vRespuesta.addProperty("golpe","Blindaje");
		    		break;
		     default:
		  }
				if(((DronTerrestre) vObjeto).ObtenerVelocidad()==0 && ((DronTerrestre) vObjeto).ObtenerCamara()==false && ((DronTerrestre) vObjeto).ObtenerCanon()==false 
						&& ((DronTerrestre) vObjeto).ObtenerBlindaje()==0 && ((DronTerrestre) vObjeto).ObtenerVision()==0) 
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
