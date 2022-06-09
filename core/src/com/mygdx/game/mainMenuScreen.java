package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import sun.applet.Main;

public class mainMenuScreen implements Screen {
    final zombie game;
    OrthographicCamera camera;
    public Texture background

    mainMenuScreen(final zombie game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,480);

    }
    

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw();

        }
    }
}
