package com.mygdx.game;

public class Police extends Character{
    public Police(int hp) {
        super(5);
    }

    @Override
    public void getDamage() {
        UpdateHp(getHp()-1);
    }

    @Override
    public void getDamage(int damage) {
    }
}
