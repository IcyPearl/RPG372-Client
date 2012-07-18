package client.core.entities.npc;

import org.newdawn.slick.Image;

import client.core.components.vendor.VendorRenderComponent;
import client.core.entities.LivingEntity;

/**
 * Super-class for vendors.
 * @author Mefu
 */

public class Vendor extends LivingEntity {

	private VendorData vd;
	
	public static final int VENDORRENDCOMP = 1;
	
	public Vendor(int id, VendorData vd, int posx, int posy, Image img) {
		super(id);
		this.vd = vd;
		this.setPosX(posx);
		this.setPosY(posy);
		this.setImage(img);
		this.addComponent(new VendorRenderComponent(VENDORRENDCOMP));
	}
	
	public Vendor(int id, VendorData vd, Image img) {
		super(id);
		this.vd = vd;
		this.setImage(img);
		this.addComponent(new VendorRenderComponent(VENDORRENDCOMP));
	}

	public VendorData getVd() {
		return vd;
	}

}
