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
    public Texture playNotTouch;
    public Texture leaderboard;
    public Texture leaderboardNotTouch;
    public Texture exit;
    public Texture exitNotTouch;
    public Rectangle playRec;
    public Rectangle playRec2;
    public Rectangle playRec3;

    mainMenuScreen(final menuScreen game) {
        this.game = game;
        background = new Texture("BackgroundMainMenu.png");
        play = new Texture("playIs!touch.png");
        playNotTouch = new Texture("playIsTouch.png");
        leaderboard = new Texture("leadearboardis!Touch.png");
        leaderboardNotTouch = new Texture("leadearboardisTouch.png");
        exit = new Texture("exitis!Touch.png");
        exitNotTouch = new Texture("exitistouch.png");
        playRec = new Rectangle();
        playRec.x = 800 / 2 - 200 / 2;
        playRec.y = 480 - (160);
        playRec.width = 200;
        playRec.height = 100;
        playRec2 = new Rectangle();
        playRec2.x = 800 / 2 - 200 / 2;
        playRec2.y = 480 - (290);
        playRec2.width = 200;
        playRec2.height = 100;
        playRec3 = new Rectangle();
        playRec3.x = 800 / 2 - 200 / 2;
        playRec3.y = 480 - (420);
        playRec3.width = 200;
        playRec3.height = 100;




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
        if (Gdx.input.getX() < playRec.x + playRec.width && Gdx.input.getX() > playRec.x && 480 - Gdx.input.getY() < playRec.y + playRec.height && 480 - Gdx.input.getY() > playRec.y ) {
            game.batch.draw(playNotTouch, playRec.x, playRec.y);
        }else{
            game.batch.draw(play, playRec.x, playRec.y);
        }
        if (Gdx.input.getX() < playRec2.x + playRec2.width && Gdx.input.getX() > playRec2.x && 480 - Gdx.input.getY() < playRec2.y + playRec2.height && 480 - Gdx.input.getY() > playRec2.y){
            game.batch.draw(leaderboardNotTouch,playRec2.x, playRec2.y);
        }
        else {
            game.batch.draw(leaderboard, playRec2.x,playRec2.y);
        }
        if (Gdx.input.getX() < playRec3.x + playRec3.width && Gdx.input.getX() > playRec3.x && 480 - Gdx.input.getY() < playRec3.y + playRec3.height && 480 - Gdx.input.getY() > playRec3.y){
            game.batch.draw(exitNotTouch, playRec3.x, playRec3.y);
        }
        else {
            game.batch.draw(exit, playRec3.x, playRec3.y);
        }
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

