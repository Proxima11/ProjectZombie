package com.mygdx.game;

abstract class Character {
    private int hp;

    public int getHp() {
        return hp;
    }

    public void UpdateHp(int hp) {
        this.hp = hp;
    }

    public Character(int hp) {
        this.hp = hp;
    }

    public void getDamage(){
    }

    public abstract void getDamage(int damage);
}
