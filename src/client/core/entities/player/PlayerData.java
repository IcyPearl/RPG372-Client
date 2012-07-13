package client.core.entities.player;

/**
 * Class for holding static player data.
 * @author Mefu
 */

public class PlayerData {
	
	private int level;
	private String name;
	
	private PlayerInventory inv;
	
	public PlayerData(){
		
	}

	public int getDamage(){
		int dmg = 0;
		int rand = (int) (Math.random() * 100);
		if(rand < 50){
			dmg = level;
		}else if(rand < 60){
			dmg = (int) (level * 1.2);
		}else if(rand < 70){
			dmg = (int) (level * 1.4);
		}else if(rand < 80){
			dmg = (int) (level * 1.6);
		}else if(rand < 90){
			dmg = (int) (level * 1.8);
		}else if(rand < 96){
			dmg = (int) (level * 2.0);
		}else if(rand < 100){
			dmg = (int) (level * 4.0);
		}	
		return dmg;
	}
	
	public int getMaxHealth() {
		return level * 10;
	}

	public int getMaxMana() {
		return level * 50;
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

	public PlayerInventory getInv() {
		return inv;
	}

	public void setInv(PlayerInventory inv) {
		this.inv = inv;
	}
	
}
