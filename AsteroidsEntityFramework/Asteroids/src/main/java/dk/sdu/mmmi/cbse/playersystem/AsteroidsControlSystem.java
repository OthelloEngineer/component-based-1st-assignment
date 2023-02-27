package dk.sdu.mmmi.cbse.playersystem;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.movementFactory.ConstantRandomMovement;
import dk.sdu.mmmi.cbse.common.data.movementFactory.MovementFactory;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 *
 * @author jcs
 */
public class AsteroidsControlSystem implements IEntityProcessingService {
    MovementFactory movementFactory;
    float asteroidTimer = 0;
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
           // asteroidSpawner(world, gameData);
            this.movementFactory.getNewMovement(movingPart);
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            updateShape(enemy);
        }
    }

    private void asteroidSpawner(World world, GameData gameData){
        asteroidTimer += gameData.getDelta();
        if(asteroidTimer >50) {
            System.out.println("hello");
            Entity entity = new Asteroids();
            float x = random.nextFloat(gameData.getDisplayWidth());
            float y = random.nextFloat(gameData.getDisplayWidth());
            float radians = random.nextFloat((float)Math.PI*2);
            entity.add(new MovingPart(10, 200, 200, 3));
            entity.add(new PositionPart(x, y, radians));
            entity.add(new LifePart(100,Asteroids.ASTREOID_EXPIRATION));
            world.addEntity(entity);
            asteroidTimer = 0;
        }
    }
    private void handleCollider(World world, Asteroids enemy){
        float newRadius = enemy.getRadius()/2;
        if(newRadius<5){
            world.removeEntity(enemy);
            return;
        }
        LifePart lifePart = enemy.getPart(LifePart.class);
        lifePart.setExpiration(Asteroids.ASTREOID_EXPIRATION);
        enemy.setRadius(newRadius);
        Entity newAsteroid = new Asteroids();
        newAsteroid.setRadius(newRadius);
        PositionPart oldPos = enemy.getPart(PositionPart.class);
        oldPos.setRadians((float) (oldPos.getRadians()+Math.PI/4));
        newAsteroid.add(new MovingPart(10, 200, 200, 3));
        newAsteroid.add(new PositionPart(oldPos.getX()+25, oldPos.getY()+25, (float) (oldPos.getRadians()-Math.PI*1)));
        newAsteroid.add(new LifePart(100,Asteroids.ASTREOID_EXPIRATION));
        world.addEntity(newAsteroid);
    }
}