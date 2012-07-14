package client.core.game.states;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	
	private TextField txt;
	private TextField txt2;
	
	private Color color1;
	private Color color2;

	private int id;
	
	public RegisterState(int id){
		this.id = id;
	}

	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		txt = new TextField(arg0, font, 300, 300, 200, 40);
		txt2 = new TextField(arg0, font, 300, 400, 200, 40);
		
		
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		txt.render(arg0, arg2);
		txt2.render(arg0, arg2);
		
		font.drawString(100, 200, "KAYIT EKRANI");
		font.drawString(100, 300, "Kullanici Adi");
		font.drawString(100, 400, "Sifre");
		font.drawString(200, 500, "ONAYLA" , color1);
		font.drawString(400, 500, "IPTAL" , color2);
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
		if(mousex > 200 && mousex < 300 && mousey > 500 && mousey < 524) // ONAYLA
		{
			color1 = Color.red;
			//Database iþlemleri
		}
		if(mousex > 400 && mousex < 480 && mousey > 500 && mousey < 524) // Iptal
		{
			color2 = Color.red;
			if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				arg1.enterState(RPG372.MENU);
			}
		}
		
		
	}
	
	
	public int getID() {
		return id;
	}

}
