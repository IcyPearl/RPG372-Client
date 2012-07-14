package client.core.game.states;

import java.awt.Font;
import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.item.Item;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;
import client.main.RPG372;

/**
 * State for trade between player and vendor.
 * @author Mefu
 */

public class TradeState extends BasicGameState {

	private int id;

	private Player currentPlayer;
	private Vendor currentVendor;
	
	private Image invbg;
	
	private Font awtFont;
	private TrueTypeFont font;
	

	public TradeState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		invbg = new Image("client/data/inventory/inv_bg1.png");
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}

	@SuppressWarnings("deprecation")
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		int plinvx = 100;
		int plinvy = 100;
		int vendinvx = 600;
		int vendinvy = 100;
		invbg.draw(plinvx, plinvy);
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		ListIterator<Item> iterator = plinv.getItems().listIterator();
		while(iterator.hasNext()){
			int[] pos = getItemPos(plinvx, plinvy, iterator.nextIndex());
			Item current = iterator.next();
			current.getIcon().draw(pos[0], pos[1]);
		}
		invbg.draw(vendinvx, vendinvy);
		VendorInventory vendinv = currentVendor.getVd().getInv();
		ListIterator<Item> iterator1 = vendinv.getItems().listIterator();
		while(iterator1.hasNext()){
			int[] pos = getItemPos(vendinvx, vendinvy, iterator1.nextIndex());
			Item current = iterator1.next();
			current.getIcon().draw(pos[0], pos[1]);
		}
		font.drawString(plinvx, plinvy-50, currentPlayer.getPD().getName());
		font.drawString(vendinvx, vendinvy-50, currentVendor.getVd().getName());
		
		int midX = (vendinvx + plinvx) / 2;
		int midY = vendinvy;
		
		font.drawString(midX, midY, "BUY");
		font.drawString(midX, midY + invbg.getHeight()/2 , "SELL");
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.GAMEPLAY);
		}
	}

	public int getID() {
		return id;
	}

	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 4 + 8 * (index) + (index%4)*32;
		pos[1] = invy + 2 + 2 * (index) + (index/4)*32;
		return pos;
	}
	
	public void setPlayer(Player player){
		this.currentPlayer = player;
	}
	
	public void setVendor(Vendor vendor){
		this.currentVendor = vendor;
	}
}
