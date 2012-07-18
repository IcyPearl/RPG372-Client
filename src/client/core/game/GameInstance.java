package client.core.game;

import java.util.ArrayList;
import java.util.ListIterator;

import org.newdawn.slick.SlickException;

import client.core.entities.npc.Mob;
import client.core.entities.npc.MobSpawner;
import client.core.entities.npc.Vendor;
import client.core.entities.npc.VendorSpawner;
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
	private ArrayList<Mob> mobs;
	private ArrayList<Vendor> vendors;

	public GameInstance(){
		mobs = new ArrayList<Mob>();
		vendors = new ArrayList<Vendor>();
	}

	public Vendor getNearestVendor(){
		Vendor vend = null;
		int i = currentPlayer.getPosX();
		int j = currentPlayer.getPosY();
		ListIterator<Vendor> iterator = vendors.listIterator();
		while(iterator.hasNext()){
			Vendor current = iterator.next();
			if(isNear(i,j,current.getPosX(),current.getPosY())){
				vend = current;
				break;
			}
		}
		return vend;
	}

	public Mob getNearestMob(){
		Mob mob = null;
		int i = currentPlayer.getPosX();
		int j = currentPlayer.getPosY();
		ListIterator<Mob> iterator = mobs.listIterator();
		while(iterator.hasNext()){
			Mob current = iterator.next();
			if(isNear(i,j,current.getPosX(),current.getPosY())){
				mob = current;
				break;
			}
		}
		return mob;
	}

	private boolean isNear(int i1, int j1, int i2, int j2){
		int idif = Math.abs(i1-i2);
		int jdif = Math.abs(j1-j2);

		if(idif > -1 && idif < 2 && jdif > -1 && jdif < 2)
			return true;
		return false;
	}

	public void spawnMobs() throws SlickException{
		int mapId = this.map.getId();
		while(mobs.size() < 3){
			int random = (int) (Math.random() * 3);
			Mob mob = null;
			if(mapId <= 4){
				mob = MobSpawner.getMob((mapId-1) * 3 + random);
			}else if(mapId == 5)
				mob = MobSpawner.getMob(12);
			mobs.add(mob);
		}
	}

	public void spawnVendor() throws SlickException {
		if(vendors.isEmpty()){
//			int mapId = this.map.getId();
			Vendor vendor = VendorSpawner.getVendor(0);
			vendors.add(vendor);
		}
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
	
	public void clearMobsAndVendors(){
		this.mobs = new ArrayList<Mob>();
		this.vendors = new ArrayList<Vendor>();
	}
}
