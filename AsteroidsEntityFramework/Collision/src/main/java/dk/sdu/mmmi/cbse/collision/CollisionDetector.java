package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jcs
 */
public class CollisionDetector implements IPostEntityProcessingService {
    List<Entity> entityList;
    @Override
    public void process(GameData gameData, World world) {
        this.entityList = world.getEntities().stream().toList();
        List<Entity> colliders = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                if(isColliding(entityList.get(i), entityList.get(j))) {
                    colliders.add(entityList.get(i));
                    colliders.add(entityList.get(j));
                }
            }
        }
        colliders.forEach(x-> ((LifePart)x.getPart(LifePart.class)).setIsHit(true));
    }

    private boolean isColliding(Entity e1, Entity e2){
        LifePart e1Life = e1.getPart(LifePart.class);
        LifePart e2Life = e1.getPart(LifePart.class);
        float expiration = e1Life.getExpiration() + e2Life.getExpiration();
        if(expiration>0){
            return false;
        }
        float[] e1Y = e1.getShapeY();
        float[] e1X = e1.getShapeX();
        float[] e2Y = e2.getShapeY();
        float[] e2X = e2.getShapeX();

        // Calculate the distance between the centers of the entities
        float centerX1 = (e1X[0] + e1X[2]) / 2;
        float centerY1 = (e1Y[0] + e1Y[2]) / 2;
        float centerX2 = (e2X[0] + e2X[2]) / 2;
        float centerY2 = (e2Y[0] + e2Y[2]) / 2;
        double distance = Math.sqrt(Math.pow(centerX1 - centerX2, 2) + Math.pow(centerY1 - centerY2, 2));

        // Calculate the sum of the radii of the entities
        int radius1 = (int) Math.sqrt(Math.pow(e1X[0] - e1X[2], 2) + Math.pow(e1Y[0] - e1Y[2], 2)) / 2;
        int radius2 = (int) Math.sqrt(Math.pow(e2X[0] - e2X[2], 2) + Math.pow(e2Y[0] - e2Y[2], 2)) / 2;
        int sumOfRadii = radius1 + radius2;
        // Check if the entities are colliding
        if (distance <= sumOfRadii){
            return true;
        }
        setIsHit(e1Life, false);
        setIsHit(e2Life, false);
        return false;
    }
    public void setIsHit(LifePart lifePart, boolean isHit){
        lifePart.setIsHit(isHit);
    }
}
