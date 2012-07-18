package client.core.components.mob;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;
import client.core.entities.npc.Mob;

public class MobRenderComponent extends RenderComponent {

	public MobRenderComponent(int id) {
		super(id);
	}
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		int posx = this.owner.getPosX()*50;
		int posy = this.owner.getPosY()*50;
		gr.drawString(((Mob)this.owner).getMd().getName(), posx, posy - 20);
		this.owner.getImage().draw(posx, posy, 0.5f);
	}
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	}

}
