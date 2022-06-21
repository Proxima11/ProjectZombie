package com.mygdx.game;

public class ZombieGladiator extends Character{
    public ZombieGladiator(int hp) {
        super(150);
    }

    @Override
    public void getDamage(int damage) {
        UpdateHp(getHp() - damage);
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
}
