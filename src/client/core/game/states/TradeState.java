package client.core.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State for trade between player and vendor.
 * @author Mefu
 */

public class TradeState extends BasicGameState {

	private int id;

	public TradeState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

	}

	public int getID() {
		return id;
	}

}
