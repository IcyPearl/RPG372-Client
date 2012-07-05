package client.core.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Special render component, which has render method instead of update.
 * @author Mefu
 */

public abstract class RenderComponent extends Component {

	public RenderComponent(int id){
		super(id);
	}

	public abstract void render(GameContainer gc, StateBasedGame sb, Graphics gr);
}
