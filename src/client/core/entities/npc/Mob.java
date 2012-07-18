package client.core.entities.npc;

import org.newdawn.slick.Image;

import client.core.components.mob.MobMoveComponent;
import client.core.components.mob.MobRenderComponent;
import client.core.entities.LivingEntity;

/**
 * Super-class for mobs.
 * @author Mefu
 */

public class Mob extends LivingEntity {

	private MobData md;
	private long moved;
	
	public static final int MOBMOVECOMP = 1;
	public static final int MOBRENDERCOMP = 2;
	
	public Mob(int id, MobData md, Image img){
		super(id);
		this.md = md;
		this.setImage(img);
		this.addComponent(new MobMoveComponent(MOBMOVECOMP));
		this.addComponent(new MobRenderComponent(MOBRENDERCOMP));
		this.setPosX(10);
		this.setPosY(10);
		moved = -1;
	}
	
	public Mob(int id, MobData md, int posx, int posy, Image img) {
		super(id);
		this.md = md;
		this.setPosX(posx);
		this.setPosY(posy);
		this.setImage(img);
		this.addComponent(new MobMoveComponent(MOBMOVECOMP));
		this.addComponent(new MobRenderComponent(MOBRENDERCOMP));
		moved = -1;
	}

	public MobData getMd() {
		return md;
	}

	public long getMoved() {
		return moved;
	}

	public void setMoved(long moved) {
		this.moved = moved;
	}
	
	public Mob clone(){
		Mob mob = new Mob(this.getId(), md, this.getPosX(), this.getPosY(), this.getImage());
		return mob;
	}

}
