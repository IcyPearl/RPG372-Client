package client.core.entities.player;

import client.core.entities.LivingEntity;
import client.core.entities.item.Armor;
import client.core.entities.item.Inventory;
import client.core.entities.item.Weapon;

public class PlayerInventory extends Inventory {

	private int gold;
	private Armor eqarmor;
	private Weapon eqweapon;
	
	public PlayerInventory(LivingEntity owner) {
		super(owner);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Armor getEqarmor() {
		return eqarmor;
	}

	public void setEqarmor(Armor eqarmor) {
		this.eqarmor = eqarmor;
	}

	public Weapon getEqweapon() {
		return eqweapon;
	}

	public void setEqweapon(Weapon eqweapon) {
		this.eqweapon = eqweapon;
	}

}
