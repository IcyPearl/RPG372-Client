package client.core.game.states;

import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.npc.Mob;
import client.core.entities.npc.Vendor;
import client.main.RPG372;

/**
 * Game play state, which means we have character and other things on a map.
 * Player can move, interact etc.
 * @author Mefu
 *
 */

public class GamePlayState extends BasicGameState {

	private int id;

	private Image[] terrains;


	public GamePlayState(int id){
		this.id = id;
		terrains = new Image[12];
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		RPG372.initTestGameInstance();
		terrains[0] = new Image("client/data/map/Terrain0.png");
		terrains[1] = new Image("client/data/map/Terrain1.png");
		terrains[2] = new Image("client/data/map/Terrain2.png");
		terrains[3] = new Image("client/data/map/Terrain3.png");
		terrains[4] = new Image("client/data/map/Terrain4.png");
		terrains[5] = new Image("client/data/map/Terrain5.png");
		terrains[6] = new Image("client/data/map/Terrain6.png");
		terrains[7] = new Image("client/data/map/Terrain7.png");
		terrains[8] = new Image("client/data/map/Terrain8.png");
		terrains[9] = new Image("client/data/map/Terrain9.png");
		terrains[10] = new Image("client/data/map/Terrain10.png");
		terrains[11] = new Image("client/data/map/Terrain11.png");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		int mapx = RPG372.gameInstance.getMap().getX();
		int mapy = RPG372.gameInstance.getMap().getY();
		for(int i = 0 ; i < mapx ; i++){
			for(int j = 0 ; j < mapy ; j++){
				int type = RPG372.gameInstance.getMap().get(i, j);
				terrains[type].draw(i*50, j*50, 0.5f);
			}
		}
		ListIterator<Mob> iterator = RPG372.gameInstance.getMobs().listIterator();
		while(iterator.hasNext()){
			iterator.next().render(arg0, arg1, arg2);
		}
		ListIterator<Vendor> iterator1 = RPG372.gameInstance.getVendors().listIterator();
		while(iterator1.hasNext()){
			iterator1.next().render(arg0, arg1, arg2);
		}
		RPG372.gameInstance.getCurrentPlayer().render(arg0, arg1, arg2);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		ListIterator<Mob> iterator = RPG372.gameInstance.getMobs().listIterator();
		while(iterator.hasNext()){
			iterator.next().update(arg0, arg1, arg2);
		}
		ListIterator<Vendor> iterator1 = RPG372.gameInstance.getVendors().listIterator();
		while(iterator1.hasNext()){
			iterator1.next().update(arg0, arg1, arg2);
		}
		RPG372.gameInstance.getCurrentPlayer().update(arg0, arg1, arg2);
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.MENU);
		}
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public int getID() {
		return id;
	}

}
