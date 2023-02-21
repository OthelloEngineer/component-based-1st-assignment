package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
/**
 *
 */
public interface IGamePluginService {
    /**
     *
     * @param gameData
     * <br>
     * Gamedata is available in order for granting information about resolutions
     * preconditions:
     * <ul>
     * <li> Gamedata must not be null </li>
     * post conditions:
     * <lI> Gamedata may not be modified</lI>
     * </ul>
     * @param world
     * <br>
     * world is provided in order to add entities to the world
     * preconditions
     * <ul>
     *
     * <li> Entity must have a shape implemented before being added to the world </li>
     * <li> world may only be used for adding entities</li>
     * </ul>
     */
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
    