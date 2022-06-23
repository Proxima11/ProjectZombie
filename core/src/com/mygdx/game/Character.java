package com.mygdx.game;

abstract class Character {
    private int hp;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public boolean AliveorNot(){
        return false;
    }
}
