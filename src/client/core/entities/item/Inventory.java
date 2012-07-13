package client.core.entities.item;

import java.util.ArrayList;

import client.core.entities.LivingEntity;

/**
 * Will be a collection of items.
 * PlayerInventory and VendorInventory is going to extend this because
 * a Player may wear some items.
 * @author Mefu
 */
public class Inventory {

	private LivingEntity owner;
	
	private ArrayList<Item> items;
	
	public Inventory(LivingEntity owner){
		this.owner = owner;
		items = new ArrayList<Item>();
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}

	public LivingEntity getOwner() {
		return owner;
	}
}
