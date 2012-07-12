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
	
	public Mob(int id, MobData md, int posx, int posy, Image img) {
		super(id);
		this.md = md;
		this.setPosX(posx);
		this.setPosY(posy);
		this.setImage(img);
		this.addComponent(new MobMoveComponent(id));
		this.addComponent(new MobRenderComponent(id));
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

}
