package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private BitmapFont font;


    public void readFile(){
        BufferedReader reader;
        try{
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

    public Leaderboard(menuScreen game){
        this.game = game;
        backgroundGame = new Texture(Gdx.files.internal("backgroundMainMenu.png"));
        backBtn = new Texture(Gdx.files.internal("arrowistouch.png"));
        backBtnTouched = new Texture(Gdx.files.internal("arrowis!touch.png"));
        backRec =new Rectangle();
        backRec.x = 20;
        backRec.y = 400;
        backRec.width = 80;
        backRec.height = 60;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
//        backRec = new Rectangle();
//        backRec.x = 800 / 2 - 80 / 2;
//        backRec.y = 480 - (60);
//        backRec.width = 80;
//        backRec.height = 60;

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ZOMBIE.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 35;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = com.badlogic.gdx.graphics.Color.CHARTREUSE;
        fontParameter.color = Color.FOREST;
        font = fontGenerator.generateFont(fontParameter);
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

        readFile();
        
        if (Gdx.input.getX() < backRec.x + backRec.width && Gdx.input.getX() > backRec.x && 480 - Gdx.input.getY() < backRec.y + backRec.height && 480 - Gdx.input.getY() > backRec.y ) {
            game.batch.draw(backBtn, backRec.x, backRec.y);
            if (Gdx.input.isTouched()){
                game.setScreen(new mainMenuScreen(game));
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
