package client.core.game.states;

import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.item.Item;
import client.core.entities.npc.Mob;
import client.core.entities.npc.MobInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;
import client.main.RPG372;

public class LootState extends BasicGameState {

	private Player currentPlayer;
	private Mob currentMob;
	
	private int mobinvselected;
	private int plinvx, plinvy, mobinvy, mobinvx, midx, midy;
	
	private Image invbg;
	
	private int id;

	public LootState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		invbg = new Image("client/data/inventory/inv_bg1.png");
		mobinvselected = 0;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		plinvx = 100;
		plinvy = 100;
		mobinvx = 600;
		mobinvy = 100;

		invbg.draw(plinvx, plinvy);
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		ListIterator<Item> iterator = plinv.getItems().listIterator();
		while(iterator.hasNext()){
			int index = iterator.nextIndex();
			int[] pos = getItemPos(plinvx, plinvy, index);
			Item current = iterator.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1]);
		}
		arg2.drawString("Gold = " + plinv.getGold(), plinvx, plinvy + invbg.getHeight());
		invbg.draw(mobinvx, mobinvy);
		MobInventory mobinv = currentMob.getMd().getInv();
		ListIterator<Item> iterator1 = mobinv.getItems().listIterator();
		while(iterator1.hasNext()){
			int index = iterator1.nextIndex();
			int[] pos = getItemPos(mobinvx, mobinvy, index);
			Item current = iterator1.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1]);
			if(index == mobinvselected){
				arg2.drawRect(pos[0], pos[1], 32, 32);
			}
		}
		
		arg2.drawString(currentPlayer.getPD().getName(),plinvx, plinvy-50);
		arg2.drawString(currentMob.getMd().getName(),mobinvx, mobinvy-50);
		
		midx = (mobinvx + plinvx) / 2;
		midy = mobinvy;
		
		arg2.drawString("LOOT", midx, midy);

	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.GAMEPLAY);
		}
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		if(mousex > midx && mousex < midx+30 && mousey > midy && mousey < midy+24) // ONAYLA
		{
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				loot();
			}
		}
		if(mousex > mobinvx && mousex < mobinvx + invbg.getWidth() && mousey > mobinvy && mousey < mobinvy + invbg.getHeight()){
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				mobinvselected = getSelectedIndex(mobinvx, mobinvy, mousex, mousey);
			}
		}
	}

	private int getSelectedIndex(int invx, int invy, int mousex, int mousey){
		int line = (mousey - invy) / (invbg.getHeight()/4);
		int lineindex = (mousex - invx) / (invbg.getWidth()/4);
		return line*4 + lineindex;
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}
	
	public void loot(){
		Item toTrade = currentMob.getMd().getInv().getItem(mobinvselected);
		if(toTrade == null){
			return;
		}
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		if(plinv.isFull()){
			return;
		}
		plinv.addItem(toTrade);
	}
	
	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 4 + 7 * (index%4) + (index%4)*32;
		pos[1] = invy + 2 + 4 * (index/4) + (index/4)*32;
		return pos;
	}
	
	public void setPlayer(Player player){
		this.currentPlayer = player;
	}
	
	public void setMob(Mob mob){
		this.currentMob = mob;
	}
	
	public int getID() {
		return id;
	}
}
