package com.star.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Asteroid {
    private Texture texture;
    private Vector2 position;
    private Vector2 lastDisplacement;
    private float angel = (float) (Math.random() * 175) - 80;

    public Vector2 getLastDisplacement() {
        return lastDisplacement;
    }

    public Asteroid() {
        this.texture = new Texture("asteroid.png");
        this.position = new Vector2(-32, (float) Math.random()*(ScreenManager.SCREEN_HEIGHT / 2));
        this.lastDisplacement = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, 1, 1,
                angel, 0, 0, 256, 256, false, false);
    }

    public void update(float dt) {

        if (position.x > ScreenManager.SCREEN_WIDTH + 32 || position.x < -32 || position.y > ScreenManager.SCREEN_HEIGHT + 32 || position.y < -32) {
            int r = new Random().nextInt(4);
            if (r == 0) {
                angel = (float) (Math.random() * 170) - 85;
                position.x = -32;
                position.y = (float) (Math.random() * (ScreenManager.SCREEN_HEIGHT + 32));
            }else if (r == 1) {
                angel = (float) (Math.random() * 170) + 95;
                position.x = ScreenManager.SCREEN_WIDTH + 32;
                position.y = (float) (Math.random() * (ScreenManager.SCREEN_HEIGHT + 32));
            }else if (r == 2) {
                angel = (float) (Math.random() * 170) + 5;
                position.x = (float) (Math.random() * (ScreenManager.SCREEN_WIDTH + 32));
                position.y = -32;
            }else {
                angel = (float) (Math.random() * 170) + 185;
                position.x = (float) (Math.random() * (ScreenManager.SCREEN_WIDTH + 32));
                position.y = ScreenManager.SCREEN_HEIGHT + 32;
            }
        } else {
            position.x += MathUtils.cosDeg(angel) * 120.0f * dt;
            position.y += MathUtils.sinDeg(angel) * 120.0f * dt;
        }
    }
}
