package Persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Logica.Equipos;
import Logica.Escenario;
import Logica.Jugadores;
import Logica.Objetos;
import Logica.Partida;

/*********************************************
 * Descripción: 	Clase que devuelve datos de las Partidas en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DAOPartida {
	
private String Url, User, Password;
	
	
public void DAOPartida(){
		this.Url = "jdbc:mysql://localhost:3306/ultimabatalla";
		this.User = "root";
		this.Password = "pepito";
	}

public void InsertarPartida(int vIdPartida,Date vFecha,int vCantJugadores,int vEscenario){
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String insertarPartida = consultas.InsertarPartida();
		PreparedStatement pstmt = con.prepareStatement(insertarPartida);
		pstmt.setInt(1, vIdPartida);
		pstmt.setDate(2, vFecha);
		pstmt.setInt(3, vCantJugadores);
		pstmt.setInt(4, vEscenario);
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException e) {

	}
}

public boolean ExistePartida(int vIdPartida){
	boolean existe = false;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		
		String existePartida = consultas.ExistePartida();
		PreparedStatement pstmt = con.prepareStatement(existePartida);	
		pstmt.setInt(1, vIdPartida);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			existe = true;
			
		rs.close();
		pstmt.close();
		con.close();
		
	} catch (SQLException e) {

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return existe;
}

public void EliminarPartida(int vIdPartida){
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String EliminarPartida= consultas.EliminarPartida();
		PreparedStatement pstmt = con.prepareStatement(EliminarPartida);
		pstmt.setInt(1, vIdPartida);
		pstmt.executeQuery();
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException e) {

	}
	
}

public Partida DevolverPartida(int vIdPartida){
	
	Partida vPartida=null;
	Equipos vEquipos=null;
	Jugadores vJugadores=null;
	Objetos vObjetos=null;
	

	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		
		String BuscarPartida = consultas.ExistePartida();
		PreparedStatement pstmt = con.prepareStatement(BuscarPartida);	
		pstmt.setInt(1, vIdPartida);
		ResultSet rs = pstmt.executeQuery();
		
		
		if(rs.next())
		{
			vEquipos=new DAOEquipo().DevolverEquiposPArtida(vIdPartida);
			vObjetos=new DAOObjeto().DevolverObjetosPartida(vIdPartida);
			vPartida=new Partida(vEquipos, rs.getInt("IdEscenario"), vObjetos, rs.getInt("CantJugadores"));
			
		}	
		rs.close();
		pstmt.close();
		con.close();
		
		
	} catch (SQLException e) {
			
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

	return vPartida;
}

}
