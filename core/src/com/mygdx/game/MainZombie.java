package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.Iterator;

public class MainZombie extends Game {


	private Texture backgroundGame;
	private Texture police;
	private Texture zombie;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle backgroundGameRec;
	private Rectangle policeRec;
	private Array<Rectangle> zombieSpawn;
	private long zombieLastSpawn;
	Character polisi;

	private Texture bullet;
	private Array<Rectangle> bulletSpawn;
	private long bulletLastSpawn;

	public void create(){
		backgroundGame = new Texture(Gdx.files.internal("background game2.png"));
		police = new Texture(Gdx.files.internal("police.png"));
		zombie = new Texture(Gdx.files.internal("zombie.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800,480);
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
		zombieSpawn = new Array<Rectangle>();

		bullet = new Texture(Gdx.files.internal("peluru.png"));
		bulletSpawn = new Array<>();

		polisi = new Police(5);


	}

	public void render(){
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) policeRec.y += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) policeRec.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) policeRec.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) policeRec.x -= 200 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.justTouched()) bulletSpawning();

		if (policeRec.y > 250) policeRec.y = 250;
		if (policeRec.y < 0) policeRec.y = 0;
		if(policeRec.x > 150) policeRec.x = 150;
		if(policeRec.x < 0) policeRec.x = 0;

		if (TimeUtils.nanoTime() - zombieLastSpawn > 1000000000) zombieSpawning();

		for (Iterator<Rectangle> iter = zombieSpawn.iterator(); iter.hasNext();){

			Rectangle zombiess = iter.next();
			zombiess.x -=200 * Gdx.graphics.getDeltaTime();
			if (zombiess.x + 64 < 0) iter.remove();
			if (zombiess.intersects(policeRec))
			{
				iter.remove();
				polisi.getDamage();
				if(polisi.AliveorNot() == false)
				{

				}
			}
//			if (zombiess.intersects(bulletSpawn.random())) iter.remove();
		}

		for (Iterator<Rectangle> iterr = bulletSpawn.iterator(); iterr.hasNext();){

			Rectangle bulletss = iterr.next();
			bulletss.x += 700 * Gdx.graphics.getDeltaTime();
			if (bulletss.x + 20 > 800) iterr.remove();
			if (bulletss.intersects(zombieSpawn.random()))
			{
				iterr.remove();
			}

		}

		ScreenUtils.clear(0.2f,0,0,1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(backgroundGame,backgroundGameRec.x,backgroundGameRec.y);
		batch.draw(police,policeRec.x, policeRec.y);
		for (Rectangle zombies : zombieSpawn){
			batch.draw(zombie, zombies.x, zombies.y);
		}
		for (Rectangle bulletss : bulletSpawn){
			batch.draw(bullet, bulletss.x, bulletss.y);
		}
		batch.end();
	}
	private void zombieSpawning(){
		Rectangle zombies = new Rectangle();
		zombies.x = 840;
		zombies.y = MathUtils.random(0,250);
		zombies.width = 64;
		zombies.height = 64;
		zombieSpawn.add(zombies);
		zombieLastSpawn = TimeUtils.nanoTime();

	}

	private void bulletSpawning(){
		Rectangle bullets = new Rectangle();
		bullets.x = policeRec.x;
		bullets.y = policeRec.y;
		bullets.width = 20;
		bullets.height = 20;
		bulletSpawn.add(bullets);
		bulletLastSpawn = TimeUtils.nanoTime();

	}
}
