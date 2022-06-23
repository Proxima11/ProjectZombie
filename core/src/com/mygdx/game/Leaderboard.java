package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leaderboard implements Screen {

    final menuScreen game;
    private Texture backgroundGame;
    private Texture backBtn;
    private Texture backBtnTouched;
    public Rectangle backRec;
    OrthographicCamera camera;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter1;
    private BitmapFont font;
    private BitmapFont font1;

    private Music soundtrack;
    

    public Leaderboard(menuScreen game){
        this.game = game;
        backgroundGame = new Texture(Gdx.files.internal("BackgroundA.png"));
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("MusicZombie.mp3"));
        backBtn = new Texture(Gdx.files.internal("arrowistouch.png"));
        backBtnTouched = new Texture(Gdx.files.internal("arrowis!touch.png"));
        backRec =new Rectangle();
        backRec.x = 20;
        backRec.y = 400;
        backRec.width = 80;
        backRec.height = 60;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 45;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLUE;
        fontParameter.color = Color.CHARTREUSE;
        font = fontGenerator.generateFont(fontParameter);

        fontParameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter1.size = 60;
        fontParameter1.borderWidth = 5;
        fontParameter1.borderColor = Color.DARK_GRAY;
        fontParameter1.color = Color.GOLD;
        font1 = fontGenerator.generateFont(fontParameter1);

        soundtrack.setVolume((float) 0.3);
        soundtrack.setLooping(true);
        soundtrack.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScoreBoard scoring = new ScoreBoard() {
            @Override
            public void readFile() {
                BufferedReader reader;
                try{
                    //membaca file leaderboard
                    String lastline = "";
                    int minus = 0;
                    reader = new BufferedReader(new FileReader("FileLeaderBoard.txt"));
                    String line;
                    while((line = reader.readLine()) != null){
                        lastline = line;
                    }

                    String[] getscore = lastline.split(" ");

                    for (int i = 0; i < getscore.length; i++) {
                        font.draw(game.batch, (i+1) + ".  " + getscore[i], 330, 350 + minus);
                        minus -= 50;
                    }

                    reader.close();
                }
                catch (IOException e){
                }
            }
        };

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundGame, 0, 0);

        //tulisan
        font1.draw(game.batch, "PLAYER SCORE", 200, 420);
        scoring.readFile();
        //kembali ke main menu
        if (Gdx.input.getX() < backRec.x + backRec.width && Gdx.input.getX() > backRec.x && 480 - Gdx.input.getY() < backRec.y + backRec.height && 480 - Gdx.input.getY() > backRec.y ) {
            game.batch.draw(backBtn, backRec.x, backRec.y);
            if (Gdx.input.justTouched()){
                game.setScreen(new mainMenuScreen(game));
                soundtrack.dispose();
            }
        }
        else {
            game.batch.draw(backBtnTouched,backRec.x,backRec.y);
        }
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
