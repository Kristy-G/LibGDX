package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.star.app.game.helpers.ObjectPool;
import com.star.app.screen.utils.Assets;

public class ParticleController extends ObjectPool<Particle> {
    private TextureRegion oneParticle = Assets.getInstance().getAtlas().findRegion("star16");
    private ParticleController.EffectBuilder effectBuilder = new ParticleController.EffectBuilder();

    public ParticleController.EffectBuilder getEffectBuilder() {
        return this.effectBuilder;
    }

    public ParticleController() {
    }

    protected Particle newObject() {
        return new Particle();
    }

    public void render(SpriteBatch batch) {
        batch.setBlendFunction(770, 771);

        int i;
        Particle o;
        float t;
        float scale;
        for(i = 0; i < this.activeList.size(); ++i) {
            o = (Particle)this.activeList.get(i);
            t = o.getTime() / o.getTimeMax();
            scale = this.lerp(o.getSize1(), o.getSize2(), t);
            batch.setColor(this.lerp(o.getR1(), o.getR2(), t), this.lerp(o.getG1(), o.getG2(), t), this.lerp(o.getB1(), o.getB2(), t), this.lerp(o.getA1(), o.getA2(), t));
            batch.draw(this.oneParticle, o.getPosition().x - 8.0F, o.getPosition().y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, scale, scale, 0.0F);
        }

        batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        batch.setBlendFunction(770, 1);

        for(i = 0; i < this.activeList.size(); ++i) {
            o = (Particle)this.activeList.get(i);
            t = o.getTime() / o.getTimeMax();
            scale = this.lerp(o.getSize1(), o.getSize2(), t);
            if (MathUtils.random(0, 300) < 3) {
                scale *= 5.0F;
            }

            batch.setColor(this.lerp(o.getR1(), o.getR2(), t), this.lerp(o.getG1(), o.getG2(), t), this.lerp(o.getB1(), o.getB2(), t), this.lerp(o.getA1(), o.getA2(), t));
            batch.draw(this.oneParticle, o.getPosition().x - 8.0F, o.getPosition().y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, scale, scale, 0.0F);
        }

        batch.setBlendFunction(770, 771);
        batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setup(float x, float y, float vx, float vy, float timeMax, float size1, float size2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        Particle item = (Particle)this.getActiveElement();
        item.init(x, y, vx, vy, timeMax, size1, size2, r1, g1, b1, a1, r2, g2, b2, a2);
    }

    public void update(float dt) {
        for(int i = 0; i < this.activeList.size(); ++i) {
            ((Particle)this.activeList.get(i)).update(dt);
        }

        this.checkPool();
    }

    public float lerp(float value1, float value2, float point) {
        return value1 + (value2 - value1) * point;
    }

    public class EffectBuilder {
        public EffectBuilder() {
        }

        public void buildMonsterSplash(float x, float y) {
            for(int i = 0; i < 15; ++i) {
                float randomAngle = MathUtils.random(0.0F, 6.28F);
                float randomSpeed = MathUtils.random(0.0F, 50.0F);
                ParticleController.this.setup(x, y, (float)Math.cos((double)randomAngle) * randomSpeed, (float)Math.sin((double)randomAngle) * randomSpeed, 1.2F, 2.0F, 1.8F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.2F);
            }

        }

        public void takePowerUpEffect(float x, float y) {
            for(int i = 0; i < 16; ++i) {
                float angle = 0.3925F * (float)i;
                ParticleController.this.setup(x, y, (float)Math.cos((double)angle) * 100.0F, (float)Math.sin((double)angle) * 100.0F, 0.8F, 3.0F, 2.8F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.5F);
            }

        }
    }
}
