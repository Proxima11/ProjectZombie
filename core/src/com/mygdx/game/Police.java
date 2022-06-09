package com.mygdx.game;

public class Police extends Character{
    public Police(int hp) {
        super(5);
    }

    @Override
    public void getDamage() {
        UpdateHp(getHp()-1);
        if(getHp() < 0)
        {
            UpdateHp(0);
        }
    }

    @Override
    public void getDamage(int damage) {
    }

    @Override
    public boolean AliveorNot() {
        if(getHp() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
