package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Logica.Dron;
import Logica.DronAereo;
import Logica.DronTerrestre;
import Logica.Drones;
import Logica.Jugador;
import Logica.Jugadores;
import Persistencia.Consultas;




/*********************************************
 * Descripción: 	Clase que devuelve datos de Jugadores en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOJugador {
	
	private String Url, User, Password;
	
	public void DAOJugador(){
		this.Url = "jdbc:mysql://localhost:3306/ultimabatalla";
		this.User = "root";
		this.Password = "pepito";
	}
	
	public void InsertarJugador(int IdJugador,int IdEquipo,int IdPartida,String NombreJugador){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarJugador = consultas.InsertarJugador();
			PreparedStatement pstmt = con.prepareStatement(insertarJugador);
			pstmt.setInt(1, IdJugador);
			pstmt.setInt(2, IdEquipo);
			pstmt.setInt(3, IdPartida);
			pstmt.setString(1, NombreJugador);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {

		}
	}
	
	public boolean ExisteJugador(int IdJugador,int IdEquipo,int IdPartida){
		boolean existe = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String existeJugador = consultas.ExisteJugador();
			PreparedStatement pstmt = con.prepareStatement(existeJugador);	
			pstmt.setInt(1, IdJugador);
			pstmt.setInt(2, IdEquipo);
			pstmt.setInt(3, IdPartida);
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
	
	public Jugador DevolverJugador(int IdJugador,int IdEquipo,int IdPartida){
		
		Jugador vJugador=null;
		Drones vDrones=null;
		Dron vDron=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String DevolverJugador = consultas.BuscarJugador();
			PreparedStatement pstmt = con.prepareStatement(DevolverJugador);	
			pstmt.setInt(1, IdJugador);
			pstmt.setInt(2, IdEquipo);
			pstmt.setInt(3, IdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			
			if(rs.next())
			{
				while(rs.next())
				{
					if(rs.getString("TieneBomba")!="")
						vDron=new DronAereo(1,rs.getBoolean("Camara"),rs.getBoolean("Cañon"),rs.getInt("Vision"),rs.getInt("MotoresActivos"),rs.getBoolean("TieneBomba"),rs.getBoolean("BombaRota"));
					else
						vDron=new DronTerrestre(1, rs.getBoolean("Camara"),rs.getBoolean("Cañon"),rs.getInt("Vision"), rs.getInt("BlindajeActivo"));
					
					 vDrones.insert(rs.getInt("IdObjeto"),vDron);
				}
				
				vJugador=new Jugador(IdJugador,rs.getString("NombreJugador"),vDrones);
				
			}	
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vJugador;
	}
	
	public Jugadores DevolverJuadoresEquipo(int IdEquipo,int IdPartida){
		
		Jugadores vListaJugadores=null;
		Jugador vJugador=null;


		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String DevolverJugadoresEquipo = consultas.BuscarJugadorEquipo();
			PreparedStatement pstmt = con.prepareStatement(DevolverJugadoresEquipo);	
			pstmt.setInt(1, IdEquipo);
			pstmt.setInt(2, IdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			
			if(rs.next())
			{
				while(rs.next())
				{
					vJugador=DevolverJugador(rs.getInt("IdJugador"), IdEquipo, IdPartida);
					vListaJugadores.insert(vJugador);
				}
				
				
			}	
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vListaJugadores;
	}
	public void EliminarJugador(int vIdJugador,int vIdEquipo,int vIdPartida){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String EliminarJugador= consultas.EliminarJugador();
			PreparedStatement pstmt = con.prepareStatement(EliminarJugador);
		    	
			pstmt.setInt(1, vIdJugador);
			pstmt.setInt(2, vIdEquipo);
			pstmt.setInt(3, vIdPartida);
			pstmt.executeQuery();
			
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {

		}
		
	}
	
	
}
