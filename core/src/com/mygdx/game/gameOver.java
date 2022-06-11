package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class gameOver implements Screen {
    final menuScreen game;
    private Texture gameOver;
    private Texture playAgain;
    private Texture mainMenu;
    private Texture playTouch;
    private Texture mainMenuTouch;
    private OrthographicCamera camera;
    private Rectangle playRec;
    private Rectangle menuRec;



    gameOver(final menuScreen game) {
        this.game = game;
        gameOver = new Texture(Gdx.files.internal("gameOver.png"));
        playAgain = new Texture(Gdx.files.internal("gameOvPlay.png"));
        playTouch = new Texture(Gdx.files.internal("gameOvPlayTouch.png"));
        mainMenu = new Texture(Gdx.files.internal("gameOvMenu.png"));
        mainMenuTouch = new Texture(Gdx.files.internal("gameOvMenuTouch.png"));

        playRec = new Rectangle();
        playRec.x = 140;
        playRec.y = 100;
        playRec.width = 250;
        playRec.height = 100;
        menuRec = new Rectangle();
        menuRec.x = 450;
        menuRec.y = 100;
        menuRec.width = 250;
        menuRec.height = 100;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(gameOver,0,0);

        if (Gdx.input.getX() < playRec.x + playRec.width && Gdx.input.getX()> playRec.x && 480 -Gdx.input.getY()  <  playRec.y + playRec.height && 480 - Gdx.input.getY() >playRec.y){
            game.batch.draw(playAgain, playRec.x, playRec.y);
            if (Gdx.input.isTouched()){
                game.setScreen(new MainZombie(game));
            }
        }
        else{
            game.batch.draw(playTouch,playRec.x,playRec.y);
        }
        if(Gdx.input.getX() < menuRec.x + menuRec.width && Gdx.input.getX() > menuRec.x && 480 - Gdx.input.getY() < menuRec.height + menuRec.y && 480 - Gdx.input.getY() > menuRec.y) {
            game.batch.draw(mainMenu, menuRec.x, menuRec.y);
            if (Gdx.input.isTouched()){
                game.setScreen(new mainMenuScreen(game));
            }
        }
        else{
            game.batch.draw(mainMenuTouch,menuRec.x,menuRec.y);
        }

        game.batch.end();


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
