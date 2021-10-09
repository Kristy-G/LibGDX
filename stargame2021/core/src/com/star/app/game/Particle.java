package com.star.app.game;

import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;

public class Particle implements Poolable {
    private Vector2 position = new Vector2(0.0F, 0.0F);
    private Vector2 velocity = new Vector2(0.0F, 0.0F);
    private float r1;
    private float g1;
    private float b1;
    private float a1;
    private float r2;
    private float g2;
    private float b2;
    private float a2;
    private boolean active;
    private float time;
    private float timeMax;
    private float size1 = 1.0F;
    private float size2 = 1.0F;

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public float getR1() {
        return this.r1;
    }

    public float getG1() {
        return this.g1;
    }

    public float getB1() {
        return this.b1;
    }

    public float getA1() {
        return this.a1;
    }

    public float getR2() {
        return this.r2;
    }

    public float getG2() {
        return this.g2;
    }

    public float getB2() {
        return this.b2;
    }

    public float getA2() {
        return this.a2;
    }

    public float getTime() {
        return this.time;
    }

    public float getTimeMax() {
        return this.timeMax;
    }

    public float getSize1() {
        return this.size1;
    }

    public float getSize2() {
        return this.size2;
    }

    public boolean isActive() {
        return this.active;
    }

    public Particle() {
    }

    public void init(float x, float y, float vx, float vy, float timeMax, float size1, float size2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        this.position.x = x;
        this.position.y = y;
        this.velocity.x = vx;
        this.velocity.y = vy;
        this.r1 = r1;
        this.r2 = r2;
        this.g1 = g1;
        this.g2 = g2;
        this.b1 = b1;
        this.b2 = b2;
        this.a1 = a1;
        this.a2 = a2;
        this.time = 0.0F;
        this.timeMax = timeMax;
        this.size1 = size1;
        this.size2 = size2;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(float dt) {
        this.time += dt;
        this.position.mulAdd(this.velocity, dt);
        if (this.time > this.timeMax) {
            this.deactivate();
        }

    }
}
