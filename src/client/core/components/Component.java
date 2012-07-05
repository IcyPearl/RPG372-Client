package client.core.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import client.core.entities.LivingEntity;

/**
 * Components are parts of entities.
 * Entities call update() in order they are added to entity.
 * @author Mefu
 */
public abstract class Component {
	
	protected int id;
	protected LivingEntity owner;
	
	public Component(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public void setOwnerEntity(LivingEntity newOwner){
		this.owner = newOwner;
	}
	
	public abstract void update(GameContainer gc, StateBasedGame sb, int delta);
}
