package com.star.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.ObjectPool;
import com.star.app.screen.ScreenManager;

import java.util.Random;

public class AsteroidController extends ObjectPool<Asteroid> {

    private Texture asteroidTexture;

    public Vector2 getStartPosition(Asteroid a) {
        Vector2 startPosition = new Vector2();
        int r = new Random().nextInt(4);
        if (r == 0) {
            a.setAngle((float) (Math.random() * 170) - 85);
            startPosition.x = -32;
            startPosition.y = (float) (Math.random() * (ScreenManager.SCREEN_HEIGHT));
        }else if (r == 1) {
            a.setAngle((float) (Math.random() * 170) + 95);
            startPosition.x = ScreenManager.SCREEN_WIDTH + 32;
            startPosition.y = (float) (Math.random() * (ScreenManager.SCREEN_HEIGHT));
        }else if (r == 2) {
            a.setAngle((float) (Math.random() * 170) + 5);
            startPosition.x = (float) (Math.random() * (ScreenManager.SCREEN_WIDTH));
            startPosition.y = -32;
        }else {
            a.setAngle((float) (Math.random() * 170) + 185);
            startPosition.x = (float) (Math.random() * (ScreenManager.SCREEN_WIDTH));
            startPosition.y = ScreenManager.SCREEN_HEIGHT + 32;
        }
        return startPosition;
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public AsteroidController() {
        this.asteroidTexture = new Texture("asteroid.png");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Asteroid a = activeList.get(i);
            batch.draw(asteroidTexture, a.getPosition().x - 32, a.getPosition().y - 32, 32, 32, 64, 64, 1, 1, a.getAngle(), 0, 0, 256, 256, false, false);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void setup(float vx, float vy){
        Asteroid a = getActiveElement();
        a.activate(getStartPosition(a).x, getStartPosition(a).y, vx,vy);
    }
}
