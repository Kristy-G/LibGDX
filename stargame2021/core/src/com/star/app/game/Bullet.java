package com.star.app.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;

public class Bullet implements Poolable {
    private GameController gc;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public boolean isActive() {
        return this.active;
    }

    public Bullet(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0.0F, 0.0F);
        this.velocity = new Vector2(0.0F, 0.0F);
        this.active = false;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(float x, float y, float vx, float vy) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.active = true;
    }

    public void update(float dt) {
        this.position.mulAdd(this.velocity, dt);
        float bx = this.position.x;
        float by = this.position.y;
        this.gc.getParticleController().setup(bx + (float)MathUtils.random(-4, 4), by + (float)MathUtils.random(-4, 4), this.velocity.x * -0.3F + (float)MathUtils.random(-20, 20), this.velocity.y * -0.3F + (float)MathUtils.random(-20, 20), 0.05F, 1.5F, 0.2F, 1.0F, 0.3F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        if (this.position.x < -20.0F || this.position.x > 1300.0F || this.position.y < -20.0F || this.position.y > 740.0F) {
            this.deactivate();
        }

    }
}
