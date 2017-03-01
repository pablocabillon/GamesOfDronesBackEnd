package Logica;

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
	
	private static int vIdJugadorAereo=0;
	
	private static int vIdJugadorTerrestre=0;
	
	private static ArrayList<Jugador> vListaJugadores=new ArrayList<>();
	
	public Fachada () {

		try{
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
			Equipo vEquipo2=new Equipo(1, "EquipoTerrestre", new Jugadores());
			vEquipos.insert(1, vEquipo1);
			vEquipos.insert(2, vEquipo2);
			Objetos vObjetos=new Objetos();
			Objeto vBase=new Objeto().GenerarObjeto(1, "Base");
			vObjetos.insert(vBase.ObtenerIdObjeto(), vBase);//Generarlo con IdObjeto 1
			int vEscenario=new Escenario().EscenarioAleatorio().ordinal();//verificar
			vPartida=new Partida(vEquipos, vEscenario, vObjetos, vCantidadJugadores);
			vIdPArtidaActual=vIdPArtidaActual+1;
			vCantJugadores=vCantidadJugadores;
			vRespuesta.addProperty("tipo","crearPartida");
			vRespuesta.addProperty("retorno","ok");	
			vRespuesta.addProperty("Escenario",vEscenario);	
			vRespuesta.addProperty("BaseX",vBase.ObtenerCoordenadaX());
			vRespuesta.addProperty("BaseY",vBase.ObtenerCoordenadaY());
			vRespuesta.addProperty("ID",vBase.ObtenerIdObjeto());
		}
		
		return vRespuesta;
	}
	
	public JsonObject UnirsePartidaAereo(String vNombreJugador,int vIdDron,int vIdDron1){
		
		JsonObject vRespuesta = new JsonObject();
		
		if(vCantJugadores==0){
			vRespuesta.addProperty("tipo", "unirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores.");
			
		}
	   else{
			vIdJugadorAereo=vIdJugadorAereo+1;
			Drones vDrones=new Drones();
			Dron vDron=(Dron) new Objeto().GenerarObjeto(vIdDron, "Aereo");
			vDrones.insert(vIdDron, vDron);
			vPartida.getObjetos().insert(vIdDron, vDron);
			if(vIdDron1>0){
				Dron vDron1=(Dron) new Objeto().GenerarObjeto(vIdDron1, "Aereo");
				vDrones.insert(vIdDron1, vDron1);
				vPartida.getObjetos().insert(vIdDron1, vDron1);
			}
			Jugador vJugador=new Jugador(vIdJugadorAereo, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(1).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
			if(vCantJugadores==0){
				vRespuesta.addProperty("tipo", "unirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				
			}
			else{
				vRespuesta.addProperty("tipo", "unirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				
			}
						
			}
		
		return vRespuesta;
	}

	public JsonObject UnirsePartidaTerrestre(String vNombreJugador,int vIdDron,int vIdDron1){
		
		JsonObject vRespuesta = new JsonObject();
		
		if(vCantJugadores==0){
			vRespuesta.addProperty("tipo", "unirPartida");
			vRespuesta.addProperty("retorno", "Ya se encuentra la totalidad de jugadores.");
			
		}
	   else{
		    vIdJugadorTerrestre=vIdJugadorTerrestre+1;
			Drones vDrones=new Drones();
			Dron vDron=(Dron) new Objeto().GenerarObjeto(vIdDron, "Terrestre");
			vDrones.insert(vIdDron, vDron);
			vPartida.getObjetos().insert(vIdDron, vDron);
			if(vIdDron1>0){
				Dron vDron1=(Dron) new Objeto().GenerarObjeto(vIdDron1, "Terrestre");
				vDrones.insert(vIdDron1, vDron1);
				vPartida.getObjetos().insert(vIdDron1, vDron1);
			}
			Jugador vJugador=new Jugador(vIdJugadorTerrestre, vNombreJugador, vDrones);
			vPartida.getEquipos().Find(2).ObtenerJugadores().insert(vJugador);
			vCantJugadores--;
			if(vCantJugadores==0){
				vRespuesta.addProperty("tipo", "unirPartida");
				vRespuesta.addProperty("retorno","iniciar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				
			}
			else{
				vRespuesta.addProperty("tipo", "unirPartida");
				vRespuesta.addProperty("retorno","esperar");
				vRespuesta.addProperty("DronX", vDron.ObtenerCoordenadaX());
				vRespuesta.addProperty("DronY", vDron.ObtenerCoordenadaY());
				vRespuesta.addProperty("DronId",vDron.ObtenerIdObjeto());
				
			}
						
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
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdObjeto, vIdPArtidaActual, vIdJugador);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronAereo((DronAereo)vPartida.getEquipos().Find(1).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdObjeto, vIdPArtidaActual, vIdJugador);
			}
		}
		
		for (int i=1;i<=vCantJugadoresEquipo2;i++){
			
			int vCantidadDronesJugador=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().cantElementos();
			
			for(int j=1;j<=vCantidadDronesJugador;j++){
				
				int vIdObjeto=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j).ObtenerIdObjeto();
				int vIdJugador=vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerIdJugador();
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDron(vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdObjeto, vIdPArtidaActual, vIdJugador);
				new DAODron(AccesoBAse.ObtenerUrl(),AccesoBAse.ObtenerUsuario(),AccesoBAse.ObtenerPassword()).InsertarDronTerrestre((DronTerrestre)vPartida.getEquipos().Find(2).ObtenerJugadores().Find(i).ObtenerColeccionDrones().find(j), vIdObjeto, vIdPArtidaActual, vIdJugador);
			}
		}
		
	}

	public void ResetearDatos(){
		
		vIdPArtidaActual=0;
		
		vIdJugadorAereo=0;
		
		vIdJugadorTerrestre=0;
		
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
	Base vBase=(Base) vPartida.getObjetos().Find(1);//verificar
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
	    	case "polvorin":
	    		((Base) vObjeto).SetearVidaZonaPolvorin(((Base) vObjeto).ObtenerVidaZonaPolvorin()-1);
	    		break;
	    	case "despuegue":
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
	
	public JsonObject DisparaDronAereo(int  vIdObjeto){
		
		JsonObject vRespuesta = new  JsonObject();
		vRespuesta.addProperty("tipo","disparoTerrestre");
		Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
		
		vRespuesta.addProperty("IdDronAereo",vObjeto.ObtenerIdObjeto());
		String vGolpe;
			vGolpe=new Conversor().DronAereo((int)(Math.random()*(6))+2);
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
	    		vRespuesta.addProperty("destruida",true);
	    		break;
	     default:
	  }
			return vRespuesta;
		
	}
	
	public JsonElement DisparaDronTerrestre(int  vIdObjeto){
	
	JsonElement vRespuesta = null;
	Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
	String vGolpe;
	vGolpe=new Conversor().DronTerrestre((int)(Math.random()*(6))+2);
		switch (vGolpe) {
		case "Velocidad":
    		if(((DronTerrestre) vObjeto).ObtenerVelocidad()>0)
    			((DronTerrestre) vObjeto).SetearVelocidad(((DronTerrestre) vObjeto).ObtenerVelocidad()-1);
    			vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerColeccionDrones().find(vIdObjeto).SetearVelocidad(1);
    		break;
    	case "Camara":
    		((DronTerrestre) vObjeto).SetearCamara(false);
    		break;
    	case "Canon":
    		((DronTerrestre) vObjeto).SetearCanon(false);
    		break;
    	case "Vision":
    		if(((DronTerrestre) vObjeto).ObtenerVision()>0)
    			((DronTerrestre) vObjeto).SetearVision(((DronTerrestre) vObjeto).ObtenerVision()-1);
    		break;
    	case "Blindaje":
    		if(((DronTerrestre) vObjeto).ObtenerBlindaje()>0)
    			((DronTerrestre) vObjeto).SetearBlindaje(((DronTerrestre) vObjeto).ObtenerBlindaje()-1);
    		break;
     default:
  }
		vRespuesta=new JsonParser().parse("Msg:Pego" + vGolpe);
		return vRespuesta;
	
}

	public JsonElement TirarBomba(int vIdObjeto){
	
	JsonElement vRespuesta = null;
	
	Objeto vObjeto=vPartida.getObjetos().Find(vIdObjeto);
	((DronAereo) vObjeto).SetearTieneBomba(false);
	
	vRespuesta=new JsonParser().parse("Msg:Tiro bomba");
	return vRespuesta;
	
}
}
