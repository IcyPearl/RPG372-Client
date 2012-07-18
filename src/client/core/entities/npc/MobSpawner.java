package client.core.entities.npc;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class MobSpawner {
	
	private static ArrayList<Mob> mobs;
	
	public static void loadMobs() throws SlickException{
		mobs = ServerConn.getMobList();
	}
	
	public static Mob getMob(int mobId){
		Mob mob = mobs.get(mobId);
		return mob.clone();
	}
}
