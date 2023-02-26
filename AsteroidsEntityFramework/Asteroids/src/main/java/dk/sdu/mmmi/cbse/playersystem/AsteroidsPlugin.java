package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IRunTimeInstantiator;
import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService, IRunTimeInstantiator {

    private Entity asteroid;

    public AsteroidsPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    private Entity createAsteroid(GameData gameData) {
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 3;
        Random random = new Random();
        float x = random.nextFloat(gameData.getDisplayWidth());
        float y = random.nextFloat(gameData.getDisplayWidth());
        float radians = random.nextFloat((float)Math.PI*2);
        Entity asteroid = new Asteroids();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(100,1));
        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }

    @Override
    public Entity createEntity(PositionPart positionPart) {
        return null;
    }
}
