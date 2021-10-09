package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.utils.Assets;

public class Asteroid implements Poolable {
    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private int hp;
    private int hpMax;
    private float angle;
    private float rotationSpeed;
    private float scale;
    private boolean active;
    private Circle hitArea;
    private final float BASE_SIZE = 256.0F;
    private final float BASE_RADIUS = 128.0F;

    public float getScale() {
        return this.scale;
    }

    public int getHpMax() {
        return this.hpMax;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public Circle getHitArea() {
        return this.hitArea;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public boolean isActive() {
        return this.active;
    }

    public Asteroid(GameController gc) {
        this.gc = gc;
        this.position = new Vector2(0.0F, 0.0F);
        this.velocity = new Vector2(0.0F, 0.0F);
        this.hitArea = new Circle(0.0F, 0.0F, 0.0F);
        this.active = false;
        this.texture = Assets.getInstance().getAtlas().findRegion("asteroid");
    }

    public void render(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x - 128.0F, this.position.y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.scale, this.scale, this.angle);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(float x, float y, float vx, float vy, float scale) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.hpMax = (int)(7.0F * scale);
        this.hp = this.hpMax;
        this.angle = MathUtils.random(0.0F, 360.0F);
        this.rotationSpeed = MathUtils.random(-180.0F, 180.0F);
        this.hitArea.setPosition(this.position);
        this.scale = scale;
        this.active = true;
        this.hitArea.setRadius(128.0F * scale * 0.9F);
    }

    public boolean takeDamage(int amount) {
        this.hp -= amount;
        if (this.hp <= 0) {
            this.deactivate();
            if ((double)this.scale > 0.3D) {
                this.gc.getAsteroidController().setup(this.position.x, this.position.y, (float)MathUtils.random(-200, 200), (float)MathUtils.random(-200, 200), this.scale - 0.2F);
                this.gc.getAsteroidController().setup(this.position.x, this.position.y, (float)MathUtils.random(-200, 200), (float)MathUtils.random(-200, 200), this.scale - 0.2F);
                this.gc.getAsteroidController().setup(this.position.x, this.position.y, (float)MathUtils.random(-200, 200), (float)MathUtils.random(-200, 200), this.scale - 0.2F);
            }

            return true;
        } else {
            return false;
        }
    }

    public void update(float dt) {
        this.position.mulAdd(this.velocity, dt);
        this.angle += this.rotationSpeed * dt;
        if (this.position.x < -200.0F) {
            this.position.x = 1480.0F;
        }

        if (this.position.x > 1480.0F) {
            this.position.x = -200.0F;
        }

        if (this.position.y < -200.0F) {
            this.position.y = 920.0F;
        }

        if (this.position.y > 920.0F) {
            this.position.y = -200.0F;
        }

        this.hitArea.setPosition(this.position);
    }
}
