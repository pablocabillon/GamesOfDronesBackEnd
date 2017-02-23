package Persistencia;


/*********************************************
 * Descripción: 	Clase que contiene todas las consultas a la base de datos
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Consultas {
	
	
	public String InsertarJugador(){
		return "INSERT INTO JUGADORES (IdJugador,NombreJugador,IdDron) VALUES(?,?,?)";
	}

	public String ExisteJugador(){
		return "SELECT * FROM JUGADORES WHERE IdJugador= ?";
	}
	
	public String EliminarJugador(){
		return "DELETE FROM JUGADORES WHERE IdJugador= ?";
	}

	public String InsertarPartida(){
		
		return "INSERT INTO PARTIDAS (IdPartida,IdEscenario,IdEquipo1,IdEquipo2) VALUES(?,?,?,?)";
	   
	}
	
	public String ExistePartida(){
		
			return "SELECT * FROM PARTIDAS WHERE IdPartida=?";
		
	}
	
	public String EliminarPartida(){
		
		return "DELETE FROM PARTIDAS WHERE IdPartida=?";
		
	}
	
	public String InsertarEquipo(){
		return "INSERT INTO EQUIPOS (IdEquipo,NombreEquipo,IdJugador1,IdJugador2) VALUES(?,?,?,?)";
	}

	public String ExisteEquipo(){
		return "SELECT * FROM EQUIPOS WHERE IdEquipo= ?";
	}
	
	public String EliminarEquipo(){
		return "DELETE FROM EQUIPOS WHERE IdEquipo= ?";
	}
	
	public String InsertarObjeto(){
		return "INSERT INTO OBJETOS (IdObjeto,CoordX,CoordY,Altura,Ancho) VALUES(?,?,?,?,?,?)";
	}

	public String ExisteObjeto(){
		return "SELECT * FROM OBJETOS WHERE IdObjeto= ?";
	}
	
	public String EliminarObjeto(){
		return "DELETE FROM OBJETOS WHERE IdObjeto= ?";
	}
	
	public String InsertarDron(){
		return "INSERT INTO DRONES (IdDron,IdObjeto,Camara,Cañon,Vision,Velocidad) VALUES(?,?,?,?,?,?)";
	}

	public String ExisteDron(){
		return "SELECT * FROM DRONES WHERE IdDron= ?";
	}
	
	public String EliminarDron(){
		return "DELETE FROM DRONES WHERE IdDron= ?";
	}
	
	public String InsertarBase(){
		return "INSERT INTO BASES (IdObjeto,VidaPolvorin,VidaZonaDespegue) VALUES(?,?,?)";
	}
	
	public String ExisteBase(){
		return "SELECT * FROM BASES WHERE IdObjeto= ?";
	}
	
	public String EliminarBase(){
		return "DELETE FROM BASES WHERE IdObjeto= ?";
	}
	
	public String InsertarTerrestre(){
		return "INSERT INTO TERRESTRES (IdDron,BlindajeActivo) VALUES(?,?)";
	}
	
	public String ExisteTerrestre(){
		return "SELECT * FROM TERRESTRES WHERE IdDron= ?";
	}
	
	public String EliminarTerrestre(){
		return "DELETE FROM TERRESTRES WHERE IdDron= ?";
	}
	
	public String InsertarAereos(){
		return "INSERT INTO AEREOS (IdDron,MotorActivo,TieneBomba) VALUES(?,?,?)";
	}
	
	public String ExisteAereo(){
		return "SELECT * FROM AEREOS WHERE IdDron= ?";
	}
	
	public String EliminarAereo(){
		return "DELETE FROM AEREOS WHERE IdDron= ?";
	}
}
