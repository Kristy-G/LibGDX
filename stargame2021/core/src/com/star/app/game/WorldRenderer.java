package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.star.app.screen.utils.Assets;

public class WorldRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private BitmapFont font32;
    private java.lang.StringBuilder stringBuilder;

    public WorldRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.font32 = (BitmapFont)Assets.getInstance().getAssetManager().get("fonts/font32.ttf", BitmapFont.class);
        this.stringBuilder = new StringBuilder();
    }

    public void render() {
        ScreenUtils.clear(0.0F, 0.2F, 0.5F, 1.0F);
        this.batch.begin();
        this.gc.getBackground().render(this.batch);
        this.gc.getAsteroidController().render(this.batch);
        this.gc.getHero().render(this.batch);
        this.gc.getBulletController().render(this.batch);
        this.gc.getPowerUpsController().render(this.batch);
        this.gc.getParticleController().render(this.batch);
        this.gc.getHero().renderGUI(this.batch, this.font32);
        this.batch.end();
    }
}
