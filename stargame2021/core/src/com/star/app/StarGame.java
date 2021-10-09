package com.star.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.ScreenManager.ScreenType;

public class StarGame extends Game {
    private SpriteBatch batch;

    public StarGame() {
    }

    public void create() {
        this.batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, this.batch);
        ScreenManager.getInstance().changeScreen(ScreenType.MENU);
    }

    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        this.getScreen().render(dt);
    }

    public void dispose() {
        this.batch.dispose();
    }
}
