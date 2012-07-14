package client.core.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;
import client.core.components.RenderComponent;

/**
 * A living entity which has its own components. When called update this class redirects
 * this call to its components.
 * A lot of things are subject to change.
 * @author Mefu
 */

public class LivingEntity {

	private int id;

	private int posx;
	private int posy;
	private float scale;
	private float rotation;

	private Image img;

	ArrayList<RenderComponent> renderComponents = null;

	ArrayList<Component> components = null;

	public LivingEntity(int id){
		this.id = id;

		components = new ArrayList<Component>();
		renderComponents = new ArrayList<RenderComponent>();

		posx = 0;
		posy = 0;
		scale = 1.0f;
		rotation = 0.0f;
	}

	public Image getImage(){
		return img;
	}

	public void setImage(Image img){
		this.img = img;
	}

	public void addComponent(Component component){
		if(RenderComponent.class.isInstance(component))
			renderComponents.add((RenderComponent) component);
		component.setOwnerEntity(this);
		components.add(component);
	}

	public Component getComponent(int id){
		for(Component comp : components){
			if ( comp.getID() == id )
				return comp;
		}
		return null;
	}

	public int getPosX() {
		return posx;
	}

	public void setPosX(int posx) {
		this.posx = posx;
	}

	public int getPosY() {
		return posy;
	}

	public void setPosY(int posy) {
		this.posy = posy;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public int getId() {
		return id;
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta){
		for(Component component : components){
			component.update(gc, sb, delta);
		}
	}

	public void render(GameContainer gc, StateBasedGame sb, Graphics gr){
		if(renderComponents != null)
			for(RenderComponent renderComponent : renderComponents){
				renderComponent.render(gc, sb, gr);
			}
	}

}
