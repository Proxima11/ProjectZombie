package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private Music soundtrack;


    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;


    mainMenuScreen(final menuScreen game) {
        this.game = game;
        background = new Texture("BackgroundA.png");
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("zombie song.mp3"));
        play = new Texture("play!TouchUpdt.png");
        playNotTouch = new Texture("playTouchUpdt.png");
        leaderboard = new Texture("leaderBoardis!touch.png");
        leaderboardNotTouch = new Texture("leaderboardTouchUpdt.png");
        exit = new Texture("exitis!touchupdt.png");
        exitNotTouch = new Texture("exitisTouchupdt.png");
        playRec = new Rectangle();
        playRec.x = 800 / 2 - 200 / 2;
        playRec.y = 480 - (230);
        playRec.width = 200;
        playRec.height = 50;
        playRec2 = new Rectangle();
        playRec2.x = 800 / 2 - 200 / 2;
        playRec2.y = 480 - (300);
        playRec2.width = 200;
        playRec2.height = 50;
        playRec3 = new Rectangle();
        playRec3.x = 800 / 2 - 200 / 2;
        playRec3.y = 480 - (370);
        playRec3.width = 200;
        playRec3.height = 50;


        soundtrack.setVolume((float) 0.3);
        soundtrack.setLooping(true);
        soundtrack.play();



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 60;
        fontParameter.borderWidth = 10;
        fontParameter.borderColor = Color.CHARTREUSE;
        fontParameter.color = Color.FOREST;
        font = fontGenerator.generateFont(fontParameter);

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);

        //ke cutscene
        if (Gdx.input.getX() < playRec.x + playRec.width && Gdx.input.getX() > playRec.x && 480 - Gdx.input.getY() < playRec.y + playRec.height && 480 - Gdx.input.getY() > playRec.y ) {
            game.batch.draw(playNotTouch, playRec.x, playRec.y);
            if(Gdx.input.justTouched()){
                game.setScreen(new Cutscene(game));
                soundtrack.dispose();
                dispose();
            }
        }else{
            game.batch.draw(play, playRec.x, playRec.y);
        }
        //ke leaderboard
        if (Gdx.input.getX() < playRec2.x + playRec2.width && Gdx.input.getX() > playRec2.x && 480 - Gdx.input.getY() < playRec2.y + playRec2.height && 480 - Gdx.input.getY() > playRec2.y){
            game.batch.draw(leaderboardNotTouch,playRec2.x, playRec2.y);
            if (Gdx.input.justTouched()) {
                game.setScreen(new Leaderboard(game));
                soundtrack.dispose();
            }
        }
        else {
            game.batch.draw(leaderboard, playRec2.x,playRec2.y);
        }
        //keluar game
        if (Gdx.input.getX() < playRec3.x + playRec3.width && Gdx.input.getX() > playRec3.x && 480 - Gdx.input.getY() < playRec3.y + playRec3.height && 480 - Gdx.input.getY() > playRec3.y){
            game.batch.draw(exitNotTouch, playRec3.x, playRec3.y);
            if (Gdx.input.justTouched()){
                soundtrack.dispose();
                Gdx.app.exit();
            }
        }
        else {
            game.batch.draw(exit, playRec3.x, playRec3.y);
        }

        font.draw(game.batch, "ZOMBIE DEFENDER", 100, 390);

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

