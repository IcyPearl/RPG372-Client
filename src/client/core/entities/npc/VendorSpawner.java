package client.core.entities.npc;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class VendorSpawner {

	private static ArrayList<Vendor> vendors;
	
	public static void loadVendors() throws SlickException {
		vendors = new ArrayList<Vendor>();
		for(int i = 1 ; i <= 5 ; i++){
			vendors.add(ServerConn.getVendor(i));
		}
	}
	
	
	public static Vendor getVendor(int vendorId){
		Vendor vendor = vendors.get(vendorId);
		return vendor;
	}
}
