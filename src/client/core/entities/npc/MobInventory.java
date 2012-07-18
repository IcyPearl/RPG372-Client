package client.core.entities.npc;

import client.core.entities.LivingEntity;
import client.core.entities.item.Inventory;

public class MobInventory extends Inventory {

	public MobInventory(LivingEntity owner) {
		super(owner);
	}
	
	public MobInventory clone(){
		MobInventory mobinv = new MobInventory(this.getOwner());
		mobinv.setItems(this.getItems());
		return mobinv;
	}

}
