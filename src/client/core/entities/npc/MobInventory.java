package client.core.entities.npc;

import java.util.ArrayList;

import client.core.entities.LivingEntity;
import client.core.entities.item.Inventory;
import client.core.entities.item.Item;

public class MobInventory extends Inventory {

	public MobInventory(LivingEntity owner) {
		super(owner);
	}
	
	public MobInventory clone(){
		MobInventory mobinv = new MobInventory(this.getOwner());
		mobinv.setItems(cloneArrayList(this.getItems()));
		return mobinv;
	}
	
	public ArrayList<Item> cloneArrayList(ArrayList<Item> items){
		ArrayList<Item> rtrlist = new ArrayList<Item>();
		for(int i = 0 ; i < items.size() ; i++){
			rtrlist.add(items.get(i));
		}
		return rtrlist;
	}

}
