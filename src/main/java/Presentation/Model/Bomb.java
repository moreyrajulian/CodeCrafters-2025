package Presentation.Model;

import Presentation.Controller.Floor;
import Presentation.Model.Observer.Observador;
import Presentation.Model.Strategy.ExplosionStrategy;

public class Bomb implements Observador {
    // Constants are static by definition.
    private final static int BOMBSIZE = 30;
    private final static int STARTCOUNTDOWN = 100;
    private int timeToExplosion = STARTCOUNTDOWN;
    private final int rowIndex;
    private final int colIndex;
    private int explosionRadius;
    private boolean playerLeft;
    private ExplosionStrategy explosionStrategy;

    public Bomb(final int rowIndex, final int colIndex, int explosionRadius, ExplosionStrategy strategy) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.explosionRadius = explosionRadius;
        this.explosionStrategy = strategy;
        playerLeft = false;
    }

    @Override
    public void update(String s, Player player) {
            if (s.equals("BombRadiusPU") && explosionStrategy != null) {
                player.setPowerUpRadius(true);
            }
    }

    public ExplosionStrategy getExplosionStrategy() {
        return this.explosionStrategy;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void explode(Floor floor) {
        explosionStrategy.explode(this, floor);
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
        return explosionRadius;
    }

    public boolean isPlayerLeft() {
        return playerLeft;
    }

    public void setPlayerLeft(final boolean playerLeft) {
        this.playerLeft = playerLeft;
    }
}