package client.core.components.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;

public class PlayerEquipmentRenderComponent extends RenderComponent {

	private boolean draw;
	private Image invbg;

	public PlayerEquipmentRenderComponent(int id) {
		super(id);
		draw = false;
		try {
			invbg = new Image("client/data/inventory/inv_bg2.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		int invx = 1000;
		int invy = 200;
		if(draw){
			invbg.draw(invx, invy);
			PlayerInventory plinv = ((Player)this.owner).getPD().getInv();
			if(plinv.getEqweapon() != null){
				plinv.getEqweapon().getIcon().draw(invx + 4, invy + 2);
			}
			if(plinv.getEqarmor() != null){
				plinv.getEqarmor().getIcon().draw(invx + 43, invy + 2);
			}
			gr.drawString("EXP = " + ((Player)this.owner).getPD().getExp(), invx, invy + 50);
		}
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		if(in.isKeyPressed(Input.KEY_C)){
			if(draw)
				draw = false;
			else
				draw = true;
		}
	}

	

}
