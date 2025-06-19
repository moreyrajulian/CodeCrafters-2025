package Presentation.Model;

import Presentation.Controller.Floor;
import Presentation.Model.Observer.Observador;
import Presentation.Model.Strategy.ExplosionAmpliada;
import Presentation.Model.Strategy.ExplosionNormal;
import Presentation.Model.Strategy.ExplosionStrategy;

public class Bomb {
    // Constants are static by definition.
    private final static int BOMBSIZE = 30;
    private final static int STARTCOUNTDOWN = 100;
    private int timeToExplosion = STARTCOUNTDOWN;
    private final int rowIndex;
    private final int colIndex;
    private boolean playerLeft;
    private ExplosionStrategy explosionStrategy;

    public Bomb(final int rowIndex, final int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        playerLeft = false;
        this.explosionStrategy = new ExplosionNormal();
    }

//    @Override
//    public void update(String s, Player player) {
//            if (s.equals("BombRadiusPU")) {
//                this.setExplosionStrategy(new ExplosionAmpliada());
//            }
//    }

    public void setExplosionStrategy(ExplosionStrategy explosionStrategy) {
        this.explosionStrategy = explosionStrategy;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void explode(Floor floor) {
        this.explosionStrategy.explode(this, floor);
    }

    // This method is static since every bomb has the same size.
    public static int getBOMBSIZE() {
        return BOMBSIZE;
    }

    public int getTimeToExplosion() {
        return timeToExplosion;
    }

    public void setTimeToExplosion(final int timeToExplosion) {
        this.timeToExplosion = timeToExplosion;
    }

    public int getExplosionRadius() {
        return this.explosionStrategy.getExplosionRadius();
    }

    public boolean isPlayerLeft() {
        return playerLeft;
    }

    public void setPlayerLeft(final boolean playerLeft) {
        this.playerLeft = playerLeft;
    }

}