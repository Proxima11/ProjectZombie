package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    private Music soundtrack;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    public int getScoreHistoryPlayer(){
        int skor = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("HistoryScorePlayer.txt"));
            String line = reader.readLine();
            skor = Integer.parseInt(line);
        }catch (IOException e){e.printStackTrace();}
        return skor;
    }

    gameOver(final menuScreen game) {
        this.game = game;
        gameOver = new Texture(Gdx.files.internal("gameOver.png"));
        playAgain = new Texture(Gdx.files.internal("gameOvPlay.png"));
        playTouch = new Texture(Gdx.files.internal("gameOvPlayTouch.png"));
        mainMenu = new Texture(Gdx.files.internal("gameOvMenu.png"));
        mainMenuTouch = new Texture(Gdx.files.internal("gameOvMenuTouch.png"));
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("369147__inspectorj__music-box-happy-birthday.wav"));

        soundtrack.setLooping(true);
        soundtrack.play();

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

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 15;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = com.badlogic.gdx.graphics.Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font = fontGenerator.generateFont(fontParameter);


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

        font.draw(game.batch, "YOUR SCORE : " + getScoreHistoryPlayer(), 220, 100);

        if (Gdx.input.getX() < playRec.x + playRec.width && Gdx.input.getX()> playRec.x && 480 -Gdx.input.getY()  <  playRec.y + playRec.height && 480 - Gdx.input.getY() >playRec.y){
            game.batch.draw(playAgain, playRec.x, playRec.y);
            if (Gdx.input.isTouched()){
                soundtrack.dispose();
                game.setScreen(new MainZombie(game));
            }
        }
        else{
            game.batch.draw(playTouch,playRec.x,playRec.y);
        }
        if(Gdx.input.getX() < menuRec.x + menuRec.width && Gdx.input.getX() > menuRec.x && 480 - Gdx.input.getY() < menuRec.height + menuRec.y && 480 - Gdx.input.getY() > menuRec.y) {
            game.batch.draw(mainMenu, menuRec.x, menuRec.y);
            if (Gdx.input.isTouched()){
                soundtrack.dispose();
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
