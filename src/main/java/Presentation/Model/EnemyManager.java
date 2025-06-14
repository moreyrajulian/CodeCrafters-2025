package Presentation.Model;

import Presentation.Model.Observer.Observador;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EnemyManager implements Observador {
    private List<Enemy> enemies;
    private static final int duracion = 5000;

    public EnemyManager(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    public void update(String s, Player player) {
        if (s.equals("FreezeEnemiesPU")) {
            for (Enemy enemy : enemies) {
                enemy.setPixelsPerStep(0);
                enemy.setCambioVelocidadTemp();
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Enemy enemy : enemies) {
                        enemy.clearCambioVelocidadTemp();
                    }
                }
            }, duracion);
        }
    }
}
