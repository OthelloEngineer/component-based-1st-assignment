package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.*;
import dk.sdu.mmmi.cbse.playersystem.AsteroidsControlSystem;
import dk.sdu.mmmi.cbse.playersystem.AsteroidsPlugin;

import dk.sdu.mmmi.cbse.collision.CollisionDetector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private World world = new World();
    private IPostEntityProcessingService collisionDetector;

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        //this.collisionDetector = new CollisionDetector();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        //IGamePluginService playerPlugin = new PlayerPlugin();
        //IGamePluginService enemyPlugin = new EnemyPlugin();
        //final int asteroidCount = 5;
        //List<IGamePluginService> asPlugins = new ArrayList<>() {{
        //    for (int i = 0; i < asteroidCount; i++) {
        //        add(new AsteroidsPlugin());
        //    }
        //}};

        //IEntityProcessingService playerProcess = new PlayerControlSystem();
        //IEntityProcessingService enemyProcess = new EnemyControlSystem();
        //IEntityProcessingService asProcessors = new AsteroidsControlSystem();
        //IEntityProcessingService bulletProccesor = new BulletControlSystem();

        // entityPlugins.add(enemyPlugin);
        // entityPlugins.add(playerPlugin);
        // entityPlugins.addAll(asPlugins);
        // entityProcessors.add(asProcessors);
        // entityProcessors.add(enemyProcess);
        // entityProcessors.add(playerProcess);
        // entityProcessors.add(bulletProccesor);
        // Lookup all Game Plugins using ServiceLoader

        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
        //this.collisionDetector.process(gameData, world);
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(entity.getColor());
            sr.begin(ShapeRenderer.ShapeType.Line);
            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();
            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }
            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
