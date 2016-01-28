package tk.tapet.android.game.birds.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tk.tapet.android.game.birds.screens.SplashScreen;

public class BackgroundManager {

    private int currentBackground = 0;
    private int nextBackground = -1;
    private int velocityX = 300;

    private ArrayList<Sprite> backgrounds = new ArrayList<Sprite>(6);

    public BackgroundManager() {
        init();
    }

    public void changeBackground() {
        nextBackground = currentBackground + 1;
        if (nextBackground == backgrounds.size()) {
            nextBackground = 0;
        }
        backgrounds.get(nextBackground).setPosition(SplashScreen.VIRTUAL_WIDTH, 0);
    }

    public void draw(SpriteBatch batch) {
        backgrounds.get(currentBackground).draw(batch);
        if (nextBackground >= 0){
            backgrounds.get(nextBackground).draw(batch);
        }
    }

    public void update(float delta) {
        if (nextBackground >= 0) {
            backgrounds.get(nextBackground).setX(backgrounds.get(nextBackground).getX() - velocityX * delta);

            backgrounds.get(currentBackground).setX(backgrounds.get(currentBackground).getX() - velocityX * delta);

            if ((int) backgrounds.get(nextBackground).getX() <= 0) {
                currentBackground = nextBackground;
                nextBackground = -1;
            }
        }


    }

    private void init() {
        currentBackground = 0;
        nextBackground = -1;

        Sprite mainBackground = new Sprite(new Texture(Gdx.files.internal("img/splash_background.jpg")));
        mainBackground.setSize(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT);
        backgrounds.add(mainBackground);
        for (int i = 0; i < 5; i++) {
            Sprite background = new Sprite(new Texture(Gdx.files.internal("img/background" + (i + 1) + ".png")));
            background.setSize(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT);
            backgrounds.add(background);
        }

    }
}
