package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    public PlayerControlSystem() {
    }

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            if(lifePart.isIsHit()) {
                handleCollider(world, player);
            }
            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            lifePart.process(gameData, player);
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            if(gameData.getKeys().isDown(SPACE) && ((Player) player).bulletCooldown <0){
                shoot(player, world);
            }
            ((Player) player).decrementCD(gameData.getDelta());
            updateShape(player);
        }
    }
    private void shoot(Entity entity, World world){
        Bullet bullet = new Bullet();
        bullet.add(new MovingPart(1, 500, 500, 0));
        ((Player) entity).setBulletCooldown();
        float outX = entity.getShapeX()[0];
        float outy = entity.getShapeY()[0];
        PositionPart part = entity.getPart(PositionPart.class);
        bullet.add(new PositionPart(outX, outy, part.getRadians()));
        bullet.add(new LifePart(100,1));
        world.addEntity(bullet);
    }
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private void handleCollider(World world, Entity player){
        world.removeEntity(player);
    }

}
