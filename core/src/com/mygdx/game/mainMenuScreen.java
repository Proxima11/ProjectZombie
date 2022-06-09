package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


import java.awt.*;

public class mainMenuScreen implements Screen {
    final menuScreen game;
    OrthographicCamera camera;
    public Texture background;
    public Texture play;
    public Rectangle playRec;

    mainMenuScreen(final menuScreen game) {
        this.game = game;
        background = new Texture(Gdx.files.internal("BackgroundMainMenu.png"));
        play = new Texture(Gdx.files.internal("kayu.png"));



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new MainZombie(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

