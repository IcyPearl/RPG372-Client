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

	private Font awtFont, awtFont2;
	private TrueTypeFont font, font2;

	int health1, health2;
	int turn = 0;
	private String actionMessage1="" , actionMessage2="";
	private String turnMessage = "";

	private String atackString1 = "";
	private String atackString2 = "";

	private Color color1,color2;

	public FightState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		awtFont2 = new Font("Times New Roman", Font.BOLD, 60);

		font = new TrueTypeFont(awtFont, false);
		font2 = new TrueTypeFont(awtFont2, false);

		color1 = Color.red;
		color2 = Color.red;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{	
		character.draw(100,300);
		mob.draw(100,100);

		font2.drawString(130,300, atackString1,color1);
		font2.drawString(130,100, atackString2,color2);

		font.drawString(300, 100, currentMob.getMd().getName());
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
			if( turn == 0)
			{
				atackString1 = "";
			}
			else
			{
				atackString1 = "X";				
			}
			if(in.isKeyPressed(Input.KEY_SPACE))
			{
				atackString1 = "";
				int dmg = currentPlayer.getPD().getDamage();
				health1 -= dmg;
				actionMessage1 = currentPlayer.getPD().getName() + " Saldirdi, Hasar " + dmg;
				turn++;
				turnMessage = "Sira "+ currentMob.getMd().getName() + "da";
				actionMessage2 = "";
				atackString2 = "X";
			}
		}
		else
		{
			int dmg = currentMob.getMd().getDamage();
			atackString2 = "";
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
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
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
