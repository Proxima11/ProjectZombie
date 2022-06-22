package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;
import java.util.Iterator;

public class TutorialScreen implements Screen {

    private int tutorialStage = 0;
    boolean drawEnter = true;
    boolean marker1Reached = false;
    boolean marker2Reached = false;
    int zombieDamaged = 0;

    final menuScreen game;
    private Texture backgroundGame;
    private Rectangle backgroundGameRec;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture leftArrow;
    private Texture downArrow;
    private Texture rightArrow;
    private Texture upArrow;
    private Texture marker;
    private Texture wasd;
    private Texture updownleftright;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private BitmapFont font2;

    private Texture bullet;
    private Array<Rectangle> bulletSpawn;
    private Array<Peluru> bulletList;

    private Texture barrier;
    private Texture barrierHeatlh;
    private Rectangle barrierRec;

    private Texture health;
    private Rectangle policeRec;
    private Texture police;
    private Character polisi;
    private Character base;
    private Texture zombie;
    private Music soundtrack;

    public TutorialScreen(menuScreen game){
        this.game = game;
        create();
    }

    public void create(){
        backgroundGame = new Texture(Gdx.files.internal("background game2.png"));
        police = new Texture(Gdx.files.internal("police.png"));
        zombie = new Texture(Gdx.files.internal("standingZombie.png"));
        health = new Texture(Gdx.files.internal("health bar 100.png"));
        barrier = new Texture(Gdx.files.internal("barrier12.png"));
        barrierHeatlh = new Texture(Gdx.files.internal("baseHealthBar.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        backgroundGameRec = new Rectangle();
        backgroundGameRec.x = 0;
        backgroundGameRec.y = 0;
        backgroundGameRec.width = 800;
        backgroundGameRec.height = 480;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        leftArrow = new Texture(Gdx.files.internal("leftArrow.png"));
        downArrow = new Texture(Gdx.files.internal("downArrow.png"));
        rightArrow = new Texture(Gdx.files.internal("rightArrow.png"));
        upArrow = new Texture(Gdx.files.internal("upArrow.png"));
        marker = new Texture(Gdx.files.internal("marker.png"));
        wasd = new Texture(Gdx.files.internal("wasd.png"));
        updownleftright = new Texture(Gdx.files.internal("updownleftright.png"));


        //create jenis font
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 12;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = com.badlogic.gdx.graphics.Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font = fontGenerator.generateFont(fontParameter);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 6;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = com.badlogic.gdx.graphics.Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font2 = fontGenerator.generateFont(fontParameter);

        //create polisi
        police = new Texture(Gdx.files.internal("police.png"));
        policeRec = new Rectangle();
        policeRec.x = 20;
        policeRec.y = 40;
        policeRec.width = 64;
        policeRec.height = 64;
        polisi = new Police(5);

        //create tampilan dan darah base
        barrier = new Texture(Gdx.files.internal("barrier12.png"));
        barrierHeatlh = new Texture(Gdx.files.internal("baseHealthBar.png"));
        barrierRec = new Rectangle();
        barrierRec.x = 0;
        barrierRec.y = 0;
        barrierRec.width = 50;
        barrierRec.height = 320;
        base = new Base(10);

        //create peluru
        bullet = new Texture(Gdx.files.internal("peluru.png"));
        bulletSpawn = new Array<>();
        bulletList = new Array<>();


        //sound untuk in game
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("mainZombie.mp3"));
        soundtrack.setVolume((float) 0.2);
        soundtrack.setLooping(true);
        soundtrack.play();

        //power up double damage
        //x2 = new Texture(Gdx.files.internal("x2.png"));
        //x2spawn = new Array<Rectangle>();
    }

    public void policeMovement(){

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            policeRec.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            policeRec.y -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            policeRec.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            policeRec.x -= 200 * Gdx.graphics.getDeltaTime();

        if (policeRec.y > 250) policeRec.y = 250;
        if (policeRec.y < 0) policeRec.y = 0;
        if (policeRec.x > 150) policeRec.x = 150;
        if (policeRec.x < barrierRec.width) policeRec.x = barrierRec.width;
    }

    public void bulletSpawning(){
        Rectangle bullets = new Rectangle();
        Peluru peluru = new Peluru();
        bullets.x = policeRec.x + 40;
        bullets.y = policeRec.y + 27;
        bullets.width = 20;
        bullets.height = 20;
        bulletSpawn.add(bullets);
        bulletList.add(peluru);
    }

    public void bulletShot(){



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (tutorialStage >= 6 && tutorialStage < 9) {
            policeMovement();
        }
        if (tutorialStage >= 7 && tutorialStage < 9){
            if (Gdx.input.justTouched()) bulletSpawning();
            for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext(); ) {
                Rectangle bullets = iterr.next();
                bullets.x += 400 * Gdx.graphics.getDeltaTime();
                if (bullets.x + 20 > 800) iterr.remove();
            }
        }

        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundGame, backgroundGameRec.x, backgroundGameRec.y);
        if (tutorialStage<=11) {
            batch.draw(barrierHeatlh, 300, 420);
            batch.draw(barrier, barrierRec.x, barrierRec.y, 50, 320);
            font.draw(batch, "X" + base.getHp(), 340, 460);
            batch.draw(police, policeRec.x, policeRec.y);
            batch.draw(health, 20, 420);
        }

        if (tutorialStage <= 10 && drawEnter) {
            font2.draw(batch, "PRESS ENTER TO CONTINUE", 250, 35);
        }

        if (tutorialStage == 0){
            font.draw(batch, "WELCOME TO \nZOMBIE DEFENDER", 350, 250);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
                tutorialStage++;
            }
        }
        else if (tutorialStage == 1){
            font.draw(batch, "THIS IS THE BARRIER \nTHAT NEED TO BE \nDEFENDED", 350, 250);
            batch.draw(leftArrow, 60, 250);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 2){
            font.draw(batch, "THIS IS THE HEALTH\nOF THE BARRIER", 350, 250);
            batch.draw(upArrow, 320, 360);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 3){
            font.draw(batch, "THIS IS YOU,\nTHE POLICE", 350, 250);
            batch.draw(downArrow, 40, 100);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 4){
            font.draw(batch, "THIS IS\nYOUR HEALTH BAR", 350, 250);
            batch.draw(upArrow, 30, 260);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 5){
            font.draw(batch, "YOU CAN MOVE THE POLICE\nWITH THESE KEYS", 275, 300);
            batch.draw(wasd,400, 150 );
            batch.draw(updownleftright, 550, 150);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 6){
            drawEnter = false;
            font.draw(batch, "TRY MOVING TO THE MARKERS", 275, 300);
            font2.draw(batch, "TOUCH THE MARKERS TO CONTINUE", 220, 35);
            if ((policeRec.x <= 78 || policeRec.x >= 82) && (policeRec.y <= 208 || policeRec.y>=212) && !marker1Reached) {
                batch.draw(marker, 80, 210);
            }
            else {
                marker1Reached = true;
            }
            if ((policeRec.x <= 98 || policeRec.x >= 102) && (policeRec.y <= 48 || policeRec.y>= 52) && !marker2Reached) {
                batch.draw(marker, 100, 50);
            }
            else {
                marker2Reached = true;
            }
            if (marker1Reached && marker2Reached){
                drawEnter = true;
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 7){
            font.draw(batch, "YOU CAN SHOOT BULLETS\nBY CLICKING THE SCREEN", 275, 300);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 8){
            drawEnter = false;
            font.draw(batch, "THERE IS A ZOMBIE IN THE FIELD, KILL IT!", 60, 380);
            font2.draw(batch, "KILL THE ZOMBIE TO CONTINUE", 230, 35);
            batch.draw(zombie, 700, 150);


            // if (zombieDamaged == 4)
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                drawEnter = true;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 9){
            font.draw(batch, "NICE JOB POLICE", 400, 300);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 10){
            font.draw(batch, "IN WAR, ZOMBIES WON'T STAND STILL\nMOVE AROUND AND SHOOT THEM!", 160, 300);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 11){
            font.draw(batch, "GOOD LUCK POLICE!", 400, 300);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                tutorialStage++;
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
            }
        }
        else if (tutorialStage == 12){
            font.draw(batch, "PRESS ENTER TO ENTER THE BATTLEFIELD!", 30, 240);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                dispose();
                soundtrack.dispose();
                game.setScreen(new MainZombie(game));
            }
        }

        batch.end();
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
