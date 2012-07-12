package client.core.game.states;


import java.awt.Font;

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
	
	private Font awtFont;
	private TrueTypeFont font;
	
	int health1, health2;
	int turn = 0;
	private String actionMessage1="" , actionMessage2="";
	private String turnMessage = "";

	public FightState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{	
		character.draw(100,300);
		mob.draw(100,100);
		
		font.drawString(300, 100, "Jordan");
		font.drawString(300, 150, "Level: " + currentMob.getMd().getLevel());
		font.drawString(300, 200, "Health: "+ health1);
		
		font.drawString(300, 300, currentPlayer.getPD().getName());
		font.drawString(300, 350, "Level: " + currentPlayer.getPD().getLevel());
		font.drawString(300, 400, "Health: " + health2);
		
		font.drawString(500, 100, actionMessage1);
		font.drawString(500, 300, actionMessage2);
		font.drawString(500, 50, turnMessage);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{	
		Input in = arg0.getInput();
		if( health1 <= 0 || health2 <= 0)
		{
			arg1.enterState(RPG372.GAMEPLAY);
		}
		if( turn % 2 == 0)
		{
			turnMessage = "Sira Sende";
			actionMessage1 = "";
			if(in.isKeyDown(Input.KEY_SPACE))
			{
				int dmg = currentPlayer.getPD().getDamage();
				health1 -= dmg;
				actionMessage1 = currentPlayer.getPD().getName() + " Saldirdi, Hasar " + dmg;
				turn++;
				turnMessage = "Sira "+ currentMob.getMd().getName() + "da";
				actionMessage2 = "";
			}
		}
		else
		{
			int dmg = currentMob.getMd().getDamage();
			actionMessage2 = currentMob.getMd().getName() + " Saldirdi, Hasar " + dmg;
			long t0,t1;
	        t0=System.currentTimeMillis();
	        do{
	            t1=System.currentTimeMillis();
	        }
	        while (t1-t0<1000);
			health2 -= dmg;
			turn++;
			
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
	
	public int getID() {
		return id;
	}
}
