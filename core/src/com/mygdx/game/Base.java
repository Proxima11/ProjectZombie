package com.mygdx.game;

public class Base extends Character{
    public Base(int hp) {
        super(hp);
    }

    @Override
    public void getDamage(int damage) {
      UpdateHp(getHp() - 1);
        if(getHp() < 0)
        {
            UpdateHp(0);
        }
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

    @Override
    public void UpdateHp(int hp) {
        super.UpdateHp(hp);
    }
}
