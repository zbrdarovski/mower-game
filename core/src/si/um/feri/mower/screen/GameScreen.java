package si.um.feri.mower.screen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.mower.MowerGame;
import si.um.feri.mower.assets.AssetDescriptors;
import si.um.feri.mower.assets.AssetPaths;
import si.um.feri.mower.common.GameManager;
import si.um.feri.mower.config.GameConfig;
import si.um.feri.mower.ecs.system.BoundsSystem;
import si.um.feri.mower.ecs.system.CameraMovementSystem;
import si.um.feri.mower.ecs.system.CollisionSystem;
import si.um.feri.mower.ecs.system.HudRenderSystem;
import si.um.feri.mower.ecs.system.MovementSystem;
import si.um.feri.mower.ecs.system.MowerInputSystem;
import si.um.feri.mower.ecs.system.MowerMusicSystem;
import si.um.feri.mower.ecs.system.RenderSystem;
import si.um.feri.mower.ecs.system.WorldWrapSystem;
import si.um.feri.mower.ecs.system.debug.DebugCameraSystem;
import si.um.feri.mower.ecs.system.debug.DebugGridRenderSystem;
import si.um.feri.mower.ecs.system.debug.DebugInputSystem;
import si.um.feri.mower.ecs.system.debug.DebugRenderSystem;
import si.um.feri.mower.ecs.system.passive.EntityFactorySystem;
import si.um.feri.mower.ecs.system.passive.SoundSystem;
import si.um.feri.mower.ecs.system.passive.StartUpSystem;
import si.um.feri.mower.ecs.system.passive.TiledSystem;

/**
 * Artwork from https://goodstuffnononsense.com/about/
 * https://goodstuffnononsense.com/hand-drawn-icons/space-icons/
 */

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private BitmapFont font;
    private PooledEngine engine;

    // OrthoCachedTiledMapRenderer mapRenderer;

    public GameScreen(MowerGame game) {
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        TiledMap map = assetManager.get(AssetPaths.TILES);   // Rethink add with manager?

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        renderer = new ShapeRenderer();
        font = assetManager.get(AssetDescriptors.FONT32);
        engine = new PooledEngine();

        // passive systems
        engine.addSystem(new EntityFactorySystem(assetManager));
        engine.addSystem(new SoundSystem(assetManager));
        engine.addSystem(new TiledSystem(map));
        engine.addSystem(new StartUpSystem());

        // active systems
        engine.addSystem(new MowerInputSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new CameraMovementSystem());
        engine.addSystem(new MowerMusicSystem(assetManager));
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new HudRenderSystem(batch, hudViewport, font));

        // debug systems
        if (GameConfig.debug) {
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(
                    GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2,    // center
                    camera
            ));
            engine.addSystem(new DebugGridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugInputSystem());
        }
        GameManager.INSTANCE.resetResult();

        // mapRenderer = new OrthoCachedTiledMapRenderer(map);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) GameManager.INSTANCE.resetResult();

        engine.update(delta);

        // if (GameManager.INSTANCE.isGameOver()) {
        // Gdx.app.exit();
        // game.setScreen(new MenuScreen(game));
        // }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }
}
