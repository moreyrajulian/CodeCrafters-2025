package Presentation.Model;

import Presentation.Controller.Floor;
import Presentation.Model.Observer.Observador;

import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends AbstractCharacter implements Observador {
    private Move currentDirection;
    private static final int duracion = 5000;

    public Enemy(int x, int y, boolean vertical) {
        super(x, y, 7);
        currentDirection = randomDirection(vertical);
    }

    @Override
    public void update(String s, Player player) {
            if (s.equals("FreezeEnemiesPU")) {

                Floor floor = player.getFloor();

            // Congelar todos los enemigos (velocidad = 0)

            for (Enemy enemy: floor.getEnemyList()){
                enemy.setPixelsPerStep(0);
                enemy.setCambioVelocidadTemp();
            }
            // Programa una tarea para descongelar a los enemigos después de la duración
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // Descongelar enemigos
                    for (Enemy enemy : floor.getEnemyList()) {
                        enemy.clearCambioVelocidadTemp();
                    }
                }
            }, duracion);
            }
    }

    public void changeDirection() {
        if (currentDirection == Move.DOWN) {
            currentDirection = Move.UP;
        } else if (currentDirection == Move.UP) {
            currentDirection = Move.DOWN;
        } else if (currentDirection == Move.LEFT) {
            currentDirection = Move.RIGHT;
        } else {
            currentDirection = Move.LEFT;
        }
    }

    public Move getCurrentDirection() {
        return currentDirection;
    }

    private Move randomDirection(boolean vertical) {
        assert Move.values().length == 4;
        int pick = (int) (Math.random() * (Move.values().length-2));
        if(vertical) {
            return Move.values()[pick];
        }
        else{
            return Move.values()[pick+2];
        }

    }
}
