package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.star.app.game.helpers.ObjectPool;
import com.star.app.screen.utils.Assets;

public class BulletController extends ObjectPool<Bullet> {
    private GameController gc;
    private TextureRegion bulletTexture;

    protected Bullet newObject() {
        return new Bullet(this.gc);
    }

    public BulletController(GameController gc) {
        this.gc = gc;
        this.bulletTexture = Assets.getInstance().getAtlas().findRegion("bullet");
    }

    public void render(SpriteBatch batch) {
        for(int i = 0; i < this.activeList.size(); ++i) {
            Bullet b = (Bullet)this.activeList.get(i);
            batch.draw(this.bulletTexture, b.getPosition().x - 16.0F, b.getPosition().y - 16.0F);
        }

    }

    public void setup(float x, float y, float vx, float vy) {
        ((Bullet)this.getActiveElement()).activate(x, y, vx, vy);
    }

    public void update(float dt) {
        for(int i = 0; i < this.activeList.size(); ++i) {
            ((Bullet)this.activeList.get(i)).update(dt);
        }

        this.checkPool();
    }
}
