package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.events.ShooterController;

/**
 *
 * @author corfixen
 */
public class Player extends Entity {
    ShooterController shooterPlugin;
    public Player() {
        this.shooterPlugin = new PlayerController();
    }
}