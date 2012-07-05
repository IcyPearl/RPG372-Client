package client.core.entities.player;

/**
 * Class for holding static player data.
 * @author Mefu
 */

public class PlayerData {

	private int maxHealth;
	private int maxMana;
	private int level;
	
	private String name;
	
	public PlayerData(){
		
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
