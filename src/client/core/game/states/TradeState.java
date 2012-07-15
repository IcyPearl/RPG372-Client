package client.core.game.states;

import java.awt.Font;
import java.util.ListIterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
@SuppressWarnings("deprecation")
public class TradeState extends BasicGameState {

	private int id;

	private Player currentPlayer;
	private Vendor currentVendor;

	private int plinvselected;
	private int vendinvselected;

	private Image invbg;

	private Font awtFont;
	private TrueTypeFont font;

	private int plinvx, plinvy, vendinvx, vendinvy, midX, midY;


	public TradeState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		invbg = new Image("client/data/inventory/inv_bg1.png");
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		plinvselected = 0;
		vendinvselected = 0;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		plinvx = 100;
		plinvy = 100;
		vendinvx = 600;
		vendinvy = 100;

		invbg.draw(plinvx, plinvy);
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		ListIterator<Item> iterator = plinv.getItems().listIterator();
		while(iterator.hasNext()){
			int index = iterator.nextIndex();
			int[] pos = getItemPos(plinvx, plinvy, index);
			Item current = iterator.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1]);
			if(index == plinvselected){
				arg2.drawRect(pos[0], pos[1], 32, 32);
			}
		}
		arg2.drawString("Gold = " + plinv.getGold(), plinvx, plinvy + invbg.getHeight());
		invbg.draw(vendinvx, vendinvy);
		VendorInventory vendinv = currentVendor.getVd().getInv();
		ListIterator<Item> iterator1 = vendinv.getItems().listIterator();
		while(iterator1.hasNext()){
			int index = iterator1.nextIndex();
			int[] pos = getItemPos(vendinvx, vendinvy, index);
			Item current = iterator1.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1]);
			if(index == vendinvselected){
				arg2.drawRect(pos[0], pos[1], 32, 32);
			}
		}

		font.drawString(plinvx, plinvy-50, currentPlayer.getPD().getName());
		font.drawString(vendinvx, vendinvy-50, currentVendor.getVd().getName());

		midX = (vendinvx + plinvx) / 2;
		midY = vendinvy;

		font.drawString(midX, midY, "BUY");
		font.drawString(midX, midY + invbg.getHeight()/2 , "SELL");
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.GAMEPLAY);
		}
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		if(mousex > midX && mousex < midX+30 && mousey > midY && mousey < midY+24) // ONAYLA
		{
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				buy();
			}
		}
		if(mousex > midX && mousex < midX+40 && mousey > midY + invbg.getHeight()/2 && mousey < midY + invbg.getHeight()/2 + 24) // Iptal
		{
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				sell();
			}
		}
		if(mousex > plinvx && mousex < plinvx + invbg.getWidth() && mousey > plinvy && mousey < plinvy + invbg.getHeight()){
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				plinvselected = getSelectedIndex(plinvx, plinvy, mousex, mousey);
			}
		}
		if(mousex > vendinvx && mousex < vendinvx + invbg.getWidth() && mousey > vendinvy && mousey < vendinvy + invbg.getHeight()){
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				vendinvselected = getSelectedIndex(vendinvx, vendinvy, mousex, mousey);
			}
		}
	}

	public int getID() {
		return id;
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	private int getSelectedIndex(int invx, int invy, int mousex, int mousey){
		int line = (mousey - invy) / (invbg.getHeight()/4);
		int lineindex = (mousex - invx) / (invbg.getWidth()/4);
		return line*4 + lineindex;
	}

	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 4 + 7 * (index%4) + (index%4)*32;
		pos[1] = invy + 2 + 4 * (index/4) + (index/4)*32;
		return pos;
	}

	public void buy(){
		Item toTrade = currentVendor.getVd().getInv().getItem(vendinvselected);
		if(toTrade == null){
			return;
		}
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		if(plinv.isFull()){
			return;
		}
		if(plinv.getGold() >= toTrade.getValue()){
			if(plinv.addItem(toTrade))
				plinv.setGold(plinv.getGold() - toTrade.getValue());
		}
	}

	public void sell(){
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		Item toTrade = plinv.getItem(plinvselected);
		if(toTrade == null){
			return;
		}
		plinv.setGold(plinv.getGold() + toTrade.getValue());
		plinv.removeItem(plinvselected);
	}

	public void setPlayer(Player player){
		this.currentPlayer = player;
	}

	public void setVendor(Vendor vendor){
		this.currentVendor = vendor;
	}
}
