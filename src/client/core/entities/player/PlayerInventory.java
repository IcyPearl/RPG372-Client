package client.core.entities.player;

import client.core.entities.LivingEntity;
import client.core.entities.item.Inventory;

public class PlayerInventory extends Inventory {

	private int gold;
	
	public PlayerInventory(LivingEntity owner) {
		super(owner);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

}
