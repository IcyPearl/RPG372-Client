package client.core.game.states;


import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import client.core.entities.npc.Mob;
import client.core.entities.player.Player;
import client.main.RPG372;

/**
 * State of a fight between a player and a mob.
 * Should produce a report for synchronizing results with server.
 * @author Mefu
 */

@SuppressWarnings("deprecation")
public class FightState extends BasicGameState {

	private int id;
	
	private Player currentPlayer;
	private Mob currentMob;
	
	private Image character;
	private Image mob;
	private Image background;
	
	private Font awtFont;
	private TrueTypeFont font;
	
	private Color colorAtack;
	private Color colorRetreat;
	
	int health1, health2;
	int turn = 0;
	
	int charx,chary;
	int mobx,moby;
	
	private String actionMessage1="" , actionMessage2="";
	private String turnMessage = "";
	

	public FightState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		background = new Image("client/data/backgrounds/fightbg3.jpg");
		
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		
		font = new TrueTypeFont(awtFont, false);
		
		colorAtack = Color.red;
		colorRetreat = Color.red;
		
		charx = 300;
		chary = 400;
		
		mobx  = 300;
		moby  = 200;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{	
		background.draw(0,0,1366,768);
		character.draw(charx,chary);
		mob.draw(mobx,moby);
		
		font.drawString(500, 200, currentMob.getMd().getName());
		font.drawString(500, 250, "Level: " + currentMob.getMd().getLevel());
		font.drawString(500, 300, "Health: "+ health1);
		
		font.drawString(500, 400, currentPlayer.getPD().getName());
		font.drawString(500, 450, "Level: " + currentPlayer.getPD().getLevel());
		font.drawString(500, 500, "Health: " + health2);
		
		font.drawString(700, 450, "ATACK",colorAtack);
		font.drawString(700, 500, "RETREAT",colorRetreat);
		
		font.drawString(700, 200, actionMessage1);
		font.drawString(700, 400, actionMessage2);
		font.drawString(700, 150, turnMessage);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{	
		Input in = arg0.getInput();
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		
		charx = 300;
		chary = 400;
		
		mobx  = 300;
		moby  = 200;
		
		if( health1 <= 0 || health2 <= 0)
		{
			if(health1 <= 0){
				LootState loot = (LootState) arg1.getState(RPG372.LOOT);
				loot.setPlayer(currentPlayer);
				loot.setMob(currentMob);
				arg1.enterState(RPG372.LOOT);
			}else if(health2 <= 0){
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}
		if( turn % 2 == 0)
		{
			turnMessage = "Sira Sende";
			actionMessage1 = "";
		}
		else
		{
			int dmg = currentMob.getMd().getDamage() - currentPlayer.getPD().getInv().getEqarmor().getDefence();
			actionMessage2 = currentMob.getMd().getName() + " Saldirdi, Hasar " + dmg;
			waitFor(1000);
			health2 -= dmg;
			turn++;
		}
		colorAtack = Color.white;
		colorRetreat = Color.white;
		if(mousex > 700 && mousex < 800 && mousey > 450 && mousey < 474) // ATACK
		{
			colorAtack = Color.red;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON))
			{
				int dmg = currentPlayer.getPD().getDamage() + currentPlayer.getPD().getInv().getEqweapon().getDamage();
				health1 -= dmg;
				actionMessage1 = currentPlayer.getPD().getName() + " Saldirdi, Hasar " + dmg;
				turn++;
				turnMessage = "Sira "+ currentMob.getMd().getName() + "da";
				try {
					animation1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				actionMessage2 = "";
			}
		}
		
		if(mousex > 700 && mousex < 820 && mousey > 500 && mousey < 524) // RETREAT
		{
			colorRetreat = Color.red;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON))
			{
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}
	}
	public void setCurrentPlayer(Player player){
		this.currentPlayer = player;
		character = currentPlayer.getImage();
		health2 = currentPlayer.getPD().getMaxHealth();
		turn = 0;
	}
	public void setMob(Mob mob){
		this.currentMob = mob;
		this.mob = currentMob.getImage();
		health1 = mob.getMd().getMaxHealth();
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}
	public int getID() {
		return id;
	}
	public void animation1() throws InterruptedException
	{
		waitFor(1000);
		charx = 300;
		chary = 300;
		mobx  = 300;
		moby  = 240;
	}
	public void waitFor(long milis)
	{
		long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<milis);
	}
	
}
