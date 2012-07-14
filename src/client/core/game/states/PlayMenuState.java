package client.core.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State for when player opens menu while in game (Bu nasil cumle la).
 * @author Mefu
 */
public class PlayMenuState extends BasicGameState {

	private int id ;

	public PlayMenuState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

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
