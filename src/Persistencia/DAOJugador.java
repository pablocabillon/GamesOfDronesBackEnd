package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Persistencia.Consultas;

//import server.Reporter;

/*********************************************
 * Descripción: 	Clase que devuelve datos de Jugadores en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOJugador {
	
	private String Url, User, Password;
	
	public void DAOJugadores(){
		this.Url = "jdbc:mysql://localhost:3306/ultimabatalla";
		this.User = "root";
		this.Password = "pepito";
	}
	
	public void InsertarJugador(String nombre,int IdDron){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarJugador = consultas.InsertarJugador();
			PreparedStatement pstmt = con.prepareStatement(insertarJugador);
			pstmt.setInt(1, 1);
			pstmt.setString(1, nombre);
			pstmt.setInt(1, IdDron);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {

		}
	}
	
	public boolean ExisteJugador(int IdJugador){
		boolean existe = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String existeJugador = consultas.ExisteJugador();
			PreparedStatement pstmt = con.prepareStatement(existeJugador);	
			pstmt.setInt(1, IdJugador);
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
}
