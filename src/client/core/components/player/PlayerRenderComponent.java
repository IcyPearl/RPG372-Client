package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;

/**
 * Renders player with its animations according to current state of player.
 * @author Mefu
 *
 */
public class PlayerRenderComponent extends RenderComponent {

	public PlayerRenderComponent(int id) {
		super(id);
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		this.owner.getImage().draw(this.owner.getPosX() * 50, this.owner.getPosY() * 50, 0.5f);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	}

}
