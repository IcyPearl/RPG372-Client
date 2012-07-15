package client.core.serverconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import client.core.game.GameInstance;
import client.main.RPG372;

/**
 * Responsible for retrieving current state of game. This state will be
 * initialized with GameInstance class.
 * 
 * @author Mefu
 */

public class ServerConn {

	final static String userName = "RPG372USER";
	final static String password = "RPGRPG123";
	final static String url = "jdbc:mysql://mefu.mine.nu/RPG372DB";
	static Connection conn = null;
	public static void main(String[] args) {

		System.out.println(login("SelcukTestUser", "123"));
	}
		
	private static ResultSet query(String sqlQuery) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			conn.close();
		} catch (Exception e) {
		}
		return rs;
	}

	/**
	 * Will get all info from server for this user. And fill GameInstance class.
	 * This class should Create player with current player data informations.
	 * RPG372.gameInstance.setCurrentPlayer(created player). Create mobs with
	 * mob data informations. RPG372.gameInstance.addMob(created mobs). Create
	 * vendors with vendor data informations.
	 * RPG372.gameInstance.addVendor(created vendors). Create other players with
	 * player data informations.(if game is online)
	 * RPG372.gameInstance.addOtherPlayer(created players). Create map from
	 * database. RPG372.gameInstance.setMap(created map) This should be enough
	 * for initializing game.
	 * 
	 * @param username
	 */

	public static void initialize(String username) {
		RPG372.gameInstance = new GameInstance();
	}

	/**
	 * Should save all informations that created at method initialize.
	 */
	public static void saveToDB() {

	}

	/**
	 * Will query server db and returns if login is successful.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public static boolean login(String username, String password) {
		QueryExecutor qr = new QueryExecutor();
		
		qr.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"+username+"' AND password='"+password+"'");
		return qr.getResultCount() > 0;
	}
	
	public static void getMob(int mobID){
		QueryExecutor qr = new QueryExecutor();
		qr.SendQuery("SELECT userId FROM RPG372DB_Mob WHERE mobID='"+mobID+"'" );
		
	}

}
