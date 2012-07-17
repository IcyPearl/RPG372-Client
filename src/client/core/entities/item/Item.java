package client.core.entities.item;

import org.newdawn.slick.Image;

/**
 * Name is self-explanatory.
 * @author Mefu
 */
public class Item {
	
	private Image icon;
	private int id;
	private int value;
	
	private String name;
	
	
	public Item(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Armor getArmor(){
		Armor armor = new Armor(id);
		armor.setName(this.name);
		armor.setValue(this.value);
		armor.setIcon(this.icon);
		return armor;
	}
	
	public Weapon getWeapon(){
		Weapon weapon = new Weapon(id);
		weapon.setName(this.name);
		weapon.setValue(this.value);
		weapon.setIcon(this.icon);
		return weapon;
	}
	
}
