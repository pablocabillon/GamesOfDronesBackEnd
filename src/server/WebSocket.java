package server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Logica.Fachada;



@ServerEndpoint("/websocket")

public class WebSocket {
	
	static final Logger LOGGER = Logger.getLogger(WebSocket.class.getName());
	
	private static Set<Session> conexiones = Collections.synchronizedSet(new HashSet<Session>());
	
	private Fachada vFachada=new Fachada(); 
	
	private int vCantidadJugadoresPartida=0;

	
	
	@OnMessage
	public void onMessage(String mensaje, Session sesion) throws IOException {
	
		JsonElement jelement = new JsonParser().parse(mensaje);
		String vTipo = jelement.getAsJsonObject().get("tipo").toString();
		JsonObject vRespuesta = null;
		switch(vTipo){
		case "\"MueveAereo\"":
		case "\"MueveTerrestre\"":
			 vRespuesta = jelement.getAsJsonObject();
			 synchronized(conexiones){
		      for(Session client : conexiones){
		        if (client.equals(sesion)){
		            client.getBasicRemote().sendText(vRespuesta.toString());
		        }
		      }
			 }
		
		break;
		case "\"crearPartida\"":
			    int vCantJugadores=jelement.getAsJsonObject().get("jugadores").getAsInt();
			    vCantidadJugadoresPartida=vCantJugadores;
				vRespuesta=vFachada.CrearPartida(vCantJugadores);
				 synchronized(conexiones){
				for(Session client : conexiones){
			        if (client.equals(sesion)){
			            client.getBasicRemote().sendText(vRespuesta.toString());
			        }
			      }
				 }
			break;
		case "\"UnirPartida\"":
			String vEquipo=jelement.getAsJsonObject().get("equipo").toString();
				if(vEquipo.equals("\"aereo\"")){
							String vNombre=jelement.getAsJsonObject().get("nombreJugador").toString();
							vRespuesta=vFachada.UnirsePartidaAereo(vNombre);
				}
				else{
						String vNombre=jelement.getAsJsonObject().get("nombreJugador").toString();
						vRespuesta=vFachada.UnirsePartidaTerrestre(vNombre);

				}
				synchronized(conexiones){
					for(Session client : conexiones){
				        if (client.equals(sesion)){
				            client.getBasicRemote().sendText(vRespuesta.toString());
				        }
				      }
					}
				JsonElement vElement=new JsonParser().parse(vRespuesta.toString());
				String vRetono=vElement.getAsJsonObject().get("retorno").toString();
				if(vRetono.equals("\"iniciar\"")){
					JsonObject vRespuestaIniciar = new JsonObject();
					vRespuestaIniciar.addProperty("tipo", "IniciarPartida");
					synchronized(conexiones){
					for(Session client : conexiones){
				            client.getBasicRemote().sendText(vRespuestaIniciar.toString());
				      }
					}
				}
			break;
			
		case "\"guardarPartida\"":
			
			break;
			
		case "\"disparoBase\"":
				vRespuesta=vFachada.DisparaBase(jelement.getAsJsonObject().get("IdBase").getAsInt(), jelement.getAsJsonObject().get("sector").toString());
				synchronized(conexiones){
				for(Session client : conexiones){
			            client.getBasicRemote().sendText(vRespuesta.toString());
			        }
			   }
				break;
			
		case "\"disparoAereo\"":
				vRespuesta=vFachada.GolpeDronTerrestre(jelement.getAsJsonObject().get("IdDronAereo").getAsInt(),jelement.getAsJsonObject().get("IdDronTerrestre").getAsInt(),jelement.getAsJsonObject().get("tipoDisparo").toString());
					
				synchronized(conexiones){
					for(Session client : conexiones){
				            client.getBasicRemote().sendText(vRespuesta.toString());
				        }
				   }
			break;
			
		case "\"disparoTerrestre\"":
				vRespuesta=vFachada.GolpeDronAereo(jelement.getAsJsonObject().get("IdDronAereo").getAsInt());
				synchronized(conexiones){
					for(Session client : conexiones){
				            client.getBasicRemote().sendText(vRespuesta.toString());
				        }
				   }
			break;
			
		case "\"TiraBomba\"":
			vRespuesta=vFachada.TirarBomba(jelement.getAsJsonObject().get("IdDronAereo").getAsInt());
			synchronized(conexiones){
				for(Session client : conexiones){
			            client.getBasicRemote().sendText(vRespuesta.toString());
			        }
			   }
			break;
			
		default:
		}
	}
	 
	@OnOpen
	public void iniciaSesion(Session session) {
	    LOGGER.log(Level.INFO, "Iniciando la conexion de {0}", session.getId());
	    conexiones.add(session); //Simplemente, lo agregamos a la lista

	}

	@OnClose
	public void finConexion(Session session) {
	    LOGGER.info("Terminando la conexion");
	    if (conexiones.contains(session)) { // se averigua si está en la colección
	        try {
	            LOGGER.log(Level.INFO, "Terminando la conexion de {0}", session.getId());
	            session.close(); //se cierra la conexión
	            conexiones.remove(session); // se retira de la lista
	        } catch (IOException ex) {
	            LOGGER.log(Level.SEVERE, null, ex);
	        }
	    }
	    
	}

}
