package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.movementFactory.ConstantRandomMovement;
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
        int scale = 20;

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

            this.movementFactory.getNewMovement(movingPart);

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        }
    }
}
