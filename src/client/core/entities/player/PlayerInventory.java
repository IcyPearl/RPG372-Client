package client.core.entities.player;

import client.core.entities.LivingEntity;
import client.core.entities.item.Inventory;

public class PlayerInventory extends Inventory {

	public PlayerInventory(LivingEntity owner) {
		super(owner);
	}

}
