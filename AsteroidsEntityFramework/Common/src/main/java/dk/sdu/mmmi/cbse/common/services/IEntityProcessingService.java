package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


public interface IEntityProcessingService {
    /**
     * preconditions:
     * @param world
     * pre conditions:
     * <ul>
     * <li> world.getEntities must accept Player.class as argument and return objects</li>
     * <li> World cannot be null</li>
     * <li> player object must contain MovingPart encapsulated by getPart</li>
     * </ul>
     * postconditions
     * <li> player must be updatable through updateShape</li>
     * <lI> </lI>
     * @param gameData
     * pre conditions:
     * <ul>
     * <li> gameData object must include getKeys and check register clicks with isDown </li>
     * </ul>
     */
    void process(GameData gameData, World world);
}
