package client.core.components.mob;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import client.core.components.Component;
import client.core.entities.npc.Mob;
import client.main.RPG372;

public class MobMoveComponent extends Component {

	public MobMoveComponent(int id) {
		super(id);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		int bposx = this.owner.getPosX();
		int bposy = this.owner.getPosY();

		int nposx = bposx;
		int nposy = bposy;
		
		int limx = RPG372.gameInstance.getMap().getX();
		int limy = RPG372.gameInstance.getMap().getY();

		int random = (int) (Math.random() * 4);

		switch(random){
		case 0:
			nposy -= 1;
			if(nposy < 0)
				nposy = 0;
			break;
		case 1:
			nposy += 1;
			if(nposy >= limy)
				nposy = limy - 1;
			break;
		case 2:
			nposx -= 1;
			if(nposx < 0)
				nposx = 0;
			break;
		case 3:
			nposx += 1;
			if(nposx >= limx)
				nposx = limx - 1;
			break;
		default:
			break;
		}
		if(RPG372.gameInstance.getMap().checkCollision(nposx, nposy)){
			Mob mob = (Mob) this.owner;
			if(mob.getMoved() == -1){
				mob.setMoved(System.currentTimeMillis());
			}else{
				if(System.currentTimeMillis() - mob.getMoved() < 1000){
					//yavaaas
				}else{
					this.owner.setPosX(nposx);
					this.owner.setPosY(nposy);
					mob.setMoved(System.currentTimeMillis());
				}
			}
		}
	}

}
