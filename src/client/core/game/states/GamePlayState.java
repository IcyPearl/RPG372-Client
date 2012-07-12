package client.core.game.states;

import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.npc.Mob;
import client.main.RPG372;

/**
 * Game play state, which means we have character and other things on a map.
 * Player can move, interact etc.
 * @author Mefu
 *
 */

public class GamePlayState extends BasicGameState {

	private int id;
	
	private Image map0;
	private Image map1;
	
	
	public GamePlayState(int id){
		this.id = id;
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		RPG372.initTestGameInstance();
		map0 = new Image("client/data/map0.png");
		map1 = new Image("client/data/map1.png");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		int mapx = RPG372.gameInstance.getMap().getX();
		int mapy = RPG372.gameInstance.getMap().getY();
		for(int i = 0 ; i < mapx ; i++){
			for(int j = 0 ; j < mapy ; j++){
				int type = RPG372.gameInstance.getMap().get(i, j);
				if(type == 0){
					map0.draw(i*100, j*100);
				}else if(type == 1){
					map1.draw(i*100, j*100);
				}
			}
		}
		RPG372.gameInstance.getCurrentPlayer().render(arg0, arg1, arg2);
		ListIterator<Mob> iterator = RPG372.gameInstance.getMobs().listIterator();
		while(iterator.hasNext()){
			iterator.next().render(arg0, arg1, arg2);
		}
	}
	
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		RPG372.gameInstance.getCurrentPlayer().update(arg0, arg1, arg2);
		ListIterator<Mob> iterator = RPG372.gameInstance.getMobs().listIterator();
		while(iterator.hasNext()){
			iterator.next().update(arg0, arg1, arg2);
		}
	}
	
	public int getID() {
		return id;
	}

}
