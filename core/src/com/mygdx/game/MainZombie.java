package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import org.w3c.dom.css.Rect;
//import sun.applet.Main;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class MainZombie implements Screen {

    final menuScreen game;
    private Texture bullet;
    private Array<Rectangle> bulletSpawn;
    private Array<Peluru> bulletList;
    private Texture backgroundGame;
    private Texture police;
    private Texture zombie;
    private Texture health100;
    private Texture health80;
    private Texture health50;
    private Texture health20;
    private Texture health10;
    private Texture barrier;
    private Texture barrierHeatlh;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle backgroundGameRec;
    private Rectangle policeRec;
    private Rectangle barrierRec;
    private Character polisi;
    private Character base;

    private Array<Rectangle> zombieSpawn;
    private Array<Character> zombieList;
    private long zombieLastSpawn;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    private int score = 0;
    private Array<Integer> scoreList;
    private String dataScore = "";


    MainZombie(menuScreen game){
        this.game = game;
        create();
    }

    public void writeToLeaderboard(int score){
        int a = 0;
        scoreList.add(score);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("FileLeaderBoard.txt"));
            String last = null, line;

            while ((line = reader.readLine()) != null){
                last = line;
            }
            if (last != null) {
                Scanner scan = new Scanner(last);
                while (scan.hasNextInt()) {
                    scoreList.add(scan.nextInt());
                }
            }

        }catch (IOException e){
            System.out.println("File tidak dapat dibaca");
        }

        if(scoreList.size > 0)
        {
            for(int i = 0; i < scoreList.size - 1; i++)
            {
                for(int j = i+1; j < scoreList.size; j++)
                {
                    if(scoreList.get(i) < scoreList.get(j))
                    {
                        int temp = scoreList.get(i);
                        scoreList.set(i, scoreList.get(j));
                        scoreList.set(j, temp);
                    }
                }
            }
        }

        try {
            if (scoreList.size < 5) {
                for (int i = 0; i < scoreList.size; i++) {
                    dataScore += scoreList.get(i) + " ";
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    dataScore += scoreList.get(i) + " ";
                }
            }
            FileWriter file = new FileWriter("FileLeaderBoard.txt", true);
            file.append(dataScore);
            file.append("\n");

            file.close();

        }catch (IOException e){
            System.out.println("File tidak dapat dibaca");
        }
    }

    public void create() {
        zombieSpawn = new Array<Rectangle>();
        backgroundGame = new Texture(Gdx.files.internal("background game2.png"));
        police = new Texture(Gdx.files.internal("police.png"));
        zombie = new Texture(Gdx.files.internal("zombie.png"));
        health100 = new Texture(Gdx.files.internal("health bar 100.png"));
        health80 = new Texture(Gdx.files.internal("health bar 80.png"));
        health50 = new Texture(Gdx.files.internal("health bar 50.png"));
        health20 = new Texture(Gdx.files.internal("health bar 20.png"));
        health10 = new Texture(Gdx.files.internal("health bar 10.png"));
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
        policeRec = new Rectangle();
        policeRec.x = 20;
        policeRec.y = 40;
        policeRec.width = 64;
        policeRec.height = 64;
        barrierRec = new Rectangle();
        barrierRec.x = 0;
        barrierRec.y = 0;
        barrierRec.width = 50;
        barrierRec.height = 320;
        bullet = new Texture(Gdx.files.internal("peluru.png"));
        bulletSpawn = new Array<>();
        bulletList = new Array<>();
        zombieList = new Array<>();

        polisi = new Police(5);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 15;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font = fontGenerator.generateFont(fontParameter);


        base = new Base(10);

        scoreList = new Array<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            pause();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            policeRec.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            policeRec.y -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            policeRec.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            policeRec.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.justTouched()) bulletSpawning();

//            bulletRec.x += 700 * Gdx.graphics.getDeltaTime();

        if (policeRec.y > 250) policeRec.y = 250;
        if (policeRec.y < 0) policeRec.y = 0;
        if (policeRec.x > 150) policeRec.x = 150;
        if (policeRec.x < barrierRec.width) policeRec.x = barrierRec.width ;

        if (TimeUtils.nanoTime() - zombieLastSpawn > 1000999999) zombieSpawning();

        try {
            int index = -1;
            for (Iterator<Rectangle> iter = zombieSpawn.iterator(); iter.hasNext(); ) {

                Rectangle zombiess = iter.next();
                index++;
                if (zombiess.x + 64 < 0) {
                    iter.remove();
                    base.getDamage(1);
                    if (!base.AliveorNot()) {
                        writeToLeaderboard(score);
                        game.setScreen(new gameOver(game));
                    }
                }
                zombiess.x -= 100 * Gdx.graphics.getDeltaTime();
                int bulletIndex = -1;
                for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext(); ) {
                    Rectangle bullets = iterr.next();
                    bulletIndex++;
                    bullets.x += 700 * Gdx.graphics.getDeltaTime();
                    if (bullets.x + 20 > 800) iterr.remove();
                    if (bullets.intersects(zombiess)) {
                        zombieList.get(index).getDamage(bulletList.get(bulletIndex).damaging());
                        if (!zombieList.get(index).AliveorNot()) {
                            iter.remove();
                            score += 25;
                            zombieList.removeIndex(index);
                        }
                        iterr.remove();
                        bulletList.removeIndex(bulletIndex);
                    }
                }
                if (zombiess.intersects(policeRec)) {
                    iter.remove();
                    score += 25;
                    polisi.getDamage();
                    if (!polisi.AliveorNot()) {
                        writeToLeaderboard(score);
                        game.setScreen(new gameOver(game));
                    }

//            if (zombiess.intersects(bulletSpawn.random())) iter.remove();

                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }

        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
//        game.batch.begin();
        batch.begin();
        batch.draw(backgroundGame, backgroundGameRec.x, backgroundGameRec.y);
        batch.draw(barrierHeatlh, 300, 420);
        batch.draw(police, policeRec.x, policeRec.y);
        if(polisi.getHp() == 5)
        {
            batch.draw(health100, 20, 420);
        }
        if(polisi.getHp() == 4)
        {
            batch.draw(health80, 20, 420);
        }
        if(polisi.getHp() == 3)
        {
            batch.draw(health50, 20, 420);
        }
        if(polisi.getHp() == 2)
        {
            batch.draw(health20, 20, 420);
        }
        if(polisi.getHp() == 1)
        {
            batch.draw(health10, 20, 420);
        }
        batch.draw(barrier,barrierRec.x,barrierRec.y,50 ,320);
        for (Rectangle zombies : zombieSpawn) {
            batch.draw(zombie, zombies.x, zombies.y);
        }
        for (Rectangle bullets : bulletSpawn) {
            batch.draw(bullet, bullets.x, bullets.y);
        }

        font.draw(batch, "POIN : " + score, 520, 455 );

        batch.end();
//        game.batch.end();


//		bulletRec = new Rectangle();
//		bulletRec.width = 20;
//		bulletRec.height = 20;
//		bulletRec.x = policeRec.x;
//		bulletRec.y = policeRec.y;

//		polisi = new Police(5);






//    @Override
//    public void show() {
//
//
//    }

    }


    private void bulletSpawning() {
        Rectangle bullets = new Rectangle();
        Peluru peluru = new Peluru();
        bullets.x = policeRec.x + 40;
        bullets.y = policeRec.y + 27;
        bullets.width = 20;
        bullets.height = 20;
        bulletSpawn.add(bullets);
        bulletList.add(peluru);
    }

    public void zombieSpawning() {
        Rectangle zombies = new Rectangle();
        Character zombie1 = new Zombie(100);
        zombies.x = 840;
        zombies.y = MathUtils.random(0, 250);
        zombies.width = 64;
        zombies.height = 64;
        zombieSpawn.add(zombies);
        zombieList.add(zombie1);
        zombieLastSpawn = TimeUtils.nanoTime();
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
