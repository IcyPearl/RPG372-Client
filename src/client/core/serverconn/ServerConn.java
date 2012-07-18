package client.core.serverconn;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
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
import client.main.RPG372;

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
		addMap();
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

		if(qe.resultSize() > 0)
			userId = Integer.parseInt(qe.vals.get(0).get(0));

		return qe.resultSize() > 0;
	}

	/**
	 * Registers user to DB.
	 * @param username
	 * @param password
	 * @return
	 */
	//COMPLETE

	public static boolean register(String username, String password, int gender) {
		int invId, userID;
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"+username+"'");
		//Existing User
		if(qe.resultSize() != 0)
			return false;

		qe.updateQuery("INSERT INTO RPG372DB_User (userName,password) VALUES ('" + username + "','"
				+ password + "')");
		qe.SendQuery("SELECT userId FROM RPG372DB_User WHERE userName='"
				+ username + "' AND password='" + password + "'");
		userID = Integer.parseInt(qe.vals.get(0).get(0));
		qe.SendQuery("SELECT invID FROM RPG372DB_Inventory ORDER BY invID DESC");
		invId = Integer.parseInt(qe.vals.get(0).get(0)) + 1;
		for(int i = 0 ; i <= 17 ; i++){
			qe.updateQuery("INSERT INTO RPG372DB_Inventory VALUES (" + invId + "," + i + "," + 0 + ")");
		}
		qe.updateQuery("INSERT INTO RPG372DB_Player VALUES ('" + username + "'," + 0 +  "," + 0 + "," 
				+ 1 + "," + invId + "," + userID + "," + 1 + "," + gender + "," + 100 + ")");

		return(login(username, password));
	}

	/**
	 * Will return player.Image loading is a problem. We'll see after we try it
	 * when game loop started.
	 * 
	 * @return
	 * @throws SlickException
	 */
	public static Player getPlayer() throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_User AS u LEFT JOIN "+ 
				"RPG372DB_Player AS p ON u.userID = p.userID " + 
				"WHERE u.userID = " + userId);
		PlayerData pd = new PlayerData();
		int posx, posy, invId, gold, gender;
		pd.setName(qe.getFirst().get(3));
		posx = Integer.parseInt(qe.getFirst().get(4));
		posy = Integer.parseInt(qe.getFirst().get(5));
		invId = Integer.parseInt(qe.getFirst().get(7));
		pd.setLevel(Integer.parseInt(qe.getFirst().get(9)));
		gender = Integer.parseInt(qe.getFirst().get(10));
		gold = Integer.parseInt(qe.getFirst().get(11));
		PlayerInventory plinv = getPlayerInventory(invId, gold);
		pd.setInv(plinv);
		pd.setGender(gender);
		Image img = new Image("client/data/player/player" + (gender+1) + ".png");
		Player player = new Player(userId, pd, posx, posy, img, true);
		return player;
	}

	/**
	 * Will return current map of user. Will use SAVED userId to get mapID.
	 * 
	 * @return
	 */
	public static Map getMap() {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT mapID FROM RPG372DB_User AS u LEFT JOIN "+ 
				"RPG372DB_Player AS p ON u.userID = p.userID " + 
				"WHERE u.userID = " + userId);
		return getMap(Integer.parseInt(qe.vals.get(0).get(0)));
	}

	/**
	 * Will return map with that id.
	 * THIS WORKS NOW WITH CURRENT MAP SETTINGS.
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

	/**
	 * Use ItemFactory. It is guaranteed to be loaded before this method is
	 * called.
	 * 
	 * @param userId
	 * @return
	 * @throws SlickException
	 */
	private static PlayerInventory getPlayerInventory(int invId, int gold)
			throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		qe.SendQuery("SELECT * FROM RPG372DB_Inventory WHERE invId='" + invId
				+ "'");
		PlayerInventory plinv = new PlayerInventory(null);
		int tempId;
		for (int i = 0; i < 16; i++) {
			tempId = Integer.parseInt(qe.vals.get(i).get(2));
			if (tempId == 0)
				continue;
			else {
				plinv.addItem(ItemFactory.getItem(tempId));
			}
		}
		plinv.setEqarmor((Armor) ItemFactory.getItem(Integer.parseInt(qe.vals.get(16)
				.get(2))));
		plinv.setEqweapon((Weapon) ItemFactory.getItem(Integer.parseInt(qe.vals.get(17)
				.get(2))));
		plinv.setGold(gold);
		return plinv;
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
				venInv.addItem(ItemFactory.getItem(tempId));
			}
		}

		return venInv;
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
				mobInv.addItem(ItemFactory.getItem(tempId));
			}
		}

		return mobInv;
	}

	public static ArrayList<Mob> getMobList() throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		ArrayList<Mob> mobList = new ArrayList<Mob>();
		qe.SendQuery("SELECT * FROM RPG372DB_Mob");
		int mobCount = qe.vals.size();
		for(int i = 0 ; i < mobCount ; i++){
			MobData md = new MobData();
			int id, level, invId;
			String name;
			id = Integer.parseInt(qe.vals.get(i).get(0));
			name = qe.vals.get(i).get(1);
			level = Integer.parseInt(qe.vals.get(i).get(3));
			invId = Integer.parseInt(qe.vals.get(i).get(4));
			md.setLevel(level);
			md.setName(name);
			md.setInv(getMobInventory(invId));
			Image img = new Image("client/data/mob/Mob" + id + ".png");
			Mob mob = new Mob(id, md, img);
			mobList.add(mob);
		}
		return mobList;
	}

	public static ArrayList<Vendor> getVendorList() throws SlickException {
		QueryExecutor qe = new QueryExecutor();
		ArrayList<Vendor> vendorList = new ArrayList<Vendor>();
		qe.SendQuery("SELECT * FROM RPG372DB_Vendor");
		int vendorCount = qe.vals.size();
		for(int i = 0 ; i < vendorCount ; i++){
			VendorData vd = new VendorData();
			int id, invId, rate, posx, posy;
			String name;
			id = Integer.parseInt(qe.vals.get(i).get(0));
			name = qe.vals.get(i).get(1);
			posx = Integer.parseInt(qe.vals.get(i).get(2));
			posy = Integer.parseInt(qe.vals.get(i).get(3));
			invId = Integer.parseInt(qe.vals.get(i).get(5));
			rate = Integer.parseInt(qe.vals.get(i).get(6));
			vd.setInv(getVendorInventory(invId));
			vd.setName(name);
			vd.setRate(rate);
			Image img = new Image("client/data/vendor/vendor1.png");
			Vendor vendor = new Vendor(id, vd, posx, posy, img);
			vendorList.add(vendor);
		}
		return vendorList;
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
			item.setName(qe.vals.get(i).get(2));
			item.setValue(Integer.parseInt(qe.vals.get(i).get(4)));

			//IF MISC
			if(items.get(i).get(1).equals("1")){
				item.setIcon(new Image("client/data/items/misc/misc1.png"));
				rtrItems.set(itemId, item);
				//IF ARMOR
			}else if(items.get(i).get(1).equals("2")){
				item.setIcon(new Image("client/data/items/armor/Armor"+(itemId%10)+".png"));
				Armor armor = item.getArmor();
				armor.setDefence(Integer.parseInt(items.get(i).get(5)));
				rtrItems.set(itemId, armor);
			}
			//IF WEAPON
			else if(items.get(i).get(1).equals("3")){
				item.setIcon(new Image("client/data/items/weapon/Weapon"+(itemId%10)+".png"));
				Weapon weapon = item.getWeapon();
				weapon.setDamage(Integer.parseInt(items.get(i).get(6)));
				rtrItems.set(itemId, weapon);
			}
		}
		return rtrItems;
	}

	public static void save(Player player){
		int posx, posy, mapId, level, gold;
		posx = player.getPosX();
		posy = player.getPosY();
		mapId = RPG372.gameInstance.getMap().getId();
		level = player.getPD().getLevel();
		gold = player.getPD().getInv().getGold();
		QueryExecutor qe = new QueryExecutor();
		qe.updateQuery("UPDATE RPG372DB_Player SET posX = " + posx + ",posY = " + posy + 
				",mapID = " + mapId + ",level = " + level + ",gold = " + gold 
				+ " WHERE userID = " + userId);
		PlayerInventory plinv = player.getPD().getInv();
		qe.SendQuery("SELECT invID FROM RPG372DB_Player WHERE userID = " + userId );
		int invId = Integer.parseInt(qe.getFirst().get(0));
		for(int i = 0 ; i < 18 ; i++){
			if(i < 16){
				int itemID = 0 ;
				if(plinv.getItem(i) != null)
					itemID = plinv.getItem(i).getId();
				qe.updateQuery("UPDATE RPG372DB_Inventory SET itemID = " + itemID +
						" WHERE invID = " + invId + " AND slotID = " + i );
			}else if(i == 16){
				int itemID = 0 ;
				if(plinv.getEqarmor() != null)
					itemID = plinv.getEqarmor().getId();
				qe.updateQuery("UPDATE RPG372DB_Inventory SET itemID = " + itemID +
						" WHERE invID = " + invId + " AND slotID = " + i );
			}else if(i == 17){
				int itemID = 0 ;
				if(plinv.getEqweapon() != null)
					itemID = plinv.getEqweapon().getId();
				qe.updateQuery("UPDATE RPG372DB_Inventory SET itemID = " + itemID +
						" WHERE invID = " + invId + " AND slotID = " + i );
			}
		}

	}

	public static void addMap(){
		Map a = new Map(20, 14, 0);
		try {
			a.readMap("src/client/data/map/map4.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sql = "";
		for (int i = 0; i < a.getY(); i++) {
			for (int j = 0; j < a.getX(); j++) {
				sql += "INSERT INTO RPG372DB_Cell VALUES("+j+","+i+","+a.get(i, j)+","+"4);\n";
			}
		}
		System.out.println(sql);
	}
}
