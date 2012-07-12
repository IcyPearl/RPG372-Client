package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;
import client.main.RPG372;

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
		Input in = gc.getInput();
		int bposx = this.owner.getPosX();
		int bposy = this.owner.getPosY(); 
		int nposx = bposx;
		int nposy = bposy;
		int limx = RPG372.gameInstance.getMap().getX();
		int limy = RPG372.gameInstance.getMap().getY();
		if(in.isKeyPressed(Input.KEY_A)){
			nposx -= 1;
			if(nposx < 0)
				nposx = 0;
		}else if(in.isKeyPressed(Input.KEY_D)){
			nposx += 1;
			if(nposx >= limx)
				nposx = limx - 1;
		}else if(in.isKeyPressed(Input.KEY_W)){
			nposy -= 1; 
			if(nposy < 0)
				nposy = 0;
		}else if(in.isKeyPressed(Input.KEY_S)){
			nposy += 1;
			if(nposy >= limy)
				nposy = limy - 1;
		} 
		this.owner.setPosX(nposx);
		this.owner.setPosY(nposy);
	}

}
