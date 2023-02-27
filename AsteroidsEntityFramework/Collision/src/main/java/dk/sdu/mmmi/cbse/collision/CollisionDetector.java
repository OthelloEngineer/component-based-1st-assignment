package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Enemy;
import javafx.geometry.Pos;

import java.util.*;

/**
 *
 * @author jcs
 */
public class CollisionDetector implements IPostEntityProcessingService {
    List<Entity> entityList;
    @Override
    public void process(GameData gameData, World world) {
        this.entityList = world.getEntities().stream().toList();
        Set<Entity> colliders = new HashSet<>();
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                if(isColliding(entityList.get(i), entityList.get(j))) {
                    colliders.add(entityList.get(i));
                    colliders.add(entityList.get(j));
                    if (entityList.get(i) instanceof Enemy || entityList.get(j) instanceof Enemy){
                        System.out.println("Enemy set as hit");
                    }
                }
            }
        }
        colliders.forEach(x-> ((LifePart)x.getPart(LifePart.class)).setIsHit(true));
    }

    private boolean isColliding(Entity e1, Entity e2){
        LifePart e1Life = e1.getPart(LifePart.class);
        LifePart e2Life = e2.getPart(LifePart.class);
        float expiration = e1Life.getExpiration() + e2Life.getExpiration();
        if(e1Life.getExpiration() > 0 || e2Life.getExpiration() > 0 ){
            return false;
        }

        PositionPart e1Pos = e1.getPart(PositionPart.class);
        PositionPart e2Pos = e2.getPart(PositionPart.class);

        double radius1 = e1.getRadius();
        double radius2 = e2.getRadius();
        double distance = Math.sqrt(Math.pow((e2Pos.getX() - e1Pos.getX()), 2) + Math.pow((e2Pos.getY() - e1Pos.getY()), 2));
        if (distance <= (radius1 + radius2)){
            System.out.println(e1);
            System.out.println(e2);
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
