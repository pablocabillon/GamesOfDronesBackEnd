package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Logica.Base;
import Logica.Objeto;

/*********************************************
 * Descripción: 	Clase que devuelve datos de la Base desde la Base de Datos
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOBase {
	
private String Url, User, Password;
	
	public DAOBase(String url, String user, String password){
		this.Url = url;
		this.User = user;
		this.Password = password;
	}
	
	
	public void InsertarBase(Base vBase,int vIdPartida){ 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarBase = consultas.InsertarBase();
			PreparedStatement pstmt = con.prepareStatement(insertarBase);
			pstmt.setInt(1,vBase.ObtenerIdObjeto());
			pstmt.setInt(2, vIdPartida);
			pstmt.setInt(3, vBase.ObtenerVidaZonaPolvorin());
			pstmt.setInt(4, vBase.ObtenerVidaZonaDespegue());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
	}
	
	public boolean ExisteBase(int vIdObjeto,int vIdPartida){
		boolean existe = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String existeBase = consultas.ExisteBase();
			PreparedStatement pstmt = con.prepareStatement(existeBase);	
			pstmt.setInt(1, vIdObjeto);
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
			e.printStackTrace();
		}
		return existe;
	}
	
	public void EliminarBase(int vIdObjeto,int vIdPartida){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String EliminarBase= consultas.EliminarBase();
			PreparedStatement pstmt = con.prepareStatement(EliminarBase);
		    	
			pstmt.setInt(1, vIdObjeto);
			pstmt.setInt(2, vIdPartida);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
		}
		
	}
	
	public Base DevolverBase(int vIdObjeto,int vIdPartida){
		
		Base vBase=null;
		Objeto vObjeto=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			
			String DevolverBase = consultas.ExisteBase();
			PreparedStatement pstmt = con.prepareStatement(DevolverBase);	
			pstmt.setInt(1, vIdObjeto);
			pstmt.setInt(2, vIdPartida);
			ResultSet rs = pstmt.executeQuery();
			
			
			if(rs.next())
			{
				vObjeto=new DAOObjeto(Url,User,Password).DevolverObjeto(vIdObjeto, vIdPartida);
				vBase=new Base(vObjeto.ObtenerIdObjeto(), vObjeto.ObtenerCoordenadaX(),vObjeto.ObtenerCoordenadaY(), vObjeto.ObtenerAltura(),vObjeto.ObtenerAncho(), vObjeto.ObtenerRotacion(),
								vObjeto.ObtenerAngulo(),vObjeto.ObtenerTipo(),rs.getInt("vidaPolvorin"),rs.getInt("vidaZonaDespegue"));
				
			}	
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {
			System.out.println("Error de SQL");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return vBase;
	}


}
