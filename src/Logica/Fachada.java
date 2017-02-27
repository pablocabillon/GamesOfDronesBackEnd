package Logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import Logica.Escenario.Escenarios;
import Persistencia.AccesoBase;
import Persistencia.DAOBase;
import Persistencia.DAODron;
import Persistencia.DAOEquipo;
import Persistencia.DAOJugador;
import Persistencia.DAOObjeto;
import Persistencia.DAOPartida;
import sun.security.jca.GetInstance.Instance;


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
	
	
	public JsonElement CrearPartida(int vCantidadJugadores) throws IOException{
		JsonElement vRespuesta = null;
		
		if(vIdPArtidaActual>0){
			vRespuesta=new JsonParser().parse("Msg: Ya hay una partida creada.");
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
		vRespuesta = new JsonParser().parse("Msg: Partida creada correctamente");		          
		}
		
		return vRespuesta;
	}
	
	public JsonElement UnirsePartidaAereo(String vNombreJugador,int vIdDron,int vIdDron1){
		
		JsonElement vRespuesta = null;
		
		if(vCantJugadores==0)
			vRespuesta=new JsonParser().parse("Msg: No se puede ingresar a la Partida.");
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
			if(vCantJugadores==0)
				vRespuesta=new JsonParser().parse("Msg: Iniciar Partida.");
			else
				vRespuesta=new JsonParser().parse("Msg: Esperar.");
						
			}
		
		return vRespuesta;
	}

	public JsonElement UnirsePartidaTerrestre(String vNombreJugador,int vIdDron,int vIdDron1){
		
		JsonElement vRespuesta = null;
		
		if(vCantJugadores==0)
			vRespuesta=new JsonParser().parse("Msg: No se puede ingresar a la Partida.");
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
			if(vCantJugadores==0)
				vRespuesta=new JsonParser().parse("Msg: Iniciar Partida.");
			else
				vRespuesta=new JsonParser().parse("Msg: Esperar.");
						
			}
		
		return vRespuesta;
	}
	
	public void GuardarPartida() throws IOException,SQLException
	{
		long time = System.currentTimeMillis();
		java.sql.Date fecha_guardar = new java.sql.Date(time);
		new DAOPartida().InsertarPartida(vIdPArtidaActual, fecha_guardar, vCantJugadores, vPartida.getEscenario());
		new DAOEquipo().InsertarEquipo(vPartida.getEquipos().Find(1).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerNombre());
		new DAOEquipo().InsertarEquipo(vPartida.getEquipos().Find(2).ObtenerIdEquipo(), vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerNombre());

		int vCantJugadoresEquipo1=vPartida.getEquipos().Find(1).ObtenerJugadores().CantJugadores();
		int vCantJugadoresEquipo2=vPartida.getEquipos().Find(2).ObtenerJugadores().CantJugadores();
			new DAOJugador().InsertarJugador(1,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(1).ObtenerNombre());
		if(vCantJugadoresEquipo1==2){
			new DAOJugador().InsertarJugador(2,1, vIdPArtidaActual, vPartida.getEquipos().Find(1).ObtenerJugadores().Find(2).ObtenerNombre());
		}
			new DAOJugador().InsertarJugador(1,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(1).ObtenerNombre());
		if(vCantJugadoresEquipo2==1){
			new DAOJugador().InsertarJugador(2,2, vIdPArtidaActual, vPartida.getEquipos().Find(2).ObtenerJugadores().Find(2).ObtenerNombre());
		}
		
		InsertarBase();
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
	new DAOBase().InsertarBase(vBaseInsert, 1, vIdPArtidaActual);
		
	}
	
	public Partida ReanudarPartida(int vIdPartida){
		
		return vPartida = new DAOPartida().DevolverPartida(vIdPartida);
		 
	}
	
	public void DisparaAereo(int  vIdObjeto){
		
		
	}
	
}
