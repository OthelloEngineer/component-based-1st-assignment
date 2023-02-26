package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.services.ShooterPlugin;

/**
 *
 * @author corfixen
 */
public class Player extends Entity {
    ShooterPlugin shooterPlugin;
    public Player() {
        this.shooterPlugin = new PlayerPlugin();
    }
}