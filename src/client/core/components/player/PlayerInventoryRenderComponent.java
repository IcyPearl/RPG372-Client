package client.core.components.player;


import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.RenderComponent;
import client.core.entities.item.Armor;
import client.core.entities.item.Item;
import client.core.entities.item.Weapon;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;

public class PlayerInventoryRenderComponent extends RenderComponent {

	private boolean draw;
	private Image invbg;
	private int invx, invy;

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
		invx = 1000;
		invy = 0;
		if(draw){
			invbg.draw(invx, invy);
			PlayerInventory plinv = ((Player)this.owner).getPD().getInv();
			ListIterator<Item> iterator = plinv.getItems().listIterator();
			while(iterator.hasNext()){
				int[] pos = getItemPos(invx, invy, iterator.nextIndex());
				Item current = iterator.next();
				if(current != null)
					current.getIcon().draw(pos[0], pos[1]);
			}
			gr.drawString("Gold = " + plinv.getGold(), invx, invy + invbg.getHeight());
		}
	}

	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 4 + 7 * (index%4) + (index%4)*32;
		pos[1] = invy + 2 + 4 * (index/4) + (index/4)*32;
		return pos;
	}

	private int getSelectedIndex(int invx, int invy, int mousex, int mousey){
		int line = (mousey - invy) / (invbg.getHeight()/4);
		int lineindex = (mousex - invx) / (invbg.getWidth()/4);
		return line*4 + lineindex;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		int mousex = in.getMouseX();
		int mousey = in.getMouseY();
		if(in.isKeyPressed(Input.KEY_I)){
			if(draw)
				draw = false;
			else
				draw = true;
		}
		if(draw){
			if(mousex > invx && mousex < invx + invbg.getWidth() && mousey > invy && mousey < invy + invbg.getHeight()){
				if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){	
					PlayerInventory plinv = ((Player)this.owner).getPD().getInv();
					int selectedIndex = getSelectedIndex(invx, invy, mousex, mousey);
					Item selectedItem = plinv.getItem(selectedIndex);
					if(selectedItem == null){

					}else if(Armor.class.isInstance(selectedItem)){
						if(plinv.getEqarmor() == null){
							plinv.setEqarmor((Armor)selectedItem);
							plinv.removeItem(selectedIndex);
						}else{
							plinv.setItem(selectedIndex, plinv.getEqarmor());
							plinv.setEqarmor((Armor)selectedItem);
						}
					}else if(Weapon.class.isInstance(selectedItem)){
						if(plinv.getEqweapon() == null){
							plinv.setEqweapon((Weapon)selectedItem);
							plinv.removeItem(selectedIndex);
						}else{
							plinv.setItem(selectedIndex, plinv.getEqweapon());
							plinv.setEqweapon((Weapon)selectedItem);
						}
					}
				}
			}
		}
	}
}
