package Persistencia;


/*********************************************
 * Descripción: 	Clase que contiene todas las consultas a la base de datos
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Consultas {
	
	
	public String InsertarJugador(){
		return "INSERT INTO JUGADOR (IdJugador,IdEquipo,IdPartida,NombreJugador) VALUES(?,?,?)";
	}

	public String ExisteJugador(){
		return "SELECT * FROM JUGADOR WHERE IdJugador= ? AND IdEquipo=? AND IdPartida=?";
	}
	
	public String EliminarJugador(){
		return "DELETE FROM JUGADOR WHERE IdJugador= ? AND IdEquipo=? AND IdPartida=?";
	}
	
	public String BuscarJugador(){
		return "SELECT JU.NombreJugador,DR.Camara,DR.Cañon,DR.Vision,AE.MotoresActivos,AE.TieneBomba,TE.BlindajeActivo FROM JUGADOR AS JU INNER JOIN DRON AS DR ON JU.IdJugador=DR.IdJugador"
				+ " AND JU.IdPartida=DR.IdPartida INNER JOIN OBJETO AS OB ON OB.IdObjeto=DR.IdObjeto AND OB.IdObjeto=DR.IdObjeto INNER JOIN TERRESTRE AS TE"
				+ "LEFT OUTER JOIN TERRESTRE AS TE ON TE.IdObjeto=DR.IdObjeto AND TE.IdJugador=DR.IdJugador LEFT OUTER JOIN"
				+ "AEREO AS AE ON AE.IdObjeto=DR.IdObjeto AND AE.IdJugador=DR.IdJugador"
				+ "WHERE JU.IdJugador= ? AND JU.IdEquipo=? AND JU.IdPartida=?";
	}

	public String InsertarPartida(){
		
		return "INSERT INTO PARTIDA (IdPartida,FechaHora,CantJugadores) VALUES(?,?,?)";
	   
	}
	
	public String BuscarJugadorEquipo(){
		return "SELECT * FROM JUGADOR WHERE IdEquipo= ? AND IdPartida= ?";
	}
	
	public String ExistePartida(){
		
			return "SELECT * FROM PARTIDA WHERE IdPartida=?";
		
	}
	
	public String EliminarPartida(){
		
		return "DELETE FROM PARTIDA WHERE IdPartida=?";
		
	}
	
	public String InsertarEquipo(){
		return "INSERT INTO EQUIPO (IdEquipo,IdPartida,NombreEquipo) VALUES(?,?,?)";
	}

	public String ExisteEquipo(){
		return "SELECT * FROM EQUIPO WHERE IdEquipo= ? AND IdPartida=?";
	}
	
	public String EliminarEquipo(){
		return "DELETE FROM EQUIPOS WHERE IdEquipo= ? AND IdPartida=?";
	}
	
	public String BuscarEquiposPartida(){
		return "SELECT * FROM EQUIPO WHERE IdPartida= ?";
	}
	public String InsertarObjeto(){
		return "INSERT INTO OBJETO (IdObjeto,IdPartida,CoordX,CoordY,Rotacion,Angulo,Altura,Ancho) VALUES(?,?,?,?,?,?,?,?)";
	}

	public String ExisteObjeto(){
		return "SELECT * FROM OBJETO WHERE IdObjeto= ? AND IdPartida= ?";
	}
	
	public String BuscarObjetosPartida(){
		return "SELECT * FROM OBJETO WHERE IdPartida= ?";
	}
	
	public String EliminarObjeto(){
		return "DELETE FROM OBJETO WHERE IdObjeto= ? AND IdPartida= ?";
	}
	
	public String InsertarDron(){
		return "INSERT INTO DRON (IdObjeto,IdPartida,IdJugador,Camara,Cañon,Vision,Velocidad) VALUES(?,?,?,?,?,?,?)";
	}

	public String ExisteDron(){
		return "SELECT * FROM DRON WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String EliminarDron(){
		return "DELETE FROM DRONES WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String InsertarBase(){
		return "INSERT INTO BASE (IdObjeto,IdPartida,VidaPolvorin,VidaZonaDespegue) VALUES(?,?,?,?)";
	}
	
	public String ExisteBase(){
		return "SELECT * FROM BASE WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String EliminarBase(){
		return "DELETE FROM BASE WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String InsertarTerrestre(){
		return "INSERT INTO TERRESTRE (IdObjeto,IdPartida,IdJugador,BlindajeActivo) VALUES(?,?,?,?)";
	}
	
	public String ExisteTerrestre(){
		return "SELECT * FROM TERRESTRE WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String EliminarTerrestre(){
		return "DELETE FROM TERRESTRE WHERE WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String InsertarAereos(){
		return "INSERT INTO AEREO (IdObjeto,IdPartida,IdJugador,MotorActivo,TieneBomba) VALUES(?,?,?,?,?)";
	}
	
	public String ExisteAereo(){
		return "SELECT * FROM AEREO WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String EliminarAereo(){
		return "DELETE FROM AEREO WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
}
