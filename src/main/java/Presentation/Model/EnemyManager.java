package Presentation.Model;

import Presentation.Model.Observer.Observador;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import Presentation.Controller.Floor;

public class EnemyManager implements Observador {
    private List<Enemy> enemies = new ArrayList<>();
    private static final int duracion = 5000;

    public EnemyManager() {
    }

    public void spawnEnemies(int nrOfEnemies, int width, int height, FloorTile[][] tiles, Floor floor) {
        for (int e = 0; e < nrOfEnemies; e++) {
            while (true) {
                int randRowIndex = 1 + (int) (Math.random() * (height - 2));
                int randColIndex = 1 + (int) (Math.random() * (width - 2));
                if (tiles[randRowIndex][randColIndex] != FloorTile.FLOOR) {
                    continue;
                }
                if (randRowIndex == 1 && randColIndex == 1 || randRowIndex == 1 && randColIndex == 2 || randRowIndex == 2 && randColIndex == 1) {
                    continue;
                }
                if ((randRowIndex % 2) == 0) {
                    Enemy enemy = new Enemy(floor.squareToPixel(randColIndex) + Presentation.View.BombermanComponent.getSquareMiddle(), floor.squareToPixel(randRowIndex) + Presentation.View.BombermanComponent.getSquareMiddle(), true);
                    enemies.add(enemy);
                } else {
                    Enemy enemy = new Enemy(floor.squareToPixel(randColIndex) + Presentation.View.BombermanComponent.getSquareMiddle(), floor.squareToPixel(randRowIndex) + Presentation.View.BombermanComponent.getSquareMiddle(), false);
                    enemies.add(enemy);
                }
                break;
            }
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void clearEnemies() {
        enemies.clear();
    }

    public void moveEnemies(Floor floor) {
        if (enemies.isEmpty()) {
            floor.setIsGameOver(true);
        }
        for (Enemy e : new ArrayList<>(enemies)) {
            Presentation.Model.AbstractCharacter.Move currentDirection = e.getCurrentDirection();
            if (currentDirection == Presentation.Model.AbstractCharacter.Move.DOWN) {
                e.move(Presentation.Model.AbstractCharacter.Move.DOWN);
            } else if (currentDirection == Presentation.Model.AbstractCharacter.Move.UP) {
                e.move(Presentation.Model.AbstractCharacter.Move.UP);
            } else if (currentDirection == Presentation.Model.AbstractCharacter.Move.LEFT) {
                e.move(Presentation.Model.AbstractCharacter.Move.LEFT);
            } else {
                e.move(Presentation.Model.AbstractCharacter.Move.RIGHT);
            }
            if (floor.collisionWithBlock(e)) {
                e.changeDirection();
            }
            if (floor.collisionWithBombs(e)) {
                e.changeDirection();
            }
            if (floor.collisionWithEnemies()) {
                floor.setIsGameOver(true);
            }
        }
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
