package client.core.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.main.RPG372;

/**
 * Main menu state.
 * @author Mefu
 */
public class MainMenuState extends BasicGameState {

	private int id;
	
	private Image menubg;
	private Image start;
	private Image exit;
	
	private float startScale = 1f;
	private float exitScale = 1f;
	
	public MainMenuState(int id){
		this.id = id;
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		menubg = new Image("client/data/menubg.png");
		start = new Image("client/data/start.png");
		exit = new Image("client/data/exit.png");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		menubg.draw(0,0);
		start.draw(200,200,startScale);
		exit.draw(200,350,exitScale);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		int mousex, mousey;
		Input in = arg0.getInput();
		mousex = in.getMouseX();
		mousey = in.getMouseY();
		if(mousex > 200 && mousey > 200 && mousey < 350){
			if(startScale < 1.5f)
				startScale += 0.01f;
			if(exitScale > 1.0f){
				exitScale -= 0.01f;
			}
			if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				arg1.enterState(RPG372.GAMEPLAY);
			}
		}else if(mousex > 200 && mousey > 350 && mousey < 500){
			if(exitScale < 1.5f)
				exitScale += 0.01f;
			if(startScale > 1.0f){
				startScale -= 0.01f;
			}
			if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				System.exit(0);
			}
		}else{
			if(startScale > 1.0f){
				startScale -= 0.01f;
			}
			if(exitScale > 1.0f){
				exitScale -= 0.01f;
			}
		}
	}

	public int getID() {
		return id;
	}

}
