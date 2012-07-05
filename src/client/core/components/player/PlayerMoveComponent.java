package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;

/**
 * Handles movement input and moves player accordingly.
 * How to synchronize with server is not decided yet.
 * @author Mefu
 */

public class PlayerMoveComponent extends Component{
	
	public PlayerMoveComponent(int id) {
		super(id);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
	
	}
	
}
