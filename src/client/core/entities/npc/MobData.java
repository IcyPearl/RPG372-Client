package client.core.entities.npc;


/**
 * A class for static mob data.
 * Example : Mob name is static, but mobs position may change.
 * @author Mefu
 */
public class MobData {
	
	private int level;
	
	private String name;
	private MobInventory inv;
	
	
	public MobData(){
		
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
	
	public int getDropGold(){
		return level * 10;
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

	public MobInventory getInv() {
		return inv;
	}

	public void setInv(MobInventory inv) {
		this.inv = inv;
	}
	
	public MobData clone(){
		MobData md = new MobData();
		md.setInv(inv.clone());
		md.setLevel(level);
		md.setName(name);
		return md;
	}
}
