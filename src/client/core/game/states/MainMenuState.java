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

import client.main.RPG372;

/**
 * Main menu state.
 * @author Mefu
 */
@SuppressWarnings("deprecation")
public class MainMenuState extends BasicGameState
{
	private Font awtFont;
	private TrueTypeFont font;

	private Image background;
	private Image box;

	private int id;

	private Color color0;
	private Color color1;
	private Color color2;

	public MainMenuState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		background = new Image("client/data/backgrounds/mainmenubg.jpg");
		box = new Image("client/data/backgrounds/frame8.png");
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);

		color0 = Color.white;
		color1 = Color.white;
		color2 = Color.white;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		background.draw(0,0,1366,768);
		box.draw(80,480,420,220);
		font.drawString(140, 520, "Continue Your Adventure",color0);
		font.drawString(140, 570, "Controls & Options",color1);
		font.drawString(140, 620, "Quit Game",color2);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{
		Input in = arg0.getInput();
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();

		color0 = Color.white;
		color1 = Color.white;
		color2 = Color.white;

		if(mousex > 140 && mousex < 420 && mousey > 520 && mousey < 544) // Continue
		{
			color0 = Color.gray;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}
		if(mousex > 140 && mousex < 360 && mousey > 570 && mousey < 594) // Options & Controls
		{
			color1 = Color.gray;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				//Option state
			}
		}
		if(mousex > 140 && mousex < 270 && mousey > 620 && mousey < 644) // Quit
		{
			color2 = Color.gray;
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				RPG372.exitGame(true);
			}
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
	
	public int getID() {
		return id;
	}

}
