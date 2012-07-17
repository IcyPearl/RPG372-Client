package client.core.entities.item;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class ItemFactory {


	private static ArrayList<Item> items;
	
	public static void loadItems() throws SlickException {
		items = ServerConn.getItemList();
	}
	
	public static Item getItem(int itemId){
		Item item = items.get(itemId);
		return item;
	}
}
