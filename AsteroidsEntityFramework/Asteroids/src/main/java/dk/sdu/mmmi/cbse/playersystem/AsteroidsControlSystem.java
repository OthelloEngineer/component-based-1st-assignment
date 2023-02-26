package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.movementFactory.ConstantRandomMovement;
import dk.sdu.mmmi.cbse.common.data.movementFactory.Movement;
import dk.sdu.mmmi.cbse.common.data.movementFactory.MovementFactory;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
/**
 *
 * @author jcs
 */
public class AsteroidsControlSystem implements IEntityProcessingService {
    MovementFactory movementFactory;

    public AsteroidsControlSystem() {
        movementFactory = new ConstantRandomMovement();
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float scale = entity.getRadius();

        shapex[0] = (float) (x + Math.cos(radians) * scale);
        shapey[0] = (float) (y + Math.sin(radians) * scale);

        shapex[1] = (float) (x + Math.cos(radians - 3.1415f/2) * scale);
        shapey[1] = (float) (y + Math.sin(radians - 3.1145f/2) * scale);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * scale);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * scale);

        shapex[3] = (float) (x + Math.cos(radians + 3.1415f/2) * scale);
        shapey[3] = (float) (y + Math.sin(radians + 3.1415f/2) * scale);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Asteroids.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            lifePart.reduceExpiration(0.5f);
            if(lifePart.isIsHit() && lifePart.getExpiration()<0){
                handleCollider(world, (Asteroids) enemy);
            }
            this.movementFactory.getNewMovement(movingPart);
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            updateShape(enemy);
        }
    }
    private void handleCollider(World world, Asteroids enemy){
        System.out.println("enemy was hit");
        float newRadius = enemy.getRadius()/2;
        if(newRadius<5){
            world.removeEntity(enemy);
            return;
        }
        LifePart lifePart = enemy.getPart(LifePart.class);
        lifePart.setExpiration(20);
        enemy.setRadius(newRadius);
        Entity newAsteroid = new Asteroids();
        newAsteroid.setRadius(newRadius);
        PositionPart oldPos = enemy.getPart(PositionPart.class);

        newAsteroid.add(new MovingPart(10, 200, 200, 3));
        newAsteroid.add(new PositionPart(oldPos.getX()+10, oldPos.getY()+10, (float) (oldPos.getRadians()-Math.PI)));
        newAsteroid.add(new LifePart(100,20));
        world.addEntity(newAsteroid);
    }
}
