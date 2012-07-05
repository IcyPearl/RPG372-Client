package client.core.entities.player;

import org.newdawn.slick.geom.Vector2f;

import client.core.components.player.PlayerInteractionComponent;
import client.core.components.player.PlayerMoveComponent;
import client.core.components.player.PlayerRenderComponent;
import client.core.entities.LivingEntity;

/**
 * Super-class for player.
 * Can hold both playing player and other players.
 * @author Mefu
 */

public class Player extends LivingEntity {

	private PlayerData pd;
	private boolean activePlayer;

	public Player(int id, PlayerData pd, Vector2f pos, boolean activePlayer) {
		super(id);
		this.pd = pd;
		this.activePlayer = activePlayer;
		this.setPosition(pos);
		if(activePlayer){
			this.addComponent(new PlayerMoveComponent(id));
			this.addComponent(new PlayerInteractionComponent(id));
		}
		this.addComponent(new PlayerRenderComponent(id));
	}

	public boolean isActivePlayer(){
		return activePlayer;
	}
	
	public PlayerData getPD(){
		return pd;
	}

}
