package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Damage {

    private static final int GRAVITY = -15;
    private static final int PUSH = 15;
    private Vector3 position;
    private Vector3 velocity;
    private Texture damage;
    private int count = 0;

    public Damage(int x, int y){
        setPosition(new Vector3(x , y, 0));
        velocity = new Vector3(0, 0, 0);
        setCount(0);
        // nanti di isi png damage nya
        //setDamage(new Texture(".png"));
    }

    public void update(float dt){
        velocity.add(PUSH, GRAVITY, 0);
        velocity.scl(dt);
        getPosition().add(velocity.x,velocity.y, 0);

        velocity.scl(1/dt);
    }

    public void upward(){
        velocity.y = 200;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Texture getDamage() {
        return damage;
    }

    public void setDamage(Texture damage) {
        this.damage = damage;
    }

    public void dispose() {
        damage.dispose();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // cara untuk pake class damage nya :
    // public Damage damage;
    // tambahin kedalam contructornya
    // damage = new Damage(x,y); -> x,y posisi kepala zombie
        // ini di dalam render setelah batch open :
        //damage.update(delta);
        // nilai a nya diganti posisi kepalanya zombie dikurangi beberapa
        //if (damage.getPosition().y >= a) {
        //    game.batch.draw(damage.getDamage(), damage.getPosition().x, damage.getPosition().y);
        //    if (damage.getCount() == 0) {
        //        damage.setCount(1);
        //        damage.upward();
        //    }
        //}

}
