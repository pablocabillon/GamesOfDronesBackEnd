package Persistencia;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*********************************************
 * Descripción: 	Clase utilizada para la conexión y desconexión a la base de datos
 * Autor: 			Martín Torres
 * Versión: 		1.0.0
 * Modificaciones: 
 **********************************************/

public class AccesoBase {
	
	String Driver, Url, User, Password;

	
	public Connection AbrirConexion () throws FileNotFoundException, IOException {
		Connection con = null;
		try
		{

			String driver = this.Driver;
			Class.forName(driver);
			con = DriverManager.getConnection(this.Url, this.User, this.Password);		
		}
		catch (ClassNotFoundException e)
		{
			e.getMessage();
		}
		catch (SQLException e)
		{
			e.getMessage();
		}
		return con;
		
	}
	
	public void CerrarConexion (Connection con) throws SQLException {
		con.close();		
	}
	public String ObtenerDriver() {
		return Driver;
	}

	public void SetearDriver(String driver) {
		this.Driver = driver;
	}

	public String ObtenerUrl() {
		return Url;
	}

	public void SetearUrl(String url) {
		this.Url = url;
	}

	public String ObtenerUsuario() {
		return User;
	}

	public void SetearUsuario(String user) {
		this.User = user;
	}

	public String ObtenerPassword() {
		return Password;
	}

	public void SetearPassword(String password) {
		this.Password = password;
	}

}
