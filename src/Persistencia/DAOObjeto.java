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
import Logica.Objetos;

/*********************************************
 * Descripción: 	Clase que devuelve datos de los Objetos en la base
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/
public class DAOObjeto {
	
private String Url, User, Password;
	
	public DAOObjeto(String url,String user, String password){
		this.Url = url;
		this.User = user;
		this.Password = password;
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
			pstmt.setString(9,vObjeto.ObtenerTipo());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error de SQL");
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
		
		System.out.println("Error de SQL");

	} catch (ClassNotFoundException e) {
		System.out.println(e.getMessage());
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
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
		
	} catch (ClassNotFoundException e) {
		System.out.println(e.getMessage());
	} catch (SQLException e) {
		String error = Integer.toString(e.getErrorCode());
		System.out.println(error);
	}
	
}

public Objeto DevolverObjeto(int vIdObjeto,int vIdPartida){
	
	Objeto vObjeto=null;

	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(Url, User, Password);
		Consultas consultas = new Consultas();
		String vTipo=null;
		String DevolverObjeto = consultas.buscarObjeto();
		PreparedStatement pstmt = con.prepareStatement(DevolverObjeto);	
		pstmt.setInt(1, vIdObjeto);
		pstmt.setInt(2, vIdPartida);
		ResultSet rs = pstmt.executeQuery();
		
		
		if(rs.next())
			vTipo=rs.getString("tipo");
		
		rs.close();
		pstmt.close();
		
		if(vTipo.equals("Aereo")){
			DevolverObjeto = consultas.DevolverDronAereo();
			pstmt = con.prepareStatement(DevolverObjeto);
			pstmt.setInt(1, vIdObjeto);
			ResultSet pr = pstmt.executeQuery();
			if(pr.next())
				vObjeto=new DronAereo(pr.getInt("idObjeto"),pr.getInt("coordX"),pr.getInt("coordY"),pr.getInt("altura"),pr.getInt("ancho"),pr.getInt("rotacion"),pr.getInt("angulo"),pr.getString("tipo"),pr.getInt("velocidad"),pr.getBoolean("camara"),pr.getBoolean("canion"),pr.getInt("vision"),pr.getInt("motoresActivos"),pr.getBoolean("tieneBomba"),pr.getBoolean("bombaRota"));
			pr.close();
			pstmt.close();
		}
		else if(vTipo.equals("Terrestre")){
			DevolverObjeto = consultas.DevolverDronTerrestre();
			pstmt = con.prepareStatement(DevolverObjeto);
			pstmt.setInt(1, vIdObjeto);
			ResultSet pr = pstmt.executeQuery();
			if(pr.next())
				vObjeto=new DronTerrestre(pr.getInt("IdObjeto"),pr.getInt("coordX"),pr.getInt("coordY"),pr.getInt("altura"),pr.getInt("ancho"),pr.getInt("rotacion"),pr.getInt("angulo"),pr.getString("tipo"),pr.getInt("velocidad"), pr.getBoolean("camara"),pr.getBoolean("canion"),pr.getInt("vision"), pr.getInt("blindajeActivo"));
			pr.close();
			pstmt.close();
		}
		else{
			DevolverObjeto = consultas.DevolverBase();
			pstmt = con.prepareStatement(DevolverObjeto);
			pstmt.setInt(1, vIdObjeto);
			ResultSet pr = pstmt.executeQuery();
			if(pr.next())
				vObjeto=new Base(pr.getInt("IdObjeto"),pr.getInt("coordX"),pr.getInt("coordY"),pr.getInt("altura"),pr.getInt("ancho"),pr.getInt("rotacion"),pr.getInt("angulo"),pr.getString("tipo"),pr.getInt("vidaPolvorin"),pr.getInt("vidaZonaDespegue"));
			pr.close();
			pstmt.close();
		}

		con.close();
		
		
	} catch (SQLException e) {
		System.out.println("Error de SQL");

	} catch (ClassNotFoundException e) {
		System.out.print(e.getMessage());
	}

	return vObjeto;
}

public Objetos DevolverObjetosPartida(int vIdPartida){
	
	Objetos vObjetos= new Objetos();
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
		System.out.println("Error de SQL");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}

	return vObjetos;
}

}
