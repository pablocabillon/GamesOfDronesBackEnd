package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Logica.Base;
import Logica.Dron;
import Logica.DronAereo;
import Logica.DronTerrestre;
import Logica.Objeto;

/*********************************************
 * Descripción: 	Clase que devuelve datos de los Drones en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAODron {
	
private String Url, User, Password;
	
	public DAODron(String url, String user, String password){
		this.Url = url;
		this.User = user;
		this.Password = password;
	}
	
public void InsertarDron(Dron vDron,int vIdPartida,int vIdJugador){ 
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarDron = consultas.InsertarDron();
			PreparedStatement pstmt = con.prepareStatement(insertarDron);
			pstmt.setInt(1,vDron.ObtenerIdObjeto());
			pstmt.setInt(2, vIdPartida);
			pstmt.setInt(3, vIdJugador);
			pstmt.setBoolean(4, vDron.ObtenerCanon());
			pstmt.setInt(5, vDron.ObtenerVision());
			pstmt.setBoolean(6, vDron.ObtenerCamara());
			pstmt.setInt(7, vDron.ObtenerVelocidad());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Hubo un error de SQL");
		}
	}

public void InsertarDronAereo(DronAereo vDronAereo,int vIdPartida,int vIdJugador){ 
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String insertarDronAereo = consultas.InsertarAereos();
		PreparedStatement pstmt = con.prepareStatement(insertarDronAereo);
		pstmt.setInt(1,vDronAereo.ObtenerIdObjeto());
		pstmt.setInt(2, vIdPartida);
		pstmt.setInt(3, vIdJugador);
		pstmt.setInt(4, vDronAereo.ObtenerMotorActivo());
		pstmt.setBoolean(5, vDronAereo.ObtenerTieneBomba());
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException e) {
		System.out.println("Error de SQL");
	}
}

public void InsertarDronTerrestre(DronTerrestre vDronTerrestre,int vIdPartida,int vIdJugador){ 
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String insertarDronTerrestre = consultas.InsertarTerrestre();
		PreparedStatement pstmt = con.prepareStatement(insertarDronTerrestre);
		pstmt.setInt(1, vDronTerrestre.ObtenerIdObjeto());
		pstmt.setInt(2, vIdPartida);
		pstmt.setInt(3, vIdJugador);
		pstmt.setInt(4, vDronTerrestre.ObtenerBlindaje());
		pstmt.executeUpdate();
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException e) {
		e.getMessage();
	}
}




}
