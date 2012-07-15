package client.core.serverconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import client.core.entities.item.Armor;
import client.core.entities.item.Item;
import client.core.entities.item.Weapon;
import client.core.entities.npc.MobData;
import client.core.entities.npc.MobInventory;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorData;
import client.core.entities.npc.VendorInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerData;
import client.core.entities.player.PlayerInventory;
import client.core.game.GameInstance;
import client.core.map.Map;
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

		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Item WHERE itemId='"+14+"'");
		System.out.println(qe.mp);
	try {
//			MobData md = getMobData(1);
//			System.out.println(md.getName());
//			System.out.println(md.getLevel());
//			System.out.println(md.getInv().getItems());
			System.out.println(getItem(14).getName());
		} catch (NumberFormatException | SlickException e) {
			e.printStackTrace();
		}
	}
		
	public static ResultSet query(String sqlQuery) {
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
		QueryExecutor qe = new QueryExecutor();
		
		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"+username+"' AND password='"+password+"'");
		return qe.getResultCount() > 0;
	}
	
	public static Map getMap(int mapId){
		return null;
	}
	
	public static Vendor getVendor(int vendorId){
		return null;
	}
	
	public static VendorData getVendorData(int vendorId){
		return null;
	}
	
	public static VendorInventory getVendorInventory(int invId){
		return null;
	}
	
	
	//get login usernames player
	public static Player getPlayer(int userId){
		
		return null;
	}
	
	public static PlayerInventory getPlayerInventory(int playerId){
		PlayerInventory plinv = new PlayerInventory(null);
		plinv.setEqarmor(null);
		plinv.setEqweapon(null);
		plinv.setGold(0);
		return null;
	}
	
	public static PlayerData getPlayerData(int playerId){
		return null;
	}
	
	public static MobData getMobData(int mobID) throws NumberFormatException, SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Mob WHERE mobID='"+mobID+"'" );
		if(qe.getResultCount() == 0)
			return null;
		MobData md = new MobData();
		md.setLevel(Integer.parseInt(qe.getRow(0).get(3)));
		md.setName(qe.getRow(0).get(0));
		md.setInv(getMobInventory(Integer.parseInt(qe.getRow(0).get(4))));
		
		return md;
	}
	private static MobInventory getMobInventory(int invId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='"+invId+"'");
		
		if(qe.getResultCount()==0)
			return null;
		MobInventory mobInv = new MobInventory(null);
			int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.getRow(0).get(2));
			if(tempId == 0)
				continue;
			else{
				mobInv.addItem(getItem(tempId));
			}
		}
		
		return mobInv;
	}
	public static Item getItem(int itemId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Item WHERE itemId='"+itemId+"'");
		if(qe.getResultCount()!=1)
			return null;
		Item item = new Item(itemId);
		item.setName(qe.getRow(0).get(0));
		item.setValue(Integer.parseInt(qe.getRow(0).get(3)));
		
		//IF MISC
		if(qe.getRow(0).get(4).equalsIgnoreCase("1"))
			return item;
		//IF ARMOR
		if(qe.getRow(0).get(4).equalsIgnoreCase("2")){
			item.setIcon(new Image("client/data/items/armor/Armor"+(itemId%10)+".png"));
			Armor armor = (Armor)item;
			qe.SendQuery("SELECT * FROM RPG372DB_Item_Armor WHERE itemId='"+itemId+"'");
			armor.setDefence(Integer.parseInt(qe.getRow(0).get(1)));
			return armor;
		}
		//IF WEAPON
		if(qe.getRow(0).get(4).equalsIgnoreCase("3")){
			item.setIcon(new Image("client/data/items/weapon/Weapon"+(itemId%10)+".png"));
			Weapon weapon = (Weapon)item;
			qe.SendQuery("SELECT * FROM RPG372DB_Item_Weapon WHERE itemId='"+itemId+"'");
			weapon.setDamage(Integer.parseInt(qe.getRow(0).get(1)));
			return weapon;
		}
		//ELSE
		return null;
	}
}
