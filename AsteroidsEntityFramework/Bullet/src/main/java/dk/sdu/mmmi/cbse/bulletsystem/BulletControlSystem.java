package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.movementFactory.ConstantRandomMovement;
import dk.sdu.mmmi.cbse.common.data.movementFactory.MovementFactory;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author jcs
 */
public class BulletControlSystem implements IEntityProcessingService {
    MovementFactory movementFactory;
    public BulletControlSystem() {
        movementFactory = new ConstantRandomMovement();
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Bullet.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            if(lifePart.isIsHit() || lifePart.getLife() <= 0) {
                handleCollider(world, player);
            }
            lifePart.setLife(lifePart.getLife()-1);
            lifePart.setExpiration(lifePart.getExpiration()-1);
            this.movementFactory.getNewMovement(movingPart);
            lifePart.process(gameData, player);
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            updateShape(player);
        }
    }
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 16);
        shapey[0] = (float) (y + Math.sin(radians) * 16);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private void handleCollider(World world, Entity bullet){
        System.out.println("bullet collided");
        world.removeEntity(bullet);
    }

}
