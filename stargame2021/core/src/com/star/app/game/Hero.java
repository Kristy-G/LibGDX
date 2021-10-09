package com.star.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.star.app.screen.utils.Assets;

public class Hero {
    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private float enginePower;
    private float fireTimer;
    private int score;
    private int scoreView;
    private int hp;
    private int money;
    private StringBuilder stringBuilder;
    private Circle hitArea;
    private Weapon currentWeapon;

    public Circle getHitArea() {
        return this.hitArea;
    }

    public float getAngle() {
        return this.angle;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Hero(GameController gc) {
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
        this.position = new Vector2(640.0F, 360.0F);
        this.velocity = new Vector2(0.0F, 0.0F);
        this.angle = 0.0F;
        this.enginePower = 500.0F;
        this.hp = 100;
        this.stringBuilder = new StringBuilder();
        this.hitArea = new Circle(this.position, 26.0F);
        this.currentWeapon = new Weapon(gc, this, "Laser", 0.2F, 1, 600.0F, 300, new Vector3[]{new Vector3(28.0F, 0.0F, 0.0F), new Vector3(28.0F, 90.0F, 20.0F), new Vector3(28.0F, -90.0F, -20.0F)});
    }

    public void render(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x - 32.0F, this.position.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, 1.0F, 1.0F, this.angle);
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {
        this.stringBuilder.delete(0, stringBuilder.length());
        this.stringBuilder.append("SCORE: ").append(this.scoreView).append("\n");
        this.stringBuilder.append("HP: ").append(this.hp).append("\n");
        this.stringBuilder.append("MONEY: ").append(this.money).append("\n");
        this.stringBuilder.append("BULLETS: ").append(this.currentWeapon.getCurBullets()).append(" / ").append(this.currentWeapon.getMaxBullets()).append("\n");
        font.draw(batch, this.stringBuilder, 20.0F, 700.0F);
    }

    public void takeDamage(int amount) {
        this.hp -= amount;
    }

    public void consume(PowerUp p) {
        switch(p.getType()) {
            case MEDKIT:
                this.hp += p.getPower();
                break;
            case MONEY:
                this.money += p.getPower();
                break;
            case AMMOS:
                this.currentWeapon.addAmmos(p.getPower());
        }

    }

    public void update(float dt) {
        this.fireTimer += dt;
        this.updateScore(dt);
        if (Gdx.input.isKeyPressed(44)) {
            this.tryToFire();
        }

        if (Gdx.input.isKeyPressed(29)) {
            this.angle += 180.0F * dt;
        }

        if (Gdx.input.isKeyPressed(32)) {
            this.angle -= 180.0F * dt;
        }

        if (Gdx.input.isKeyPressed(51)) {
            Vector2 var10000 = this.velocity;
            var10000.x += MathUtils.cosDeg(this.angle) * this.enginePower * dt;
            var10000 = this.velocity;
            var10000.y += MathUtils.sinDeg(this.angle) * this.enginePower * dt;
        }

        this.position.mulAdd(this.velocity, dt);
        this.hitArea.setPosition(this.position);
        float stopKoef = 1.0F - 1.0F * dt;
        if (stopKoef < 0.0F) {
            stopKoef = 0.0F;
        }

        this.velocity.scl(stopKoef);
        if (this.velocity.len() > 50.0F) {
            float bx = this.position.x + MathUtils.cosDeg(this.angle + 180.0F) * 20.0F;
            float by = this.position.y + MathUtils.sinDeg(this.angle + 180.0F) * 20.0F;

            for(int i = 0; i < 2; ++i) {
                this.gc.getParticleController().setup(bx + (float)MathUtils.random(-4, 4), by + (float)MathUtils.random(-4, 4), this.velocity.x * -0.3F + (float)MathUtils.random(-20, 20), this.velocity.y * -0.3F + (float)MathUtils.random(-20, 20), 0.5F, 1.2F, 0.2F, 1.0F, 0.3F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        this.checkSpaceBorders();
    }

    private void updateScore(float dt) {
        if (this.scoreView < this.score) {
            this.scoreView = (int)((float)this.scoreView + 1000.0F * dt);
            if (this.scoreView > this.score) {
                this.scoreView = this.score;
            }
        }

    }

    private void tryToFire() {
        if ((double)this.fireTimer > 0.2D) {
            this.fireTimer = 0.0F;
            this.currentWeapon.fire();
        }

    }

    private void checkSpaceBorders() {
        Vector2 var10000;
        if (this.position.x < 32.0F) {
            this.position.x = 32.0F;
            var10000 = this.velocity;
            var10000.x *= -1.0F;
        }

        if (this.position.x > 1248.0F) {
            this.position.x = 1248.0F;
            var10000 = this.velocity;
            var10000.x *= -1.0F;
        }

        if (this.position.y < 32.0F) {
            this.position.y = 32.0F;
            var10000 = this.velocity;
            var10000.y *= -1.0F;
        }

        if (this.position.y > 688.0F) {
            this.position.y = 688.0F;
            var10000 = this.velocity;
            var10000.y *= -1.0F;
        }

    }
}
