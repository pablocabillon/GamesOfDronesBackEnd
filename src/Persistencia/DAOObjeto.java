package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Logica.Base;
import Logica.Objeto;
import Logica.Objetos;

/*********************************************
 * Descripción: 	Clase que devuelve datos de los Objetos en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOObjeto {
	
private String Url, User, Password;
	
	public void DAOObjeto(){
		this.Url = "jdbc:mysql://localhost:3306/ultimabatalla";
		this.User = "root";
		this.Password = "pepito";
	}
	
	
public void InsertarObjeto(Objeto vObjeto,int vIdPartida){ 
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(Url, User, Password);
			Consultas consultas = new Consultas();
			String insertarObjeto = consultas.InsertarObjeto();
			PreparedStatement pstmt = con.prepareStatement(insertarObjeto);
			pstmt.setInt(1, vObjeto.ObtenerIdObjeto());
			pstmt.setInt(2, vIdPartida);
			pstmt.setInt(3, vObjeto.ObtenerCoordenadaX());
			pstmt.setInt(4, vObjeto.ObtenerCoordenadaY());
			pstmt.setInt(5, vObjeto.ObtenerRotacion());
			pstmt.setInt(6, vObjeto.ObtenerAngulo());
			pstmt.setInt(7, vObjeto.ObtenerAltura());
			pstmt.setInt(8, vObjeto.ObtenerAncho());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {

		}
	}


public boolean ExisteObjeto(int vIdObjeto,int vIdPartida){
	boolean existe = false;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		
		String existeObjeto = consultas.ExisteObjeto();
		PreparedStatement pstmt = con.prepareStatement(existeObjeto);	
		pstmt.setInt(1, vIdObjeto);
		pstmt.setInt(2, vIdPartida);
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

public void EliminarObjeto(int vIdObjeto,int vIdPartida){
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String EliminarObjeto= consultas.EliminarObjeto();
		PreparedStatement pstmt = con.prepareStatement(EliminarObjeto);
	    	
		pstmt.setInt(1, vIdObjeto);
		pstmt.setInt(2, vIdPartida);
		pstmt.executeQuery();
		
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		e.getMessage();
	} catch (SQLException e) {

	}
	
}

public Objeto DevolverObjeto(int vIdObjeto,int vIdPartida){
	
	Objeto vObjeto=null;
	String vTipo="";

	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		
		String DevolverObjeto = consultas.BuscarJugador();
		PreparedStatement pstmt = con.prepareStatement(DevolverObjeto);	
		pstmt.setInt(1, vIdObjeto);
		pstmt.setInt(2, vIdPartida);
		ResultSet rs = pstmt.executeQuery();
		
		
		if(rs.next())
		{
			vObjeto=new Objeto(vIdObjeto, rs.getInt("CoordenadaX"),rs.getInt("CoordenadaY"), rs.getInt("Altura"),rs.getInt("Ancho"),rs.getInt("Rotacion"),rs.getInt("Angulo"), vTipo);
			
		}	
		rs.close();
		pstmt.close();
		con.close();
		
		
	} catch (SQLException e) {

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

	return vObjeto;
}

public Objetos DevolverObjetosPartida(int vIdPartida){
	
	Objetos vObjetos=null;
	Objeto vObjeto=null;

	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		
		String DevolverObjetosPartida = consultas.BuscarObjetosPartida();
		PreparedStatement pstmt = con.prepareStatement(DevolverObjetosPartida);	
		pstmt.setInt(1, vIdPartida);

		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next())
		{
			vObjeto=DevolverObjeto(rs.getInt("IdObjeto"), vIdPartida);
			vObjetos.insert(rs.getInt("IdObjeto"), vObjeto);
			
		}	
		rs.close();
		pstmt.close();
		con.close();
		
		
	} catch (SQLException e) {

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

	return vObjetos;
}

}
