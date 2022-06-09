package com.mygdx.game;

public class Zombie extends Character{
    public Zombie(int hp) {
        super(100);
    }

    @Override
    public void getDamage(int damage) {
        UpdateHp(getHp() - damage);
    }
}
