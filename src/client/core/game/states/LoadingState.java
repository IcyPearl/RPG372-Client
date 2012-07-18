package client.core.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.main.RPG372;

public class LoadingState extends BasicGameState {

	private int id ;
	private Image bg;

	public LoadingState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		bg = new Image("client/data/backgrounds/loadingbg.jpg");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		bg.draw(0, 0);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		RPG372.loadDB();
		RPG372.initGameInstance();
		arg1.enterState(RPG372.MENU);
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
