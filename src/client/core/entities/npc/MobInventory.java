package client.core.entities.npc;

import client.core.entities.LivingEntity;
import client.core.entities.item.Inventory;

public class MobInventory extends Inventory {

	public MobInventory(LivingEntity owner) {
		super(owner);
	}

}
