package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;
import client.core.entities.npc.Mob;
import client.core.game.states.FightState;
import client.main.RPG372;


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
		Input in = gc.getInput();
		if(in.isKeyDown(Input.KEY_L))
		{
			Mob mob = RPG372.gameInstance.getNearestMob();
			if(mob != null){
				FightState fs = (FightState)sb.getState(RPG372.FIGHT);
				fs.setMob(mob);
				fs.setCurrentPlayer(RPG372.gameInstance.getCurrentPlayer());
				sb.enterState(RPG372.FIGHT);	
			}
		}
	}

}
