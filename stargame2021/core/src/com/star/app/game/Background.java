package com.star.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.screen.utils.Assets;

public class Background {
    private final int STAR_COUNT = 1000;
    private GameController gc;
    private Texture textureCosmos = new Texture("images/bg.png");
    private TextureRegion textureStar = Assets.getInstance().getAtlas().findRegion("star16");
    private Background.Star[] stars;

    public Background(GameController gc) {
        this.gc = gc;
        this.stars = new Background.Star[1000];

        for(int i = 0; i < this.stars.length; ++i) {
            this.stars[i] = new Background.Star();
        }

    }

    public void render(SpriteBatch batch) {
        batch.draw(this.textureCosmos, 0.0F, 0.0F);

        for(int i = 0; i < this.stars.length; ++i) {
            batch.draw(this.textureStar, this.stars[i].position.x - 8.0F, this.stars[i].position.y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, this.stars[i].scale, this.stars[i].scale, 0.0F);
            if (MathUtils.random(0, 300) < 1) {
                batch.draw(this.textureStar, this.stars[i].position.x - 8.0F, this.stars[i].position.y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, this.stars[i].scale * 2.0F, this.stars[i].scale * 2.0F, 0.0F);
            }
        }

    }

    public void update(float dt) {
        for(int i = 0; i < this.stars.length; ++i) {
            this.stars[i].update(dt);
        }

    }

    private class Star {
        private Vector2 position = new Vector2((float)MathUtils.random(-200, 1480), (float)MathUtils.random(-200, 920));
        private Vector2 velocity = new Vector2((float)MathUtils.random(-40, -5), 0.0F);
        private float scale;

        public Star() {
            this.scale = Math.abs(this.velocity.x) / 40.0F * 0.8F;
        }

        public void update(float dt) {
            Vector2 var10000 = this.position;
            var10000.x = (float)((double)var10000.x + ((double)this.velocity.x - (double)Background.this.gc.getHero().getVelocity().x * 0.1D) * (double)dt);
            var10000 = this.position;
            var10000.y = (float)((double)var10000.y + ((double)this.velocity.y - (double)Background.this.gc.getHero().getVelocity().y * 0.1D) * (double)dt);
            if (this.position.x < -200.0F) {
                this.position.x = 1480.0F;
                this.position.y = (float)MathUtils.random(-200, 920);
                this.scale = Math.abs(this.velocity.x) / 40.0F * 0.8F;
            }

        }
    }
}
