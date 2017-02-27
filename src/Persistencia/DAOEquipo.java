package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Logica.Equipo;
import Logica.Equipos;

import Logica.Jugadores;

/*********************************************
 * Descripción: 	Clase que devuelve datos de los Equipos en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class DAOEquipo {

	
	private String Url, User, Password;
	
	
	public DAOEquipo(String url, String user, String password){
			this.Url = url;
			this.User = user;
			this.Password = password;
		}
	
	
	public void InsertarEquipo(int vIdEquipo,int IdPartida,String vNombreEquipo){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarEquipo = consultas.InsertarEquipo();
			PreparedStatement pstmt = con.prepareStatement(insertarEquipo);
			pstmt.setInt(1, vIdEquipo);
			pstmt.setInt(2, IdPartida);
			pstmt.setString(3, vNombreEquipo);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
	}
	
	public boolean ExisteEquipo(int vIdEquipo,int vIdPartida){
		boolean existe = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String existeEquipo = consultas.ExisteEquipo();
			PreparedStatement pstmt = con.prepareStatement(existeEquipo);	
			pstmt.setInt(1, vIdEquipo);
			pstmt.setInt(2, vIdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
				existe = true;
				
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return existe;
	}
	
	public void EliminarEquipo(int vIdEquipo,int vIdPartida){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String EliminarEquipo= consultas.EliminarEquipo();
			PreparedStatement pstmt = con.prepareStatement(EliminarEquipo);
		    	
			pstmt.setInt(1, vIdEquipo);
			pstmt.setInt(2, vIdPartida);
			pstmt.executeUpdate();
			
			
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
		
	}
	
	public Equipo DevolverEquipo(int vIdEquipo,int vIdPartida){
		

		Jugadores vJugadores=null;
		Equipo vEquipo=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String DevolverEquipo = consultas.ExisteEquipo();
			PreparedStatement pstmt = con.prepareStatement(DevolverEquipo);	
			pstmt.setInt(1, vIdEquipo);
			pstmt.setInt(2, vIdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			
			if(rs.next())
			{
				vEquipo=new Equipo(rs.getInt("IdEquipo"),rs.getString("NombreEquipo"), vJugadores);
				vJugadores=new DAOJugador(Url,User,Password).DevolverJugadoresEquipo(vIdEquipo,vIdPartida);
				vEquipo.SetearJugadores(vJugadores);
			}	
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vEquipo;
	}
	
public Equipos DevolverEquiposPArtida(int vIdPartida){
		

		Equipo vEquipo=null;
		Equipos vEquipos=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String DevolverEquiposPartida = consultas.BuscarEquiposPartida();
			PreparedStatement pstmt = con.prepareStatement(DevolverEquiposPartida);	
			pstmt.setInt(1, vIdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
				vEquipo=DevolverEquipo(rs.getInt("IdEquipo"), vIdPartida);
				vEquipos.insert(rs.getInt("IdEquipo"), vEquipo);
				
			}	
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vEquipos;
	}
}
