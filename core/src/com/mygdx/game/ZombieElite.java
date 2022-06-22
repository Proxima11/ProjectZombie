package com.mygdx.game;

public class ZombieElite extends Character{
    @Override
    public void setScore(int score) {
        super.setScore(score);
    }

    @Override
    public int getScore() {
        return super.getScore();
    }

    public ZombieElite(int hp) {
        super(100);
        setScore(35);
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
