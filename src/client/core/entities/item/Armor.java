package client.core.entities.item;

/**
 * Super-class for armor items.
 * @author Mefu
 */
public class Armor extends Item {

	private int defence;
	
	public Armor(int id) {
		super(id);
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

}
