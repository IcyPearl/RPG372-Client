package client.core.game.states;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.main.RPG372;

@SuppressWarnings("deprecation")
public class LoginState extends BasicGameState{

	private int id;
	
	private Font awtFont;
	private Image background;
	
	private TrueTypeFont font;
	
	private TextField txt;
	private TextField txt2;
	
	private Color color0;
	private Color color1;
	private Color color2;
	
	public LoginState(int id){
		this.id = id;
	}
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		background = new Image("client/data/backgrounds/loginbg.jpg");
		
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		txt = new TextField(arg0, font, 300, 300, 200 , 40);
		txt2 = new TextField(arg0, font, 300, 400, 200, 40);
		
		txt.setAcceptingInput(true);
		txt2.setAcceptingInput(true);
		
		color0 = Color.white;
		color1 = Color.white;
		color2 = Color.white;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		background.draw(0, 0, 1366, 768);
		txt.render(arg0, arg2);
		txt2.render(arg0, arg2);
		
		
		font.drawString(100, 200, "LOGIN AREA");
		font.drawString(100, 300, "Username");
		font.drawString(100, 400, "Password");
		font.drawString(200, 500, "REGISTER" , color0);
		font.drawString(350, 500, "LOGIN" , color1);
		font.drawString(450, 500, "EXIT" , color2);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{
		Input in = arg0.getInput();
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(RPG372.MENU);
		}
		int mousex, mousey;
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		
		color0 = Color.white;
		color1 = Color.white;
		color2 = Color.white;
		
		if(mousex > 200 && mousex < 330 && mousey > 500 && mousey < 524) // REGISTER
		{
			color0 = new Color(29, 55, 169);
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				RegisterState reg = (RegisterState) arg1.getState(RPG372.REGISTER);
				reg.setInput(true);
				this.setInput(false);
				arg1.enterState(RPG372.REGISTER);
			}
		}
		
		if(mousex > 350 && mousex < 430 && mousey > 500 && mousey < 524) // LOGIN
		{
			color1 = new Color(29, 55, 169);
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				this.setInput(false);
				arg1.enterState(RPG372.MENU);
			}
		}
		if(mousex > 450 && mousex < 510 && mousey > 500 && mousey < 524) // EXIT
		{
			color2 = new Color(29, 55, 169);
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				this.setInput(false);
				arg1.enterState(RPG372.MENU);
			}
		}
	}

	public void setInput(boolean a)
	{
		txt.setAcceptingInput(a);
		txt2.setAcceptingInput(a);
	}
	public int getID() {
		return id;
	}

}
