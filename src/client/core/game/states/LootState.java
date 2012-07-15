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
import client.core.entities.npc.Mob;
import client.core.entities.npc.MobInventory;
import client.core.entities.player.Player;
import client.core.entities.player.PlayerInventory;
import client.main.RPG372;

@SuppressWarnings("deprecation")
public class LootState extends BasicGameState {

	private Player currentPlayer;
	private Mob currentMob;
	
	private Image background;
	
	private Font awtFont;

	private TrueTypeFont font;
	
	
	private int mobinvselected;
	private int plinvx, plinvy, mobinvy, mobinvx, midx, midy;

	
	private Color colorLoot, colorExit;
	
	private Image invbg;
	
	private int id;

	public LootState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		invbg = new Image("client/data/inventory/inv_bg1.png");
		background = new Image("client/data/backgrounds/lootbg2.jpg");
		awtFont = new Font("Times New Roman", Font.BOLD, 24);		
		font = new TrueTypeFont(awtFont, false);
		
		mobinvselected = 0;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		plinvx = 200;
		plinvy = 200;
		
		mobinvx = 800;
		mobinvy = 200;
		
		background.draw(0,0,1366,768);
		invbg.draw(plinvx, plinvy, 2.0f);

		
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		ListIterator<Item> iterator = plinv.getItems().listIterator();
		while(iterator.hasNext()){
			int index = iterator.nextIndex();
			int[] pos = getItemPos(plinvx, plinvy, index);
			Item current = iterator.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1], 2.0f);
		}
		
		font.drawString(plinvx, plinvy+ invbg.getHeight()*2, "Gold = " + plinv.getGold());
		
		invbg.draw(mobinvx, mobinvy,2.0f);
		MobInventory mobinv = currentMob.getMd().getInv();
		ListIterator<Item> iterator1 = mobinv.getItems().listIterator();
		while(iterator1.hasNext()){
			int index = iterator1.nextIndex();
			int[] pos = getItemPos(mobinvx, mobinvy, index);
			Item current = iterator1.next();
			if(current != null)
				current.getIcon().draw(pos[0], pos[1], 2.0f);
			if(index == mobinvselected){
				arg2.drawRect(pos[0], pos[1], 64, 64);
			}
		}
		
		font.drawString(plinvx, plinvy-50, currentPlayer.getPD().getName());
		font.drawString(mobinvx, mobinvy-50, currentMob.getMd().getName());
		
		midx = (mobinvx - 60 + plinvx + invbg.getWidth()*2) / 2;
		midy = mobinvy;
		
		font.drawString(midx , midy, "LOOT",colorLoot);
		
		font.drawString(midx , midy+50, "EXIT",colorExit);
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.GAMEPLAY);
		}
		colorLoot = Color.white;
		colorExit = Color.white;
		
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		
		if(mousex > midx && mousex < midx + 80 && mousey > midy && mousey < midy+24)
		{
			colorLoot = Color.red;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				loot();
			}
		}
		if(mousex > midx && mousex < midx + 80 && mousey > midy+50 && mousey < midy+74)
		{
			colorExit = Color.red;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}
		if(mousex > mobinvx && mousex < mobinvx + invbg.getWidth()*2 && mousey > mobinvy && mousey < mobinvy + invbg.getHeight()*2){
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				mobinvselected = getSelectedIndex(mobinvx, mobinvy, mousex, mousey);
			}
		}
	}

	private int getSelectedIndex(int invx, int invy, int mousex, int mousey){
		int line = (mousey - invy) / (invbg.getHeight()*2/4);
		int lineindex = (mousex - invx) / (invbg.getWidth()*2/4);
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
		MobInventory mobinv = currentMob.getMd().getInv();
		Item toTrade = mobinv.getItem(mobinvselected);
		if(toTrade == null){
			return;
		}
		PlayerInventory plinv = currentPlayer.getPD().getInv();
		if(plinv.isFull()){
			return;
		}
		plinv.addItem(toTrade);
		mobinv.removeItem(mobinvselected);
	}
	
	private int[] getItemPos(int invx, int invy, int index) {
		int[] pos = new int[2];
		pos[0] = invx + 8 + 14 * (index%4) + (index%4)*64;
		pos[1] = invy + 4 + 8 * (index/4) + (index/4)*64;
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
