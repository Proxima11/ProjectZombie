package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
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
//import org.w3c.dom.css.Rect;
//import sun.applet.Main;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainZombie implements Screen {
    //game
    final menuScreen game;
    private Texture backgroundGame;
    private Rectangle backgroundGameRec;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private boolean pause;

    //variable untuk tampilan tulisan atau text
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    //variable untuk lagu
    private Music levelUp;
    private Music soundtrack;

    //create variable untuk peluru
    private Texture bullet;
    private Array<Rectangle> bulletSpawn;
    private Array<Peluru> bulletList;

    //create variabel untuk barrier base
    private Texture barrier;
    private Texture barrierHeatlh;
    private Rectangle barrierRec;

    //create variable untuk darah polisi dan gambar polisi yang digerakkan player
    private Texture health100;
    private Texture health80;
    private Texture health50;
    private Texture health20;
    private Texture health10;
    private Rectangle policeRec;
    private Texture police;
    private Character polisi;
    private Character base;

    //create variable untuk zombie
    private int temp = 1;
    private int temp2 = 1;
    private double kecepatanZomb = 0;
    private Texture zombie;
    private Texture zombieRed;
    private Texture zombiePolice;
    private Array<Rectangle> zombieSpawn;
    private Array<Character> zombieList;
    private long zombieLastSpawn;

    //create variable untuk score dan menampilkan score
    private int score = 0;
    private Array<Integer> scoreList;
    private String dataScore = "";

    //create variable untuk bom
    private Texture bom;
    private Array<Rectangle> bomSpawn;
    private long bomLastSpawn;
    private boolean bomActivate;
    private Music soundBom;

    // texture dan background untuk pause state
    private Texture backgroundPause;
    private Texture resumeButton;
    private Texture resumeButtonTouched;
    private Texture mainMenuButton;
    private Texture mainMenuButtonTouched;

    // damage
    private Texture damage;
    private Array<Rectangle> damageSpawn;
    private Array<Damage> damageList;
    private static final int GRAVITY = 20;
    private static final int PUSH = 60;
    private ArrayList<Boolean> damageJump;
    private Array<Double> baseY;

    MainZombie(menuScreen game) {
        this.game = game;
        create();
    }

    public void create() {
        //create dasar game
        backgroundGame = new Texture(Gdx.files.internal("background game2.png"));
        police = new Texture(Gdx.files.internal("police.png"));
        zombie = new Texture(Gdx.files.internal("zombie.png"));
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

        //create jenis font
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Jelly Crazies.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 15;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.FIREBRICK;
        fontParameter.color = Color.GREEN;
        font = fontGenerator.generateFont(fontParameter);

        //create zombie dab array simpannya
        zombieSpawn = new Array<Rectangle>();
        zombie = new Texture(Gdx.files.internal("zombie.png"));
        zombieRed = new Texture(Gdx.files.internal("redZombie.png"));
        zombiePolice = new Texture(Gdx.files.internal("policeZombie.png"));
        zombieList = new Array<>();

        //create tampilan darah polisi
        health100 = new Texture(Gdx.files.internal("health bar 100.png"));
        health80 = new Texture(Gdx.files.internal("health bar 80.png"));
        health50 = new Texture(Gdx.files.internal("health bar 50.png"));
        health20 = new Texture(Gdx.files.internal("health bar 20.png"));
        health10 = new Texture(Gdx.files.internal("health bar 10.png"));

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

        //array score
        scoreList = new Array<>();

        //sound untuk level up
        levelUp = Gdx.audio.newMusic(Gdx.files.internal("442943__qubodup__level-up.wav"));
        levelUp.setLooping(false);

        //sound untuk in game
        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("mainZombie.mp3"));
        soundtrack.setVolume((float) 0.2);
        soundtrack.setLooping(true);
        soundtrack.play();

        //bom
        bom = new Texture(Gdx.files.internal("bomb.png"));
        bomSpawn = new Array<Rectangle>();
        bomActivate = false;
        soundBom = Gdx.audio.newMusic(Gdx.files.internal("suaraBom.mp3"));
        soundBom.setVolume((float) 0.2);
        soundBom.setLooping(false);


        // pause states
        backgroundPause = new Texture(Gdx.files.internal("BackgroundMainMenu.png"));
        resumeButton = new Texture(Gdx.files.internal("resumeButton.png"));
        resumeButtonTouched = new Texture(Gdx.files.internal("resumeButtonTouched.png"));
        mainMenuButton = new Texture(Gdx.files.internal("mainMenuButton.png"));
        mainMenuButtonTouched = new Texture(Gdx.files.internal("mainMenuButtonTouched.png"));

        // damage
        damage = new Texture(Gdx.files.internal("damage.png"));
        damageSpawn = new Array<>();
        damageList = new Array<>();
        damageJump = new ArrayList<>();
        baseY = new Array<>();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //anonymous untuk fungsi write leaderboard
        ScoringBoard scoring = new ScoringBoard() {
            @Override
            public void writeToLeaderBoard(int score) {
                scoreList.add(score);

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("FileLeaderBoard.txt"));
                    String line;

                    line = reader.readLine();
                    if (line != null) {
                        Scanner scan = new Scanner(line);
                        while (scan.hasNextInt()) {
                            scoreList.add(scan.nextInt());
                        }
                    }

                } catch (IOException e) {
                    System.out.println("File tidak dapat dibaca");
                }

                if (scoreList.size > 0) {
                    for (int i = 0; i < scoreList.size - 1; i++) {
                        for (int j = i + 1; j < scoreList.size; j++) {
                            if (scoreList.get(i) < scoreList.get(j)) {
                                int temp = scoreList.get(i);
                                scoreList.set(i, scoreList.get(j));
                                scoreList.set(j, temp);
                            }
                        }
                    }
                }
                //hanya membaca score 5 tertinggi
                try {
                    if (scoreList.size < 5) {
                        for (int i = 0; i < scoreList.size; i++) {
                            dataScore += scoreList.get(i) + " ";
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {
                            dataScore += scoreList.get(i) + " ";
                        }
                    }
                    FileWriter file = new FileWriter("FileLeaderBoard.txt");
                    file.append(dataScore);

                    file.close();

                } catch (IOException e) {
                    System.out.println("File tidak dapat dibaca");
                }
            }
        };


        if (!pause) {
            //kondisi untuk pause
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
                pause = true;
            }

            //gerak W A S D untuk karakter polisi (W untuk maju, A untuk ke kiri, S untuk mundur, D untuk ke kanan)
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
                policeRec.y += 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
                policeRec.y -= 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                policeRec.x += 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                policeRec.x -= 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.justTouched()) bulletSpawning();
        }
        else {
            //kondisi untuk unpause
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
                pause = false;
            }
        }

        //batas dari gerak karakter polisi dengan input keyboard
        if (policeRec.y > 250) policeRec.y = 250;
        if (policeRec.y < 0) policeRec.y = 0;
        if (policeRec.x > 150) policeRec.x = 150;
        if (policeRec.x < barrierRec.width) policeRec.x = barrierRec.width;

        //kondisi spawn zombie
        if (!pause) {
            if (TimeUtils.nanoTime() - zombieLastSpawn > 1999999999) zombieSpawning();
        }

        //kondisi spawn bom
        if (!pause) {
            if(TimeUtils.millis() - bomLastSpawn > 50000) bomSpawning();
        }

        if (!pause) {
            try {
                //kondisi cek untuk bom yang collision dengan karakter polisi maka akan menuju class game over
                for(Iterator<Rectangle> itter = bomSpawn.iterator(); itter.hasNext();){
                    Rectangle power = itter.next();
                    power.x -= (60) * Gdx.graphics.getDeltaTime();
                    if(power. x + 64 < 0) itter.remove();
                    if(power.intersects(policeRec))
                    {
                        itter.remove();
                        bomActivate = true;
                        soundBom.play();
                    }
                }
                //kondisi cek untuk bom yang collision dengan karakter polisi maka akan menuju class game ove
                if(bomActivate)
                {
                    for (int i = 0; i < polisi.getHp(); i++){
                        polisi.getDamage();
                    }
                    if (!polisi.AliveorNot()) {
                        scoring.writeToLeaderBoard(score);
                        game.setScreen(new gameOver(game));
                        soundtrack.dispose();
                    }
                }

                //kondisi cek zombie
                int index = -1;
                for (Iterator<Rectangle> iter = zombieSpawn.iterator(); iter.hasNext(); ) {
                    Rectangle zombiess = iter.next();
                    index++;
                    //ketika keluar dari layar di kiri maka darah base akan berkurang
                    if (zombiess.x + 64 < 0) {
                        zombieList.removeIndex(index);
                        iter.remove();
                        base.getDamage(1);
                        //cek ketika base sudah berdarah 0 atau belum
                        if (!base.AliveorNot()) {
                            scoring.writeToLeaderBoard(score);
                            game.setScreen(new gameOver(game));
                            soundtrack.dispose();
                        }
                    }
                    //spawn dan remove damage ketika peluru hit zombie
                    Iterator<Double> itterY = baseY.iterator();
                    for (Iterator<Rectangle> iterrD = damageSpawn.iterator(); iterrD.hasNext();){
                        Rectangle damages = iterrD.next();
                        double minY = itterY.next();
                        damages.x += PUSH * Gdx.graphics.getDeltaTime();
                        damages.y -= GRAVITY * Gdx.graphics.getDeltaTime();
                        if (damages.y < minY ) iterrD.remove();
                    }
                    //kondisi zombie ketika score melebihi 1000
                    if (score > 1000) {
                        kecepatanZomb += 0.01;
                        //spawn suara
                        if (temp2 == 1) {
                            levelUp.play();
                            temp2++;
                        }
                        //spawn kondisi random zombie 2
                        if (TimeUtils.nanoTime() - zombieLastSpawn > 1000999999) randomZombie2();
                        //gerak zombie ke kiri
                        zombiess.x -= (80 + (int) kecepatanZomb) * Gdx.graphics.getDeltaTime();
                        int bulletIndex = -1;
                        //spawn bullet
                        for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext(); ) {
                            Rectangle bullets = iterr.next();
                            bulletIndex++;
                            //gerak zombie ke kanan
                            bullets.x += 600 * Gdx.graphics.getDeltaTime();
                            if (bullets.x + 20 > 800) iterr.remove();
                            //kondisi ketika peluru intersect zombie
                            if (bullets.intersects(zombiess)) {
                                damageSpawn(bullets.x, bullets.y);
                                zombieList.get(index).getDamage(bulletList.get(bulletIndex).getDamage());
                                if (!zombieList.get(index).AliveorNot()) {
                                    score += zombieList.get(index).getScore();
                                    zombieList.removeIndex(index);
                                    iter.remove();
                                }
                                bulletList.removeIndex(bulletIndex);
                                iterr.remove();
                            }
                        }
                        //ketika zombie collision dengan polisi akan mengurangi darah polisi
                        if (zombiess.intersects(policeRec)) {
                            zombieList.removeIndex(index);
                            iter.remove();
                            score += 25;
                            polisi.getDamage();
                            //ketika polisi berdarah 0
                            if (!polisi.AliveorNot()) {
                                scoring.writeToLeaderBoard(score);
                                game.setScreen(new gameOver(game));
                                soundtrack.dispose();
                            }

                        }
                    }
                    //kondisi zombie ketika score berada diantara 500 keatas sampai 1000
                    if (score > 500 && score <= 1000) {
                        //spawn lagu
                        if (temp == 1) {
                            levelUp.play();
                            temp++;
                        }
                        //spawn random zombie 1
                        if (TimeUtils.nanoTime() - zombieLastSpawn > 900000000) randomZombie1();
                        //gerak zombie
                        zombiess.x -= 80 * Gdx.graphics.getDeltaTime();
                        //cek zombie collasion dengan peluru
                        int bulletIndex = -1;
                        for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext(); ) {
                            Rectangle bullets = iterr.next();
                            bulletIndex++;
                            bullets.x += 400 * Gdx.graphics.getDeltaTime();
                            if (bullets.x + 20 > 800) iterr.remove();
                            if (bullets.intersects(zombiess)) {
                                damageSpawn(bullets.x, bullets.y);
                                zombieList.get(index).getDamage(bulletList.get(bulletIndex).getDamage());
                                if (!zombieList.get(index).AliveorNot()) {
                                    score += zombieList.get(index).getScore();
                                    zombieList.removeIndex(index);
                                    iter.remove();
                                }
                                bulletList.removeIndex(bulletIndex);
                                iterr.remove();
                            }
                        }
                        //kondisi ketika zombie collasion dengan polisi
                        if (zombiess.intersects(policeRec)) {
                            zombieList.removeIndex(index);
                            iter.remove();
                            score += 25;
                            polisi.getDamage();
                            //ketika polisi sudah berdarah 0
                            if (!polisi.AliveorNot()) {
                                scoring.writeToLeaderBoard(score);
                                game.setScreen(new gameOver(game));
                                soundtrack.dispose();
                            }

                        }
                    }
                    //kondisi zombie ketika score berada diantara 0 keatas sampai 500
                    if (score >= 0 && score <= 500) {
                        //spawn zombie
                        if (TimeUtils.nanoTime() - zombieLastSpawn > 800000000) zombieSpawning();
                        //gerak zombie
                        zombiess.x -= 40 * Gdx.graphics.getDeltaTime();
                        //ketika keluar dari layar di kiri maka darah base akan berkurang
                        if (zombiess.x == 0) {
                            zombieList.removeIndex(index);
                            iter.remove();
                            base.getDamage(1);
                            //cek ketika base sudah berdarah 0 atau belum
                            if (!base.AliveorNot()) {
                                scoring.writeToLeaderBoard(score);
                                game.setScreen(new gameOver(game));
                                soundtrack.dispose();
                            }
                        }
                        //cek kondisi peluru yang collasion dengan zombie
                        int bulletIndex = -1;
                        for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext(); ) {
                            Rectangle bullets = iterr.next();
                            bulletIndex++;
                            bullets.x += 200 * Gdx.graphics.getDeltaTime();
                            //ketika melewati batas layar
                            if (bullets.x + 20 > 800) iterr.remove();
                            //kondisi ketika peluru collasion dengan zombie
                            if (bullets.intersects(zombiess)) {
                                damageSpawn(bullets.x, bullets.y);
                                zombieList.get(index).getDamage(bulletList.get(bulletIndex).getDamage());
                                //kondisi ketika zombie berdarah 0
                                if (!zombieList.get(index).AliveorNot()) {
                                    score += zombieList.get(index).getScore();
                                    zombieList.removeIndex(index);
                                    iter.remove();
                                }
                                bulletList.removeIndex(bulletIndex);
                                iterr.remove();
                            }

                        }
                        //kondisi ketika zombie collasion dengan polisi
                        if (zombiess.intersects(policeRec)) {
                            zombieList.removeIndex(index);
                            iter.remove();
                            score += 25;
                            polisi.getDamage();
                            //kondisi ketika polisi berdarah 0
                            if (!polisi.AliveorNot()) {
                                scoring.writeToLeaderBoard(score);
                                game.setScreen(new gameOver(game));
                                soundtrack.dispose();
                            }

                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        //menggambar bagian - bagian dari screen
        ScreenUtils.clear(0.2f, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundGame, backgroundGameRec.x, backgroundGameRec.y);
        //kondisi tampilan darah base
        batch.draw(barrierHeatlh, 300, 420);
        font.draw(batch, "X" + base.getHp(), 340, 460);
        batch.draw(police, policeRec.x, policeRec.y);
        //kondisi tampilan darah polisi
        if (polisi.getHp() == 5) {
            batch.draw(health100, 20, 420);
        }
        if (polisi.getHp() == 4) {
            batch.draw(health80, 20, 420);
        }
        if (polisi.getHp() == 3) {
            batch.draw(health50, 20, 420);
        }
        if (polisi.getHp() == 2) {
            batch.draw(health20, 20, 420);
        }
        if (polisi.getHp() == 1) {
            batch.draw(health10, 20, 420);
        }
        batch.draw(barrier, barrierRec.x, barrierRec.y, 50, 320);
        //kondisi tampilan zombie
        for (Rectangle zombies : zombieSpawn) {
            if (zombies.getWidth() == 64 && zombies.getHeight() == 64){
                batch.draw(zombie, zombies.x, zombies.y);
            }
            if (zombies.getWidth() == 65 && zombies.getHeight() == 65){
                batch.draw(zombiePolice, zombies.x, zombies.y);
            }
            if (zombies.getWidth() == 66 && zombies.getHeight() == 66){
                batch.draw(zombieRed, zombies.x, zombies.y);
            }
        }
        //kondisi tampilan bom
        for(Rectangle bomm : bomSpawn)
        {
                batch.draw(bom,bomm.x, bomm.y);
        }
        //kondisi tampilan peluru
        for (Rectangle bullets : bulletSpawn) {
            batch.draw(bullet, bullets.x, bullets.y);
        }
        //kondisi tampilan damage
        for (Rectangle damages : damageSpawn) {
            batch.draw(damage, damages.x, damages.y);
        }

        if (!pause) {
            batch.draw(backgroundGame, backgroundGameRec.x, backgroundGameRec.y);
            batch.draw(barrierHeatlh, 300, 420);
            font.draw(batch, "X" + base.getHp(), 340, 460);
            batch.draw(police, policeRec.x, policeRec.y);
            if (polisi.getHp() == 5) {
                batch.draw(health100, 20, 420);
            }
            if (polisi.getHp() == 4) {
                batch.draw(health80, 20, 420);
            }
            if (polisi.getHp() == 3) {
                batch.draw(health50, 20, 420);
            }
            if (polisi.getHp() == 2) {
                batch.draw(health20, 20, 420);
            }
            if (polisi.getHp() == 1) {
                batch.draw(health10, 20, 420);
            }
            batch.draw(barrier, barrierRec.x, barrierRec.y, 50, 320);
            for (Rectangle zombies : zombieSpawn) {
                if (zombies.getWidth() == 64 && zombies.getHeight() == 64){
                    batch.draw(zombie, zombies.x, zombies.y);
                }
                if (zombies.getWidth() == 65 && zombies.getHeight() == 65){
                    batch.draw(zombiePolice, zombies.x, zombies.y);
                }
                if (zombies.getWidth() == 66 && zombies.getHeight() == 66){
                    batch.draw(zombieRed, zombies.x, zombies.y);
                }
            }

            for(Rectangle bomm : bomSpawn)
            {
                batch.draw(bom, bomm.x, bomm.y);
            }

            for (Rectangle bullets : bulletSpawn) {
                batch.draw(bullet, bullets.x, bullets.y);
            }

            for (Rectangle damages : damageSpawn) {
                batch.draw(damage, damages.x, damages.y);
            }

            font.draw(batch, "POIN : " + score, 520, 455);
            getScoreHistory(score);
            
        }
        if (pause){
            //kondisi ingame ketika pause
            batch.draw(backgroundPause, backgroundGameRec.x, backgroundGameRec.y);
            font.draw(batch, "GAME IS CURRENTLY PAUSED", 120, 455);
            if ((Gdx.input.getX() >= 300 && Gdx.input.getX() <= 500) && (Gdx.input.getY() <= 220 && Gdx.input.getY() >= 110)){
                batch.draw(resumeButtonTouched, 300 , 260);
                if (Gdx.input.justTouched()) {
                    pause = false;
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){

                    }
                }
            }
            else {
                batch.draw(resumeButton, 300, 260);
            }

            //kondisi untuk ke main menu
            if ((Gdx.input.getX() >= 300 && Gdx.input.getX() <= 500) && (Gdx.input.getY() <= 370 && Gdx.input.getY() >= 260)){
                batch.draw(mainMenuButtonTouched, 300 , 110);
                if (Gdx.input.justTouched()) {
                    soundtrack.dispose();
                    game.setScreen(new mainMenuScreen(game));
                }
            }
            else {
                batch.draw(mainMenuButton, 300, 110);
            }
        }
        batch.end();
    }

    //fungsi spawn damage
    public void damageSpawn(int x, int y){
        Rectangle damages = new Rectangle();
        Damage damage = new Damage(x , y);
        double baseY = y - 5;
        damages.x = x + 64;
        damages.y = y;
        damages.width = 25;
        damages.height = 20;
        damageSpawn.add(damages);
        damageList.add(damage);
        this.baseY.add(baseY);
    }


    //fungsi mendapatkan score
    public void getScoreHistory(int score){
        try {
            String data = "";
            data += score;
            FileWriter file = new FileWriter("HistoryScorePlayer.txt");
            file.append(data);
            file.close();

        }catch (IOException e){e.printStackTrace();}
    }

    //fungsi spawn peluru
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

    //fungsi spawn zombie
    public void zombieSpawning() {
        Rectangle zombies = new Rectangle();
        Character zombie1 = new ZombieNormal(50);
        zombies.x = 840;
        zombies.y = MathUtils.random(0, 250);
        zombies.width = 64;
        zombies.height = 64;
        zombieSpawn.add(zombies);
        zombieList.add(zombie1);
        zombieLastSpawn = TimeUtils.nanoTime();
    }

    //fungsi spawn zombie 1
    public void zombie1Spawning() {
        Rectangle zombies = new Rectangle();
        Character zombie1 = new ZombieElite(100);
        zombies.x = 840;
        zombies.y = MathUtils.random(0, 250);
        zombies.width = 65;
        zombies.height = 65;
        zombieSpawn.add(zombies);
        zombieList.add(zombie1);
        zombieLastSpawn = TimeUtils.nanoTime();
    }

    //fungsi spawn zombie 2
    public void zombie2Spawning() {
        Rectangle zombies = new Rectangle();
        Character zombie2 = new ZombieGladiator(150);
        zombies.x = 840;
        zombies.y = MathUtils.random(0, 250);
        zombies.width = 66;
        zombies.height = 66;
        zombieSpawn.add(zombies);
        zombieList.add(zombie2);
        zombieLastSpawn = TimeUtils.nanoTime();
    }

    //fungsi random zombie 1
    public void randomZombie1() {
        int random = MathUtils.random(9);
        if (random >= 0 && random <= 5){
            zombieSpawning();
        }
        else if (random >= 6 && random <= 8){
            zombie1Spawning();
        }
    }

    //fungsi random zombie 2
    public void randomZombie2() {
        int random = MathUtils.random(11);
        if (random >= 0 && random <= 5){
            zombieSpawning();
        }
        else if (random >= 6 && random <= 8){
            zombie1Spawning();
        }
        else if (random == 9 || random == 10){
            zombie2Spawning();
        }
    }

    //fungsi spawn bom
    public void bomSpawning(){
        Rectangle boom = new Rectangle();
        boom.x = 840;
        boom.y = MathUtils.random(0, 250);
        boom.width = 40;
        boom.height = 40;
        bomSpawn.add(boom);
        bomLastSpawn = TimeUtils.millis();
    }

    @Override
    public void resize(int width, int height) {

    }

    //fungsi pause
    @Override
    public void pause() {
        try {
            batch.draw(backgroundPause, backgroundGameRec.x, backgroundGameRec.y);
        }
        catch (IllegalStateException e)
        {

        }
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
