package Persistencia;


/*********************************************
 * Descripci�n: 	Clase que contiene todas las consultas a la base de datos
 * Autor: 			Mart�n Torres
 * Versi�n: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class Consultas {
	
	
	public String InsertarJugador(){
		return "INSERT INTO JUGADOR (idJugador,idEquipo,IdPartida,nombre) VALUES(?,?,?,?)";
	}

	public String ExisteJugador(){
		return "SELECT * FROM JUGADOR WHERE IdJugador= ? AND IdEquipo=? AND IdPartida=?";
	}
	
	public String EliminarJugador(){
		return "DELETE FROM JUGADOR WHERE idJugador= ? AND idEquipo=? AND idPartida=?";
	}
	
	public String BuscarJugador(){
		return "SELECT JU.nombre,DR.camara,DR.canion,DR.vision,DR.velocidad,AE.motoresActivos,AE.tieneBomba,AE.bombaRota,TE.blindajeActivo,OB.idObjeto,OB.coordX,OB.coordY,"
				+"OB.altura,OB.rotacion,OB.angulo,OB.tipo,OB.ancho "
				+"FROM JUGADOR AS JU INNER JOIN DRON AS DR ON JU.IdJugador=DR.IdJugador " 
				+"AND JU.IdPartida=DR.IdPartida INNER JOIN OBJETO AS OB ON OB.IdObjeto=DR.IdObjeto " 
				+"AND OB.IdObjeto=DR.IdObjeto LEFT OUTER JOIN TERRESTRE AS TE ON TE.IdObjeto=DR.IdObjeto " 
				+"AND TE.IdJugador=DR.IdJugador LEFT OUTER JOIN AEREO AS AE ON AE.IdObjeto=DR.IdObjeto AND AE.IdJugador=DR.IdJugador "
				+"WHERE JU.idJugador=? AND JU.idEquipo=? AND JU.idPartida=? AND DR.idObjeto IN (?,?)";
	}
	
	public String NombreJugador(){
		
		return "SELECT  * FROM JUGADOR WHERE rtrim(ltrim(nombre))=rtrim(ltrim(?))" ;
	}
	
	public String IdDronJugador(){
		
		return "SELECT MIN(idObjeto) AS IdObjeto FROM DRON WHERE idJugador=? AND idObjeto IN(?,?)" ;
	}
	
	public String InsertarPartida(){
		
		return "INSERT INTO PARTIDA (idPartida,fechaHora,CantJugadores,idEscenario) VALUES(?,?,?,?)";
	   
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
		return "INSERT INTO EQUIPO (idEquipo,idPartida,nombre) VALUES(?,?,?)";
	}

	public String ExisteEquipo(){
		return "SELECT * FROM EQUIPO WHERE idEquipo= ? AND idPartida=?";
	}
	
	public String EliminarEquipo(){
		return "DELETE FROM EQUIPO WHERE idEquipo= ? AND idPartida=?";
	}
	
	public String BuscarEquiposPartida(){
		return "SELECT * FROM EQUIPO WHERE IdPartida= ?";
	}
	public String InsertarObjeto(){
		return "INSERT INTO OBJETO (IdObjeto,IdPartida,CoordX,CoordY,Rotacion,Angulo,Altura,Ancho,tipo) VALUES(?,?,?,?,?,?,?,?,?)";
	}

	public String ExisteObjeto(){
		return "SELECT * FROM OBJETO WHERE IdObjeto= ? AND IdPartida= ?";
	}
	
	public String BuscarObjetosPartida(){
		return "SELECT * FROM OBJETO WHERE idPartida= ?";
	}
	
	public String EliminarObjeto(){
		return "DELETE FROM OBJETO WHERE idObjeto= ? AND idPartida= ?";
	}
	
	public String InsertarDron(){
		return "INSERT INTO DRON (idObjeto,idPartida,idJugador,canion,vision,camara,velocidad) VALUES(?,?,?,?,?,?,?)";
	}

	public String ExisteDron(){
		return "SELECT * FROM DRON WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String EliminarDron(){
		return "DELETE FROM DRON WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String InsertarBase(){
		return "INSERT INTO BASE (IdObjeto,IdPartida,VidaPolvorin,VidaZonaDespegue) VALUES(?,?,?,?)";
	}
	
	public String ExisteBase(){
		return "SELECT * FROM BASE WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String EliminarBase(){
		return "DELETE FROM BASE WHERE idObjeto= ? AND idPartida=?";
	}
	
	public String InsertarTerrestre(){
		return "INSERT INTO TERRESTRE (IdObjeto,IdPartida,IdJugador,BlindajeActivo) VALUES(?,?,?,?)";
	}
	
	public String ExisteTerrestre(){
		return "SELECT * FROM TERRESTRE WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String EliminarTerrestre(){
		return "DELETE FROM TERRESTRE WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String InsertarAereos(){
		return "INSERT INTO AEREO (idObjeto,idPartida,idJugador,tieneBomba,motoresActivos,bombaRota) VALUES(?,?,?,?,?,?)";
	}
	
	public String ExisteAereo(){
		return "SELECT * FROM AEREO WHERE IdObjeto= ? AND IdPartida=? AND IdJugador=?";
	}
	
	public String DevolverDronAereo(){
		return "SELECT OB.idObjeto,OB.coordX,OB.coordY,OB.altura,OB.ancho,OB.rotacion,OB.angulo,OB.TIPO,DR.velocidad,DR.camara,DR.canion,DR.vision,AE.motoresActivos,"
				+ "AE.tieneBomba,AE.bombaRota FROM OBJETO AS OB INNER JOIN AEREO AS AE ON AE.idObjeto=OB.idObjeto INNER JOIN DRON AS DR ON DR.idObjeto=OB.idObjeto "
				+ " WHERE OB.IdObjeto=?";
	}
	
	public String DevolverDronTerrestre(){
		return "SELECT OB.idObjeto,OB.coordX,OB.coordY,OB.altura,OB.ancho,OB.rotacion,OB.angulo,OB.TIPO,DR.velocidad,DR.camara,DR.canion,DR.vision,TR.blindajeActivo"
				+ " FROM OBJETO AS OB INNER JOIN TERRESTRE AS TR ON TR.idObjeto=OB.idObjeto INNER JOIN DRON AS DR ON DR.idObjeto=OB.idObjeto "
				+ " WHERE OB.IdObjeto=?";
	}
	
	public String DevolverBase(){
		return "SELECT OB.idObjeto,OB.coordX,OB.coordY,OB.altura,OB.ancho,OB.rotacion,OB.angulo,OB.TIPO,BA.vidaPolvorin,BA.vidaZonaDespegue "
				+ " FROM OBJETO AS OB INNER JOIN BASE AS BA ON BA.idObjeto=OB.idObjeto  WHERE OB.IdObjeto=?";
	}
	public String EliminarAereo(){
		return "DELETE FROM AEREO WHERE IdObjeto= ? AND IdPartida=?";
	}
	
	public String buscarObjeto(){
		return "SELECT * FROM OBJETO WHERE IdObjeto=? and IdPartida=?";
	}
}
