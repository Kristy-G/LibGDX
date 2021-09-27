package com.star.app.game;

public class GameController {
    private Background background;
    private Hero hero;
    private AsteroidController asteroidController;
    private BulletController bulletController;

    public BulletController getBulletController() {
        return bulletController;
    }

    public Hero getHero() {
        return hero;
    }

    public Background getBackground() {
        return background;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public GameController() {
        this.background = new Background(this);
        this.hero = new Hero(this);
        this.asteroidController = new AsteroidController();
        this.bulletController = new BulletController();
    }

    public void update(float dt) {
        background.update(dt);
        asteroidController.update(dt);
        hero.update(dt);
        bulletController.update(dt);
        if (asteroidController.getActiveList().size() < 2) {
            asteroidController.setup(0, 0);
        }



        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);
            for (int j = 0; j < asteroidController.getActiveList().size(); j++)
            {
                Asteroid a = asteroidController.getActiveList().get(j);
                if (a.getPosition().dst(b.getPosition()) < 32.0f) {
                    b.deactivate();
                    a.deactivate();
                }
            }
        }
    }
}
