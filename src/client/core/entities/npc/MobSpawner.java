package client.core.entities.npc;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class MobSpawner {

	public MobSpawner(){
		
	}
	
	public Mob getMob(int id){
		Mob mob = null;
		try {
			ServerConn.getMobData(id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return mob;
	}
}
