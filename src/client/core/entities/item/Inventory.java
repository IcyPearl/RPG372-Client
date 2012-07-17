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
		for(int i = 0 ; i < 16 ; i++){
			items.add(null);
		}
	}
	
	public boolean isFull(){
		for(int i = 0 ; i < 16 ; i++){
			if(getItem(i) == null){
				return false;
			}
		}
		return true;
	}
	
	public boolean addItem(Item item){
		int i = 0;
		while(getItem(i) != null){
			i++;
		}
		if(i > 15){
			return false;
		}
		items.set(i, item);
		while(items.size() != 16){
			if(items.size() < 16){
				items.add(null);
			}else{
				items.remove(15);
			}
		}
		return true;
	}
	
	public Item getItem(int n){
		return items.get(n);
	}
	
	public void removeItem(int n){
		items.remove(n);
		items.add(null);
		while(items.size() != 16){
			if(items.size() < 16){
				items.add(null);
			}else{
				items.remove(15);
			}
		}
	}
	
	public void setItem(int n, Item item){
		items.set(n, item);
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}

	public LivingEntity getOwner() {
		return owner;
	}
}
