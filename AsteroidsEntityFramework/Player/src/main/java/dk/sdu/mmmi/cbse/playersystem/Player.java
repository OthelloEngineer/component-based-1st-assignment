package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

/**
 *
 * @author corfixen
 */
public class Player extends Entity {
    float bulletCooldown = 0;

    public void decrementCD(float dt){
        bulletCooldown -= dt;
    }
    public void setBulletCooldown(){
        this.bulletCooldown = 0.5f;
    }
}
