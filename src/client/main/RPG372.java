package client.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * States will be initialized here and game will be started.
 * This should display a black screen. Use it for testing if slick and lwjgl are working.
 * @author Mefu
 */
public class RPG372 extends StateBasedGame {

	public RPG372() {
		super("RPG372");
	}
	
	public static void main(String[] args) throws SlickException{
         AppGameContainer app = new AppGameContainer(new RPG372());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
	
	public void initStatesList(GameContainer arg0) throws SlickException {
		
	}

}
