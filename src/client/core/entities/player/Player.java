package client.core.entities.player;

import org.newdawn.slick.Image;

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

	public Player(int id, PlayerData pd, int posx, int posy, Image img, boolean activePlayer) {
		super(id);
		this.pd = pd;
		this.activePlayer = activePlayer;
		this.setImage(img);
		this.setPosX(posx);
		this.setPosY(posy);
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
