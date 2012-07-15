package client.main;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.item.Item;
import client.core.entities.npc.Mob;
import client.core.entities.npc.MobData;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorData;
import client.core.entities.npc.VendorInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerData;
import client.core.entities.player.PlayerInventory;
import client.core.game.GameInstance;
import client.core.game.states.*;
import client.core.map.Map;

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
		this.addState(new LoginState(LOGIN));
		this.addState(new MainMenuState(MENU));
		this.addState(new PlayMenuState(PLAYMENU));
		this.addState(new GamePlayState(GAMEPLAY));
		this.addState(new FightState(FIGHT));
		this.addState(new TradeState(TRADE));
		this.addState(new RegisterState(REGISTER));
	}

	public static void initTestGameInstance() throws SlickException{
		PlayerData pd = new PlayerData();
		MobData md = new MobData();
		VendorData vd = new VendorData();
		pd.setLevel(5);
		pd.setName("Mefu");
		md.setLevel(7);
		md.setName("Jordan");
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
		Item item2 = new Item(5);
		item2.setIcon(new Image("client/data/items/armor/Armor1.png"));
		item2.setName("Yellow Armor");
		item2.setValue(50);
		plinv.addItem(item1);
		plinv.addItem(item2);
		plinv.addItem(item2);
		plinv.addItem(item2);
		Mob mob = new Mob(213, md, 3, 3, new Image("client/data/mob/mob1.png"));
		Mob mob1 = new Mob(214, md, 5, 6, new Image("client/data/mob/mob4.png"));
		Vendor vend = new Vendor(231, vd, 10, 10, new Image("client/data/vendor/vendor1.png"));
		VendorInventory vendinv = new VendorInventory(vend);
		vendinv.addItem(item1);
		vendinv.addItem(item2);
		vd.setInv(vendinv);
		gameInstance = new GameInstance();
		gameInstance.setCurrentPlayer(pl);
		gameInstance.addMob(mob);
		gameInstance.addMob(mob1);
		gameInstance.addVendor(vend);
		gameInstance.setMap(new Map(1,1));
		try {
			gameInstance.getMap().readMap("src/client/data/map/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
