package client.core.serverconn;

/**
 * Responsible for retrieving current state of game.
 * This state will be initialized with GameInstance class.
 * @author Mefu
 */

public class ServerConn {
	
	/**
	 * Will get all info from server for this user. And fill GameInstance class.
	 * This class should
	 * Create player with current player data informations.
	 * RPG372.gameInstance.setCurrentPlayer(created player).
	 * Create mobs with mob data informations.
	 * RPG372.gameInstance.addMob(created mobs).
	 * Create vendors with vendor data informations.
	 * RPG372.gameInstance.addVendor(created vendors).
	 * Create other players with player data informations.(if game is online)
	 * RPG372.gameInstance.addOtherPlayer(created players).
	 * Create map from database.
	 * RPG372.gameInstance.setMap(created map)
	 * This should be enough for initializing game.
	 * @param username
	 */
	
	public static void initialize(String username){
		
	}
	/**
	 * Should save all informations that created at method initialize.
	 */
	public static void saveToDB(){
		
	}
	
	/**
	 * Will query server db and returns if login is successful.
	 * @param username
	 * @param password
	 * @return
	 */
	
	public static boolean login(String username, String password){
		
		
		return false;
	}
	
}
