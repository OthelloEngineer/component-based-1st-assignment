package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IRunTimeInstantiator;

public class BulletPlugin implements IRunTimeInstantiator {

    public BulletPlugin() {
    }

    @Override
    public Entity createEntity(PositionPart positionPart) {
        Bullet newBullet = new Bullet();
        newBullet.add(new MovingPart(1, 1500, 1500, 0));
        newBullet.add(new LifePart(100,20));
        newBullet.add(positionPart);
        return newBullet;
    }
}
