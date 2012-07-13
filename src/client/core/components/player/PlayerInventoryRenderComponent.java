package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;

public class PlayerInventoryRenderComponent extends RenderComponent {

	private boolean draw;
	
	public PlayerInventoryRenderComponent(int id) {
		super(id);
		draw = false;
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if(draw){
			
		}
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		if(in.isKeyDown(Input.KEY_I)){
			if(draw)
				draw = false;
			else
				draw = true;
		}
	}

}
