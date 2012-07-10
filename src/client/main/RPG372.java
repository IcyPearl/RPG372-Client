package client.main;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.player.Player;
import client.core.entities.player.PlayerData;
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
	public static GameInstance gameInstance;
	
	public RPG372() {
		super("RPG372");
	}
	
	public static void main(String[] args) throws SlickException{
         AppGameContainer app = new AppGameContainer(new RPG372());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
	
	public void initStatesList(GameContainer arg0) throws SlickException {
		//this.addState(new LoginState(LOGIN));
		this.addState(new MainMenuState(MENU));
		this.addState(new PlayMenuState(PLAYMENU));
		this.addState(new GamePlayState(GAMEPLAY));
		this.addState(new FightState(FIGHT));
		this.addState(new TradeState(TRADE));
	}

	public static void initTestGameInstance() throws SlickException{
		PlayerData pd = new PlayerData();
		pd.setLevel(5);
		pd.setName("Mefu");
		int posx = 0;
		int posy = 0;
		Image img = new Image("client/data/player.png");
		Player pl = new Player(111, pd, posx, posy, img, true);
		gameInstance = new GameInstance();
		gameInstance.setCurrentPlayer(pl);
		gameInstance.setMap(new Map(1,1));
		try {
			gameInstance.getMap().readMap("src/client/data/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
