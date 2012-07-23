package client.core.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.main.RPG372;

/**
 * State for when player opens menu while in game (Bu nasil cumle la).
 * @author Mefu
 */
public class ControlsState extends BasicGameState {

	private int id ;

	public ControlsState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		arg2.drawString("UP - W", 100, 100);
		arg2.drawString("DOWN - S", 100, 150);
		arg2.drawString("LEFT - A", 100, 200);
		arg2.drawString("RIGHT - D", 100, 250);
		arg2.drawString("INTERACT - L", 100, 300);
		arg2.drawString("CHARACTER MENU - C", 100, 350);
		arg2.drawString("To change map press 1 2 3 4 5 at the right bottom corner - W", 100, 400);
		arg2.drawString("ESC - Main menu", 100, 450);
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
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
