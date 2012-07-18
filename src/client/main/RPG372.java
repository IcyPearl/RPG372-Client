package client.main;


import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.item.Armor;
import client.core.entities.item.Item;
import client.core.entities.item.ItemFactory;
import client.core.entities.item.Weapon;
import client.core.entities.npc.MobSpawner;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorData;
import client.core.entities.npc.VendorInventory;
import client.core.entities.npc.VendorSpawner;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerData;
import client.core.entities.player.PlayerInventory;
import client.core.game.GameInstance;
import client.core.game.states.*;
import client.core.map.Map;
import client.core.serverconn.ServerConn;

/**
 * States will be initialized here and game will be started.
 * This should display a black screen. Use it for testing if slick and lwjgl are working.
 * @author Mefu
 */

public class RPG372 extends StateBasedGame {

	public static final int LOGIN = 1;
	public static final int GAMEPLAY = 2;
	public static final int MENU = 3;
	public static final int PLAYMENU = 4;
	public static final int TRADE = 5;
	public static final int FIGHT = 6;
	public static final int REGISTER = 7;
	public static final int LOOT = 8;
	public static final int LOAD = 9;
	public static GameInstance gameInstance;

	public RPG372() {
		super("RPG372");
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new RPG372());

		app.setDisplayMode(1366, 768, false);
		app.setFullscreen(true);
		app.start();
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		//RPG372.initTestGameInstance();
		this.addState(new LoginState(LOGIN));
		this.addState(new MainMenuState(MENU));
		this.addState(new PlayMenuState(PLAYMENU));
		this.addState(new GamePlayState(GAMEPLAY));
		this.addState(new FightState(FIGHT));
		this.addState(new TradeState(TRADE));
		this.addState(new RegisterState(REGISTER));
		this.addState(new LootState(LOOT));
		this.addState(new LoadingState(LOAD));
	}

	public static void initTestGameInstance() throws SlickException{
		PlayerData pd = new PlayerData();
		VendorData vd = new VendorData();
		pd.setLevel(5);
		pd.setName("Mefu");
		vd.setName("Ibne Satici");
		int posx = 0;
		int posy = 0;
		Image img = new Image("client/data/player/player1.png");
		Player pl = new Player(111, pd, posx, posy, img, true);
		PlayerInventory plinv = new PlayerInventory(pl);
		plinv.setGold(500);
		pd.setInv(plinv);
		Item item1 = new Item(10);
		item1.setIcon(new Image("client/data/items/misc/misc1.png"));
		item1.setName("Magic Powder");
		item1.setValue(30);
		Armor item2 = new Armor(5);
		Armor item4 = new Armor(3);
		Weapon item3 = new Weapon(6);
		item2.setDefence(5);
		item4.setDefence(10);
		item2.setIcon(new Image("client/data/items/armor/Armor1.png"));
		item4.setIcon(new Image("client/data/items/armor/Armor2.png"));
		item2.setName("Yellow Armor");
		item4.setName("Chain Mail");
		item2.setValue(50);
		item4.setValue(100);
		item3.setDamage(5);
		item3.setIcon(new Image("client/data/items/weapon/Weapon1.png"));
		item3.setName("Hammer");
		item3.setValue(50);
		plinv.addItem(item1);
		plinv.addItem(item2);
		plinv.addItem(item2);
		plinv.addItem(item2);
		plinv.addItem(item4);
		plinv.addItem(item3);
		Vendor vend = new Vendor(231, vd, 10, 10, new Image("client/data/vendor/vendor1.png"));
		VendorInventory vendinv = new VendorInventory(vend);
		vendinv.addItem(item1);
		vendinv.addItem(item2);
		vd.setInv(vendinv);
		gameInstance = new GameInstance();
		gameInstance.setCurrentPlayer(pl);
		gameInstance.addVendor(vend);
		gameInstance.setMap(new Map(1,1,10));
		try {
			gameInstance.getMap().readMap("src/client/data/map/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadDB() throws SlickException {
		ItemFactory.loadItems();
		MobSpawner.loadMobs();
		VendorSpawner.loadVendors();
	}
	
	public static void initGameInstance() throws SlickException {
		gameInstance = new GameInstance();
		gameInstance.setCurrentPlayer(ServerConn.getPlayer());
		gameInstance.setMap(ServerConn.getMap());
		gameInstance.spawnMobs();
		gameInstance.spawnVendor();
	}
	
	public static void changeMap(int mapId) throws SlickException{
		gameInstance.setMap(ServerConn.getMap(mapId));
		gameInstance.spawnMobs();
		gameInstance.spawnVendor();
	}
}
