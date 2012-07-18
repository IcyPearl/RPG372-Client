package client.core.entities.item;

/**
 * Super-class for weapon items.
 * @author Mefu
 */
public class Weapon extends Item {

	private int damage;
	
	public Weapon(int id) {
		super(id);
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public Weapon clone(){
		Weapon weapon = super.clone().getWeapon();
		weapon.setDamage(this.damage);
		return weapon;
	}

}
