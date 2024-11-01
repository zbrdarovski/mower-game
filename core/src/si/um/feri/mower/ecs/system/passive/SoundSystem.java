package si.um.feri.mower.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import si.um.feri.mower.assets.AssetDescriptors;

public class SoundSystem extends EntitySystem {

    private final AssetManager assetManager;

    private Sound pickSound;
    private Sound obstacleSound;
    private float obstacleTimePlaying;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        // setProcessing(false);    // passive
        init();
    }

    private void init() {
        pickSound = assetManager.get(AssetDescriptors.PICK_SOUND);
        obstacleSound = assetManager.get(AssetDescriptors.OBSTACLE_SOUND);
    }

    public void pick() {
        pickSound.play();
    }

    public void obstacle() {
        if (obstacleTimePlaying < 0) {
            obstacleSound.play();
            obstacleTimePlaying = 2000; // 2 s
        }
    }

    @Override
    public void update(float deltaTime) {
        obstacleTimePlaying -= deltaTime;
    }
}