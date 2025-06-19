package Presentation.Model;

import java.util.ArrayList;
import java.util.Collection;

public class PowerUpManager {
    private Collection<AbstractPowerUp> powerupList = new ArrayList<>();

    public void addPowerUp(AbstractPowerUp powerUp) {
        powerupList.add(powerUp);
    }

    public void removePowerUp(AbstractPowerUp powerUp) {
        powerupList.remove(powerUp);
    }

    public Iterable<AbstractPowerUp> getPowerupList() {
        return powerupList;
    }

    public void checkCollisionWithPlayer(Player player) {
        for (AbstractPowerUp powerup : new ArrayList<>(powerupList)) {
            if (collidingCircles(player, powerup.getX() - player.getSize() / 2, powerup.getY() - player.getSize() / 2)) {
                powerup.addToPlayer(player);
                powerupList.remove(powerup);
                break;
            }
        }
    }

    private boolean collidingCircles(Player player, int x, int y) {
        int a = player.getX() - x;
        int b = player.getY() - y;
        int a2 = a * a;
        int b2 = b * b;
        double c = Math.sqrt(a2 + b2);
        return (player.getSize() > c);
    }
}
