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
import Logica.Objeto;
import Persistencia.Consultas;




/*********************************************
 * Descripción: 	Clase que devuelve datos de Jugadores en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOJugador {
	
	private String Url, User, Password;
	
	public DAOJugador(String url, String user, String password){
		this.Url = url;
		this.User = user;
		this.Password = password;
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
			pstmt.setString(4, NombreJugador);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
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
		Drones vDrones=new Drones();
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
			if(IdEquipo==1){
				pstmt.setInt(4, 2);
				pstmt.setInt(5, 3);
			}else{
				pstmt.setInt(4, 4);
				pstmt.setInt(5, 5);
			}
			ResultSet rs = pstmt.executeQuery();
			String nombre=null;
			

				while(rs.next())
				{
					if(rs.getString("tipo").equals("Aereo"))
						vDron=(Dron)new DronAereo(rs.getInt("idObjeto"),rs.getInt("coordX"),rs.getInt("coordY"),rs.getInt("altura"),rs.getInt("ancho"),rs.getInt("rotacion"),rs.getInt("angulo"),rs.getString("tipo"),rs.getInt("velocidad"),rs.getBoolean("camara"),rs.getBoolean("canion"),rs.getInt("vision"),rs.getInt("motoresActivos"),rs.getBoolean("tieneBomba"),rs.getBoolean("bombaRota"));
					else
						vDron=(Dron)new DronTerrestre(rs.getInt("IdObjeto"),rs.getInt("coordX"),rs.getInt("coordY"),rs.getInt("altura"),rs.getInt("ancho"),rs.getInt("rotacion"),rs.getInt("angulo"),rs.getString("tipo"),rs.getInt("velocidad"), rs.getBoolean("camara"),rs.getBoolean("canion"),rs.getInt("vision"), rs.getInt("blindajeActivo"));
					
					 vDrones.insert(rs.getInt("idObjeto"),vDron);
					 nombre = rs.getString("nombre");
				}
				
			vJugador=new Jugador(IdJugador,nombre,vDrones);
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vJugador;
	}
	
	public Jugadores DevolverJugadoresEquipo(int IdEquipo,int IdPartida){
		
		Jugadores vListaJugadores= new Jugadores();
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
			
			
				while(rs.next())
				{
					vJugador=DevolverJugador(rs.getInt("IdJugador"), IdEquipo, IdPartida);
					vListaJugadores.insert(vJugador);
				}
				
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {
			System.out.println("Error de SQL");
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
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
		
	}
	
	public int DevolverIdDron(String vNombreJugador){
		
		int vIdDron=0;
		int vIdJugador=0;
		int vIdEquipo=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String DevolverIdDronJugador= consultas.NombreJugador();
			PreparedStatement pstmt = con.prepareStatement(DevolverIdDronJugador);
		    	
			pstmt.setString(1, vNombreJugador);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()){
				vIdJugador=rs.getInt("IdJugador");
				vIdEquipo=rs.getInt("IdEquipo");
			}
			pstmt.close();
			DevolverIdDronJugador= consultas.IdDronJugador();
			pstmt = con.prepareStatement(DevolverIdDronJugador);
			pstmt.setInt(1, vIdJugador);
			if(vIdEquipo==1){
				pstmt.setInt(2,2);
				pstmt.setInt(3, 3);
			}else{
				pstmt.setInt(2,4);
				pstmt.setInt(3, 5);
				
			}
			rs = pstmt.executeQuery();

			if(rs.next())
				vIdDron=rs.getInt("IdObjeto");
			
			con.close();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
		
		return vIdDron;
	}
}
