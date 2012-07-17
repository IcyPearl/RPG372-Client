package client.core.entities.npc;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class MobSpawner {
	
	private static ArrayList<Mob> mobs;
	
	public static void loadMobs() throws SlickException{
		mobs = new ArrayList<Mob>();
		for(int i = 1 ; i <= 13 ; i++){
			mobs.add(ServerConn.getMob(i));
		}
	}
	
	public static Mob getMob(int mobId){
		Mob mob = mobs.get(mobId);
		return mob;
	}
}
