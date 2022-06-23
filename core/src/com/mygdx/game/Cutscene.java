package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class Cutscene implements Screen {

    final menuScreen game;
    private Texture backgroundGame;
    private Rectangle backgroundGameRec;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private BitmapFont font2;

    private Texture citizens;
    private Rectangle citizensRec;
    private Texture police;
    private Rectangle policeRec;
    private Texture barrier;
    private Rectangle barrierRec;

    private Texture yes;
    private Texture yesTouched;
    private Texture no;
    private Texture noTouched;

    private boolean tutorial;
    private int cutsceneStage = 0;

    private Music running;

    public Cutscene(menuScreen game){
        this.game = game;
        create();
    }

    public void create(){
        backgroundGame = new Texture(Gdx.files.internal("background game2.png"));
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

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 12;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.CYAN;
        fontParameter.color = Color.FIREBRICK;
        font = fontGenerator.generateFont(fontParameter);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 6;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font2 = fontGenerator.generateFont(fontParameter);

        citizens = new Texture(Gdx.files.internal("runningcitizen.png"));
        citizensRec = new Rectangle();
        citizensRec.x = 800;
        citizensRec.y = 0;
        citizensRec.width = 150;
        citizensRec.height = 480;
        police = new Texture(Gdx.files.internal("police.png"));
        policeRec = new Rectangle();
        policeRec.x = -64;
        policeRec.y = 40;
        policeRec.width = 64;
        policeRec.height = 64;
        barrier = new Texture(Gdx.files.internal("barrier12.png"));
        barrierRec = new Rectangle();
        barrierRec.x = -50;
        barrierRec.y = 0;
        barrierRec.width = 50;
        barrierRec.height = 320;

        yes = new Texture(Gdx.files.internal("yesButton.png"));
        yesTouched = new Texture(Gdx.files.internal("yesButtonTouched.png"));
        no = new Texture(Gdx.files.internal("noButton.png"));
        noTouched = new Texture(Gdx.files.internal("noButtonTouched.png"));

        running = Gdx.audio.newMusic(Gdx.files.internal("Running Sound Effect.wav"));
        running.setLooping(true);
        running.setVolume((float) 0);
        running.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundGame, backgroundGameRec.x, backgroundGameRec.y);
        if (cutsceneStage == 0){
            font.draw(batch, "DO YOU WANT TO PLAY THE TUTORIAL?", 125, 350);
            if ((Gdx.input.getX() >= 125 && Gdx.input.getX() <= 345) && (Gdx.input.getY() <= 330 && Gdx.input.getY() >= 230)) {
                batch.draw(yesTouched, 125, 150);
                if (Gdx.input.isTouched()){
                    tutorial = true;
                    cutsceneStage++;
                }
            }
            else {
                batch.draw(yes, 125, 150);
            }

            if ((Gdx.input.getX() >= 450 && Gdx.input.getX() <= 670) && (Gdx.input.getY() <= 330 && Gdx.input.getY() >= 230)) {
                batch.draw(noTouched, 450, 150);
                if (Gdx.input.isTouched()){
                    tutorial = false;
                    cutsceneStage++;
                }
            }
            else {
                batch.draw(no, 450, 150);
            }

        }
        else if (cutsceneStage == 1){
            running.setVolume((float) 0.2);
            citizensRec.x -= 200 * Gdx.graphics.getDeltaTime();
            batch.draw(citizens, citizensRec.x, citizensRec.y);
            if (citizensRec.x >= 550 && citizensRec.x <= 700){
                font2.draw(batch, "HELP US", 700, 200);
                font2.draw(batch, "HELP ", 680, 310);
                font2.draw(batch, "HELP ", 710, 110);
            }

            if (citizensRec.x >= 200 && citizensRec.x <= 300)font2.draw(batch, "PLEASE HELP US", 300, 200);
            if (citizensRec.x <= -160){
                cutsceneStage++;
                running.dispose();
            }
        }
        else if (cutsceneStage == 2){
            policeRec.x += 100 * Gdx.graphics.getDeltaTime();
            batch.draw(police, policeRec.x, 40);
            if (policeRec.x >= 20){
                cutsceneStage++;
            }
        }
        else if (cutsceneStage == 3){
            batch.draw(police, 20, 40);
            barrierRec.x += 25 * Gdx.graphics.getDeltaTime();
            batch.draw(barrier, barrierRec.x, barrierRec.y);

            if (barrierRec.x >= -50){
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
                cutsceneStage++;
            }
        }
        else if (cutsceneStage == 4){
            if (tutorial){
                game.setScreen(new TutorialScreen(game));
            }
            else {
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
