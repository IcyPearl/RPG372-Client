package client.core.components.vendor;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;

public class VendorRenderComponent extends RenderComponent {

	public VendorRenderComponent(int id) {
		super(id);
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		int posx = this.owner.getPosX();
		int posy = this.owner.getPosY();
		this.owner.getImage().draw(posx*50, posy*50, 0.5f);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	}

}
