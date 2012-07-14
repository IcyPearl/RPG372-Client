package client.core.components.player;

import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;
import client.core.entities.item.Item;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;

public class PlayerInventoryRenderComponent extends RenderComponent {

	private boolean draw;
	private Image invbg;
	
	public PlayerInventoryRenderComponent(int id) {
		super(id);
		draw = false;
		try {
			invbg = new Image("client/data/inventory/inv_bg1.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		int invx = 1000;
		int invy = 0;
		if(draw){
			invbg.draw(invx, invy);
			PlayerInventory plinv = ((Player)this.owner).getPD().getInv();
			ListIterator<Item> iterator = plinv.getItems().listIterator();
			while(iterator.hasNext()){
				int[] pos = getItemPos(invx, invy, iterator.nextIndex());
				Item current = iterator.next();
				current.getIcon().draw(pos[0], pos[1]);
			}
		}
	}

	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 4 + 7 * (index%4) + (index%4)*32;
		pos[1] = invy + 2 + 2 * (index/4) + (index/4)*32;
		return pos;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		if(in.isKeyPressed(Input.KEY_I)){
			if(draw)
				draw = false;
			else
				draw = true;
		}
	}

}
