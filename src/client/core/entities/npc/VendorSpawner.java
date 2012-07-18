package client.core.entities.npc;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import client.core.serverconn.ServerConn;

public class VendorSpawner {

	private static ArrayList<Vendor> vendors;
	
	public static void loadVendors() throws SlickException {
		vendors = ServerConn.getVendorList();
	}
	
	
	public static Vendor getVendor(int vendorId){
		Vendor vendor = vendors.get(vendorId);
		return vendor;
	}
}
