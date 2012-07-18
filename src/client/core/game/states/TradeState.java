package client.core.game.states;

import java.awt.Font;
import java.util.ListIterator;

import org.newdawn.slick.Color;
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

	private Image invbg,background;

	private Font awtFont;
	private TrueTypeFont font;
	
	private Color colorBuy, colorSell,colorExit;

	private int plinvx, plinvy, vendinvx, vendinvy, midX, midY;


	public TradeState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		invbg = new Image("client/data/inventory/inv_bg1.png");
		background = new Image("client/data/backgrounds/tradebg2.jpg");
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		plinvselected = 0;
		vendinvselected = 0;
		colorBuy = Color.white;
		colorSell = Color.white;
		colorExit = Color.white;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		plinvx = 200;
		plinvy = 200;
		
		vendinvx = 800;
		vendinvy = 200;
		
		background.draw(0,0,1366,768);
		
		invbg.draw(plinvx, plinvy, 2.0f);
		
		
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		ListIterator<Item> iterator = plinv.getItems().listIterator();
		while(iterator.hasNext()){
			int index = iterator.nextIndex();
			int[] pos = getItemPos(plinvx, plinvy, index);
			Item current = iterator.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1],2.0f);
			if(index == plinvselected){
				arg2.drawRect(pos[0], pos[1], 64, 64);
			}
		}
		
		font.drawString(plinvx, plinvy+ invbg.getHeight()*2, "Gold = " + plinv.getGold(),Color.black);
		
		invbg.draw(vendinvx, vendinvy,2.0f);
		
		VendorInventory vendinv = currentVendor.getVd().getInv();
		ListIterator<Item> iterator1 = vendinv.getItems().listIterator();
		while(iterator1.hasNext()){
			int index = iterator1.nextIndex();
			int[] pos = getItemPos(vendinvx, vendinvy, index);
			Item current = iterator1.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1],2.0f);
			if(index == vendinvselected){
				arg2.drawRect(pos[0], pos[1], 64, 64);
			}
		}
		
		font.drawString(vendinvx, vendinvy + invbg.getHeight()*2, "Item Name = " + vendinv.getItem(vendinvselected).getName(),Color.black);
		font.drawString(vendinvx, vendinvy + invbg.getHeight()*2 + 24, "Item Value = " + vendinv.getItem(vendinvselected).getValue(),Color.black);
		

		font.drawString(plinvx, plinvy-50, currentPlayer.getPD().getName(),Color.black);
		font.drawString(vendinvx, vendinvy-50, currentVendor.getVd().getName(),Color.black);

		midX = (vendinvx - 60 + plinvx + invbg.getWidth()*2) / 2;
		midY = vendinvy;

		font.drawString(midX, midY, "BUY", colorBuy);
		font.drawString(midX, midY +50 , "SELL", colorSell);
		font.drawString(midX, midY +100 , "EXIT", colorExit);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.GAMEPLAY);
		}
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		
		colorBuy = Color.black;
		colorSell = Color.black;
		colorExit = Color.black;
		if(mousex > midX && mousex < midX+70 && mousey > midY && mousey < midY+24) // BUY
		{
			colorBuy = Color.blue;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				buy();
			}
		}
		if(mousex > midX && mousex < midX+70 && mousey > midY +50 && mousey < midY + 74) // SELL
		{
			colorSell = Color.blue;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				sell();
			}
		}
		if(mousex > midX && mousex < midX+70 && mousey > midY +100 && mousey < midY + 124) // EXIT
		{
			colorExit = Color.blue;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}
		if(mousex > plinvx && mousex < plinvx + invbg.getWidth()*2 && mousey > plinvy && mousey < plinvy + invbg.getHeight()*2){
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				plinvselected = getSelectedIndex(plinvx, plinvy, mousex, mousey);
			}
		}
		if(mousex > vendinvx && mousex < vendinvx + invbg.getWidth()*2 && mousey > vendinvy && mousey < vendinvy + invbg.getHeight()*2){
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
		int line = (mousey - invy) / (invbg.getHeight()*2/4);
		int lineindex = (mousex - invx) / (invbg.getWidth()*2/4);
		return line*4 + lineindex;
	}

	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 8 + 14 * (index%4) + (index%4)*64;
		pos[1] = invy + 4 + 8 * (index/4) + (index/4)*64;
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
