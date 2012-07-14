package client.core.entities.npc;

/**
 * A class for holding static vendor data. (lol)
 * @author Mefu
 */
public class VendorData {
	
	private String name;
	private VendorInventory inv;
	
	public VendorData(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VendorInventory getInv() {
		return inv;
	}

	public void setInv(VendorInventory inv) {
		this.inv = inv;
	}
}
