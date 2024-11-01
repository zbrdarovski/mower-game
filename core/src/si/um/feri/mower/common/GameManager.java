package si.um.feri.mower.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String RESULT_BEST = "BEST_RESULT";

    private final Preferences PREFS;
    private int result;
    private int health;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(GameManager.class.getSimpleName());
    }

    public void resetResult() {
        result = 0;
        health = 100;
    }

    public boolean isGameOver() {
        return health <= 0;
    }

    public void damage() {
        health--;
        if (health == 0) {
            if (result > getBestResult()) setBestResult(result);
        }
    }

    public int getHealth() {
        return health;
    }

    public void incResult() {
        result++;
    }

    public int getResult() {
        return result;
    }

    public void setBestResult(int result) {
        PREFS.putInteger(RESULT_BEST, result);
        PREFS.flush();
    }

    public int getBestResult() {
        return PREFS.getInteger(RESULT_BEST, 0);
    }
}
