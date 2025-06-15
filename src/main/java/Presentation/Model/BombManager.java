package Presentation.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Presentation.Controller.Floor;

public class BombManager {
    private List<Bomb> bombList = new ArrayList<>();
    private Collection<Bomb> explosionList = new ArrayList<>();
    private Collection<Explosion> explosionCoords = new ArrayList<>();
    private SoundManager soundManager;

    public BombManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public Iterable<Bomb> getBombList() {
        return bombList;
    }

    public int getBombListSize() {
        return bombList.size();
    }

    public Iterable<Bomb> getExplosionList() {
        return explosionList;
    }

    public Iterable<Explosion> getExplosionCoords() {
        return explosionCoords;
    }

    public void addExplosion(Explosion explosion) {
        explosionCoords.add(explosion);
    }

    public void bombCountdown() {
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        int index = 0;
        for (Bomb b : bombList) {
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
            if (b.getTimeToExplosion() == 0) {
                bombIndexesToBeRemoved.add(index);
                explosionList.add(b);
            }
            index++;
        }
        for (int i : bombIndexesToBeRemoved) {
            bombList.remove(i);
        }
    }

    public void explosionHandler(Presentation.Controller.Floor floor) {
        Collection<Explosion> explosionsToBeRemoved = new ArrayList<>();
        for (Explosion e : explosionCoords) {
            e.setDuration(e.getDuration() - 1);
            if (e.getDuration() == 0) {
                explosionsToBeRemoved.add(e);
            }
        }
        for (Explosion e : explosionsToBeRemoved) {
            explosionCoords.remove(e);
            soundManager.playSound("explosion.wav");
        }
        for (Bomb bomb : explosionList) {
            bomb.explode(floor);
        }
    }
}
