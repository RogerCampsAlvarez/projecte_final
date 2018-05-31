package inici;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexioBD {

	
	/**
	 * CONNEXI� BASE DE DADES
	 * 
	 * @param db
	 */	
	Connection con = null;
	public ConnexioBD() {

		try {
			Class.forName("org.postgresql.Driver");
			//con = DriverManager.getConnection("jdbc:postgresql://144.217.11.3/luxyrestaurant", "luxy", "LuxyRestaurant");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/LuxyRestaurant", "postgres", "postgres");
		} catch (Exception e) {
			e.getStackTrace();
		}		
	}

	/**
	 * CONSULTA BASE DE DAES
	 * 
	 * @param sQuery
	 */
	public ResultSet queryDB(String sQuery) {
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery(sQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * SCRIPTS BASE DE DADES
	 * 
	 * @param sQuery
	 */
	public void execDB(String sQuery) {
		try {
			con.createStatement().execute(sQuery);
			//db.conn.commit();
		} catch (Exception e) {
		}
	}
	
	/**
	 * RETORNA L'STATEMENT DE LA CONEXIO
	 * 
	 * @return
	 * @throws SQLException
	 */
	/*public Statement getStatement(){
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
		
	}*/
	
	public Connection getConnection() {
		return con;
		
	}
	/**
	 * DESCONEXI� BASE DE DADES
	 *
	 */
	public void desconnectarDB() {
		try {
			con.close();
		} catch (Exception e) {
		}
	}

}
