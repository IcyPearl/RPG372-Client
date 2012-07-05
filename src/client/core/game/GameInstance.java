package client.core.game;

import java.util.ArrayList;

import client.core.entities.npc.Mob;
import client.core.entities.npc.Vendor;
import client.core.entities.player.Player;
import client.core.map.Map;

/**
 * An instance of game, which renderer side of client is going to render.
 * Every change from client and server should finally be reflected here.
 * @author Mefu
 *
 */

public class GameInstance {
	
	private Map map;
	private Player currentPlayer;
	private ArrayList<Player> otherPlayers;
	private ArrayList<Mob> mobs;
	private ArrayList<Vendor> vendors;
	
	public GameInstance(){
		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ArrayList<Player> getOtherPlayers() {
		return otherPlayers;
	}

	public void addOtherPlayer(Player player){
		otherPlayers.add(player);
	}
	
	public void setOtherPlayers(ArrayList<Player> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}
	
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	public void addMob(Mob mob){
		mobs.add(mob);
	}

	public void setMobs(ArrayList<Mob> mobs) {
		this.mobs = mobs;
	}
	
	public ArrayList<Vendor> getVendors() {
		return vendors;
	}
	

	public void addVendor(Vendor vendor){
		vendors.add(vendor);
	}

	public void setVendors(ArrayList<Vendor> vendors) {
		this.vendors = vendors;
	}
	
}
