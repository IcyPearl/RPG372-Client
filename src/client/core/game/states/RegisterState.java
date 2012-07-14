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
public class RegisterState extends BasicGameState
{
	private Font awtFont;
	private TrueTypeFont font;
	
	private TextField newUsername;
	private TextField newPassword;
	
	private Color color1;
	private Color color2;

	private int id;
	
	private Image background;
	
	public RegisterState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		background = new Image("client/data/backgrounds/registerbg.jpg");
		
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		
		newUsername = new TextField(arg0, font, 300, 300, 200, 40);
		newPassword = new TextField(arg0, font, 300, 400, 200, 40);
		
		newUsername.setAcceptingInput(true);
		newPassword.setAcceptingInput(true);
		
		color1 = Color.white;
		color2 = Color.white;
	}

	public void render(GameContainer arg0, StateBasedGame arg11, Graphics arg2) throws SlickException
	{
		background.draw(0,0,1366,768);
		newUsername.render(arg0, arg2);
		newPassword.render(arg0, arg2);
		
		font.drawString(100, 200, "REGISTER AREA");
		font.drawString(100, 300, "New Username");
		font.drawString(100, 400, "Password");
		font.drawString(200, 500, "REGISTER" , color1);
		font.drawString(400, 500, "CANCEL" , color2);
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
		color1 = Color.white;
		color2 = Color.white;
		if(mousex > 200 && mousex < 330 && mousey > 500 && mousey < 524) // REGISTER
		{
			color1 =  new Color(247, 173, 36);
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				this.setInput(false);
			}
			//Database iþlemleri
		}
		if(mousex > 400 && mousex < 510 && mousey > 500 && mousey < 524) // CANCEL
		{
			color2 = new Color(247, 173, 36);
			if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				this.setInput(false);
				LoginState login = (LoginState) arg1.getState(RPG372.LOGIN);
				login.setInput(true);
				arg1.enterState(RPG372.LOGIN);
			}
		}
		
		
	}
	
	public void setInput(boolean a)
	{
		newUsername.setAcceptingInput(a);
		newPassword.setAcceptingInput(a);
	}
	public int getID() {
		return id;
	}

}
