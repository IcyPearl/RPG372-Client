package client.core.serverconn;

import org.newdawn.slick.SlickException;

import client.core.entities.item.Armor;
import client.core.entities.item.Item;
import client.core.entities.item.Weapon;
import client.core.entities.npc.Mob;
import client.core.entities.npc.MobData;
import client.core.entities.npc.MobInventory;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorData;
import client.core.entities.npc.VendorInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerData;
import client.core.entities.player.PlayerInventory;
import client.core.map.Map;

/**
 * Responsible for retrieving current state of game. These methods will be used in
 * RPG372.initGameInstance() mainly.
 * @author Mefu
 */

public class ServerConn {
	
	//For holding user id of user that log on.
	private static int userId ;

	public static void main(String[] args) {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Item WHERE itemId='"+14+"'");
		System.out.println(qe.vals);
		try {
			System.out.println(getItem(14).getName());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Will query server db and returns if login is successful.
	 * Got to save log on user id at somewhere so when player is requested
	 * we can get that users player.
	 * @param username
	 * @param password
	 * @return
	 */
	
	public static boolean login(String username, String password){
		QueryExecutor qe = new QueryExecutor();

		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"+username+"' AND password='"+password+"'");
		return qe.resultSize() > 0;
	}
	
	/**
	 * Will register user to db. For now it doesnt work, because
	 * Can not issue data manipulation statements with executeQuery().
	 * Selcuk bakacak :D. 
	 * @param username
	 * @param password
	 * @return
	 */
	
	public static boolean register(String username, String password){
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("INSERT INTO RPG372DB_User VALUES ('" + username + "','" + password + "')");
		if(login(username, password)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Will return player.Image loading is a problem. We'll see after we try it when
	 * game loop started.
	 * @return
	 * @throws SlickException 
	 */
	public static Player getPlayer() throws SlickException{
		getPlayerData(userId);//To get rid of not used errors
		return null;
	}
	
	public static Vendor getVendor(int vendorId){
		getVendorData(vendorId);//To get rid of not used errors.
		getVendorInventory(vendorId);
		return null;
	}
	
	public static Mob getMob(int mobId) throws SlickException{
		getMobData(mobId);//To get rid of not used errors
		getMobInventory(mobId);
		return null;
	}
	
	public static Item getItem(int itemId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Item WHERE itemId='"+itemId+"'");
		if(qe.resultSize()!=1)
			return null;
		Item item = new Item(itemId);
		item.setName(qe.getFirst().get(2));
		item.setValue(Integer.parseInt(qe.getFirst().get(4)));

		//IF MISC
		if(qe.getFirst().get(1).equals("1"))
			return item;
		//IF ARMOR
		if(qe.getFirst().get(1).equals("2")){
			//item.setIcon(new Image("client/data/items/armor/Armor"+(itemId%10)+".png"));
			Armor armor = item.getArmor();
			qe.SendQuery("SELECT * FROM RPG372DB_Item_Armor WHERE itemId='"+itemId+"'");
			armor.setDefence(Integer.parseInt(qe.getFirst().get(1)));
			return armor;
		}
		//IF WEAPON
		if(qe.getFirst().get(1).equals("3")){
			//item.setIcon(new Image("client/data/items/weapon/Weapon"+(itemId%10)+".png"));
			Weapon weapon = item.getWeapon();
			qe.SendQuery("SELECT * FROM RPG372DB_Item_Weapon WHERE itemId='"+itemId+"'");
			weapon.setDamage(Integer.parseInt(qe.getFirst().get(1)));
			return weapon;
		}
		//ELSE
		return null;
	}
	
	/**
	 * Will return current map of user.
	 * Will use SAVED userId to get mapID.
	 * @return
	 */
	public static Map getMap(){
		return null;
	}
	
	/**
	 * Will return map with that id.
	 * @param mapId
	 * @return
	 */
	public static Map getMap(int mapId){
		return null;
	}

	private static PlayerData getPlayerData(int playerId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Player WHERE userId='"+playerId+"'");
		PlayerData pData = new PlayerData();
		
		pData.setLevel(Integer.parseInt(qe.getFirst().get(6)));
		pData.setName(qe.getFirst().get(0));
		pData.setInv(getPlayerInventory(playerId));
		
		return null;
	}

	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is called.
	 * @param playerId
	 * @return
	 * @throws SlickException
	 */
	private static PlayerInventory getPlayerInventory(int playerId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Player WHERE userId='"+playerId+"'");
		int invId = Integer.parseInt(qe.getFirst().get(4));
		int gold = Integer.parseInt(qe.getFirst().get(8));
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='"+invId+"'");
		PlayerInventory pi = new PlayerInventory(null);
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if(tempId == 0)
				continue;
			else{
				pi.addItem(getItem(tempId));
			}
		}
		
		PlayerInventory plinv = new PlayerInventory(null);
		plinv.setEqarmor((Armor)getItem(Integer.parseInt(qe.vals.get(16).get(2))));
		plinv.setEqweapon((Weapon)getItem(Integer.parseInt(qe.vals.get(17).get(2))));
		plinv.setGold(gold);
		return plinv;
	}
	
	private static VendorData getVendorData(int vendorId){
		return null;
	}
	
	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is called.
	 * @param invId
	 * @return
	 */
	private static VendorInventory getVendorInventory(int invId){
		return null;
	}

	private static MobData getMobData(int mobID) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Mob WHERE mobID='"+mobID+"'" );
		if(qe.resultSize() == 0)
			return null;
		MobData md = new MobData();
		md.setLevel(Integer.parseInt(qe.getFirst().get(3)));
		md.setName(qe.getFirst().get(1));
		md.setInv(getMobInventory(Integer.parseInt(qe.getFirst().get(4))));

		return md;
	}
	
	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is called.
	 * @param invId
	 * @return
	 * @throws SlickException
	 */
	private static MobInventory getMobInventory(int invId) throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='"+invId+"'");

		if(qe.resultSize()==0)
			return null;
		MobInventory mobInv = new MobInventory(null);
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if(tempId == 0)
				continue;
			else{
				mobInv.addItem(getItem(tempId));
			}
		}

		return mobInv;
	}
}
