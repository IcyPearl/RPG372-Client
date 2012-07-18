package client.core.serverconn;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.entities.item.Armor;
import client.core.entities.item.Item;
import client.core.entities.item.ItemFactory;
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
 * Responsible for retrieving current state of game. These methods will be used
 * in RPG372.initGameInstance() mainly.
 * 
 * @author Mefu
 */

public class ServerConn {

	// For holding user id of user that log on.
	private static int userId;

	public static void main(String[] args) throws SlickException {
		Map t = getMap(1);
		for (int i = 0; i < t.getY(); i++) {
			for (int j = 0; j < t.getX(); j++) {
				System.out.print(t.get(i, j)+" ");
			}
			System.out.println();
		}
	}

	/**
	 * Will query server db and returns if login is successful. Got to save log
	 * on user id at somewhere so when player is requested we can get that users
	 * player.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public static boolean login(String username, String password) {
		QueryExecutor qe = new QueryExecutor();

		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"
				+ username + "' AND password='" + password + "'");
		return qe.resultSize() > 0;
	}

	/**
	 * Will register user to db. For now it doesnt work, because Can not issue
	 * data manipulation statements with executeQuery(). Selcuk bakacak :D.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public static boolean register(String username, String password) {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"+username+"'");
		//Existing User
		if(qe.resultSize() != 0)
			return false;
		
		qe.updateQuery("INSERT INTO RPG372DB_User (userName,password) VALUES ('" + username + "','"
				+ password + "')");
		//Bu Ne?
		//return(login(username, password));
		return true;
	}

	/**
	 * Will return player.Image loading is a problem. We'll see after we try it
	 * when game loop started.
	 * 
	 * @return
	 * @throws SlickException
	 */
	public static Player getPlayer() throws SlickException {
		getPlayerData(userId);// To get rid of not used errors
		return null;
	}

	public static Vendor getVendor(int vendorId) {
		getVendorData(vendorId);// To get rid of not used errors.
		getVendorInventory(vendorId);
		return null;
	}

	public static Mob getMob(int mobId) throws SlickException {
		getMobData(mobId);// To get rid of not used errors
		getMobInventory(mobId);
		return null;
	}

	public static Item getItem(int itemId) {
		return ItemFactory.getItem(itemId);
	}

	/**
	 * Will return current map of user. Will use SAVED userId to get mapID.
	 * 
	 * @return
	 */
	public static Map getMap() {
		int Yazdik_Ya_Amk_Tepeye_Oyun_Ilk_basladiginda_Hangi_mapda_olduumuzu_Bilmok_Ona_Gore_Dondurcek;
		return null;
	}

	/**
	 * Will return map with that id.
	 * 
	 * @param mapId
	 * @return
	 */
	public static Map getMap(int mapId) {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Map WHERE map_id='"+mapId+"'");
		int x = Integer.parseInt(qe.getFirst().get(2));
		int y = Integer.parseInt(qe.getFirst().get(3));
		Map rtrnmap = new Map(x, y, mapId);
		qe.SendQuery("SELECT * FROM RPG372DB_Cell WHERE MapID='"+mapId+"' ORDER BY y ASC, x ASC");
		int p = 0;
		int t = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				p = i*x+j;
				t = Integer.parseInt(qe.vals.get(p).get(2));
				rtrnmap.setCell(j, i, t);
			}
		}
		return rtrnmap;
	}

	private static PlayerData getPlayerData(int playerId) throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Player WHERE userId='" + playerId
				+ "'");
		PlayerData pData = new PlayerData();

		pData.setLevel(Integer.parseInt(qe.getFirst().get(6)));
		pData.setName(qe.getFirst().get(0));
		pData.setInv(getPlayerInventory(playerId));

		return null;
	}

	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is
	 * called.
	 * 
	 * @param playerId
	 * @return
	 * @throws SlickException
	 */
	private static PlayerInventory getPlayerInventory(int playerId)
			throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Player WHERE userId='" + playerId
				+ "'");
		int invId = Integer.parseInt(qe.getFirst().get(4));
		int gold = Integer.parseInt(qe.getFirst().get(8));
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='" + invId
				+ "'");
		PlayerInventory pi = new PlayerInventory(null);
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if (tempId == 0)
				continue;
			else {
				pi.addItem(getItem(tempId));
			}
		}

		PlayerInventory plinv = new PlayerInventory(null);
		plinv.setEqarmor((Armor) getItem(Integer.parseInt(qe.vals.get(16)
				.get(2))));
		plinv.setEqweapon((Weapon) getItem(Integer.parseInt(qe.vals.get(17)
				.get(2))));
		plinv.setGold(gold);
		return plinv;
	}

	private static VendorData getVendorData(int vendorId) {
		
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Vendor WHERE vendorID='" + vendorId + "'");
		if (qe.resultSize() != 1)
			return null;
		VendorData vd = new VendorData();
		vd.setInv(getVendorInventory(Integer.parseInt(qe.getFirst().get(5))));
		vd.setName(qe.getFirst().get(1));
		return vd;
	}

	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is
	 * called.
	 * 
	 * @param invId
	 * @return
	 */
	private static VendorInventory getVendorInventory(int invId) {
		
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='" + invId
				+ "'");

		if (qe.resultSize() == 0)
			return null;
		
		VendorInventory venInv = new VendorInventory(null);
		
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if (tempId == 0)
				continue;
			else {
				venInv.addItem(getItem(tempId));
			}
		}

		return venInv;
	}

	private static MobData getMobData(int mobID) throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Mob WHERE mobID='" + mobID + "'");
		if (qe.resultSize() == 0)
			return null;
		MobData md = new MobData();
		md.setLevel(Integer.parseInt(qe.getFirst().get(3)));
		md.setName(qe.getFirst().get(1));
		md.setInv(getMobInventory(Integer.parseInt(qe.getFirst().get(4))));

		return md;
	}

	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is
	 * called.
	 * 
	 * @param invId
	 * @return
	 * @throws SlickException
	 */
	private static MobInventory getMobInventory(int invId)
			throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='" + invId
				+ "'");

		if (qe.resultSize() == 0)
			return null;
		MobInventory mobInv = new MobInventory(null);
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if (tempId == 0)
				continue;
			else {
				mobInv.addItem(getItem(tempId));
			}
		}

		return mobInv;
	}

	public static ArrayList<Item> getItemList() throws SlickException{
		QueryExecutor qe = new QueryExecutor();
		ArrayList<ArrayList<String>> items;
		ArrayList<Item> rtrItems = new ArrayList<Item>();
		for (int i = 0; i <= 30; i++) {
			rtrItems.add(null);
		}
		String query = "SELECT i.itemID,typeID,Name,icon,value,defence,damage "+ 
				"FROM RPG372DB.RPG372DB_Item AS i "+
				"LEFT JOIN RPG372DB.RPG372DB_Item_Armor AS a ON i.itemID = a.itemID "+
				"LEFT JOIN RPG372DB.RPG372DB_Item_Weapon AS w ON i.itemID = w.itemID "+
				"ORDER BY itemID ASC;";
		
				qe.SendQuery(query);
		items = qe.vals;
		for (int i = 0; i < items.size(); i++) {
			int itemId = Integer.parseInt(items.get(i).get(0));
			Item item = new Item(itemId);
			item.setName(qe.getFirst().get(2));
			item.setValue(Integer.parseInt(qe.getFirst().get(4)));

			//IF MISC
			if(items.get(i).get(1).equals("1"))
				rtrItems.set(itemId, item);
			//IF ARMOR
			else if(items.get(i).get(1).equals("2")){
				//item.setIcon(new Image("client/data/items/armor/Armor"+(itemId%10)+".png"));
				Armor armor = item.getArmor();
				armor.setDefence(Integer.parseInt(items.get(i).get(5)));
				rtrItems.set(itemId, armor);
			}
			//IF WEAPON
			else if(items.get(i).get(1).equals("3")){
				//item.setIcon(new Image("client/data/items/weapon/Weapon"+(itemId%10)+".png"));
				Weapon weapon = item.getWeapon();
				weapon.setDamage(Integer.parseInt(items.get(i).get(6)));
				rtrItems.set(itemId, weapon);
			}
		}

		return rtrItems;

	}
}
