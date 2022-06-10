package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Achievement implements Screen {

    final menuScreen game;
    private Texture backgroundGame;
    private Texture backBtn;
    private Texture backBtnTouched;
    public Rectangle backRec;
    OrthographicCamera camera;

    public Achievement(menuScreen game){
        this.game = game;
        backgroundGame = new Texture(Gdx.files.internal("backgroundMainMenu.png"));
        backBtn = new Texture(Gdx.files.internal("arrowistouch.png"));
        backBtnTouched = new Texture(Gdx.files.internal("arrowis!touch.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
//        backRec = new Rectangle();
//        backRec.x = 800 / 2 - 80 / 2;
//        backRec.y = 480 - (60);
//        backRec.width = 80;
//        backRec.height = 60;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundGame, 0, 0);
        
//        if (Gdx.input.getX() < backRec.x + backRec.width && Gdx.input.getX() > backRec.x && 480 - Gdx.input.getY() < backRec.y + backRec.height && 480 - Gdx.input.getY() > backRec.y ) {
//            game.batch.draw(backBtn, backRec.x, backRec.y);
//        }
        game.batch.end();
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
