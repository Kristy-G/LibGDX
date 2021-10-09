package com.star.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameController {
    private Background background = new Background(this);
    private AsteroidController asteroidController = new AsteroidController(this);
    private BulletController bulletController = new BulletController(this);
    private ParticleController particleController = new ParticleController();
    private PowerUpsController powerUpsController = new PowerUpsController(this);
    private Hero hero = new Hero(this);
    private Vector2 tmpVec = new Vector2(0.0F, 0.0F);
    private Boolean pause = false;

    public PowerUpsController getPowerUpsController() {
        return this.powerUpsController;
    }

    public AsteroidController getAsteroidController() {
        return this.asteroidController;
    }

    public BulletController getBulletController() {
        return this.bulletController;
    }

    public ParticleController getParticleController() {
        return this.particleController;
    }

    public Hero getHero() {
        return this.hero;
    }

    public Background getBackground() {
        return this.background;
    }

    public GameController() {
        for(int i = 0; i < 3; ++i) {
            this.asteroidController.setup((float)MathUtils.random(0, 1280), (float)MathUtils.random(0, 720), (float)MathUtils.random(-200, 200), (float)MathUtils.random(-200, 200), 1.0F);
        }

    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(62)){
            pause = !pause;
        }
        if (!pause){
            this.background.update(dt);
            this.hero.update(dt);
            this.asteroidController.update(dt);
            this.bulletController.update(dt);
            this.powerUpsController.update(dt);
            this.particleController.update(dt);
            this.checkCollisions();
        }
    }

    public void checkCollisions() {
        int i;
        for(i = 0; i < this.asteroidController.getActiveList().size(); ++i) {
            Asteroid a = (Asteroid)this.asteroidController.getActiveList().get(i);
            if (a.getHitArea().overlaps(this.hero.getHitArea())) {
                float dst = a.getPosition().dst(this.hero.getPosition());
                float halfOverLen = (a.getHitArea().radius + this.hero.getHitArea().radius - dst) / 2.0F;
                this.tmpVec.set(this.hero.getPosition()).sub(a.getPosition()).nor();
                this.hero.getPosition().mulAdd(this.tmpVec, halfOverLen);
                a.getPosition().mulAdd(this.tmpVec, -halfOverLen);
                float sumScl = this.hero.getHitArea().radius * 2.0F + a.getHitArea().radius;
                this.hero.getVelocity().mulAdd(this.tmpVec, 200.0F * a.getHitArea().radius / sumScl);
                a.getVelocity().mulAdd(this.tmpVec, -200.0F * this.hero.getHitArea().radius / sumScl);
                if (a.takeDamage(2)) {
                    this.hero.addScore(a.getHpMax() * 20);
                }

                this.hero.takeDamage(2);
            }
        }

        label55:
        for(i = 0; i < this.bulletController.getActiveList().size(); ++i) {
            Bullet b = (Bullet)this.bulletController.getActiveList().get(i);

            for(int j = 0; j < this.asteroidController.getActiveList().size(); ++j) {
                Asteroid a = (Asteroid)this.asteroidController.getActiveList().get(j);
                if (a.getHitArea().contains(b.getPosition())) {
                    this.particleController.setup(b.getPosition().x + (float)MathUtils.random(-4, 4), b.getPosition().y + (float)MathUtils.random(-4, 4), b.getVelocity().x * -0.3F + (float)MathUtils.random(-30, 30), b.getVelocity().y * -0.3F + (float)MathUtils.random(-30, 30), 0.2F, 2.3F, 1.7F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F);
                    b.deactivate();
                    if (!a.takeDamage(1)) {
                        break;
                    }

                    this.hero.addScore(a.getHpMax() * 100);
                    int k = 0;

                    while(true) {
                        if (k >= 3) {
                            continue label55;
                        }

                        this.powerUpsController.setup(a.getPosition().x, a.getPosition().y, a.getScale() / 4.0F);
                        ++k;
                    }
                }
            }
        }

        for(i = 0; i < this.powerUpsController.getActiveList().size(); ++i) {
            PowerUp p = (PowerUp)this.powerUpsController.getActiveList().get(i);
            if (this.hero.getHitArea().contains(p.getPosition())) {
                this.hero.consume(p);
                this.particleController.getEffectBuilder().takePowerUpEffect(p.getPosition().x, p.getPosition().y);
                p.deactivate();
            }
        }
    }
}
