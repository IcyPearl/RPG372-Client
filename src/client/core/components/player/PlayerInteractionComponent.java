package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;


/**
 * This class will define players interaction behaviors.
 * Example 1 : Player interacts with Mob = Fight
 * Example 2 : Player interacts with Vendor = Trade
 * @author Mefu
 */
public class PlayerInteractionComponent extends Component {

	public PlayerInteractionComponent(int id) {
		super(id);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	}

}
