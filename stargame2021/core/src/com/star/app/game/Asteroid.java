package com.star.app.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;

public class Asteroid implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Asteroid() {
        this.position = new Vector2(-32, (float) Math.random()*(ScreenManager.SCREEN_HEIGHT));
        this.velocity = new Vector2(0,0);
        this.active = false;
    }

    public void activate(float x, float y, float vx, float vy) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
    }

    public void update(float dt) {

        if (position.x > ScreenManager.SCREEN_WIDTH + 32 || position.x < -32 || position.y > ScreenManager.SCREEN_HEIGHT + 32 || position.y < -32) {
            this.deactivate();
        } else {
            position.x += MathUtils.cosDeg(angle) * 120.0f * dt;
            position.y += MathUtils.sinDeg(angle) * 120.0f * dt;
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

}
