package com.mygdx.game;

public class Peluru {
    private int damage;

    public Peluru() {
        this.damage = 25;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int damaging(){
        return damage;
    }

    public void DoubleDamage(){
        setDamage(getDamage()*2);
    }
}
