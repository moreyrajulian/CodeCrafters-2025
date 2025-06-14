package Presentation.Controller;

import Presentation.Model.AbstractCharacter.Move;
import Presentation.Model.Bomb;
import Presentation.Model.Observer.Observador;
import Presentation.Model.Player;
import Presentation.Model.Strategy.*;


public class PlayerController implements Observador {
    private Player player;
    private Floor floor;
    private ExplosionStrategy strategy = new ExplosionNormal();

    public PlayerController(Player player, Floor floor) {
        this.player = player;
        this.floor = floor;
    }

    @Override
    public void update(String s, Player player) {
        switch (s) {
            case "BombRadiusPU":
                this.strategy = new ExplosionAmpliada();
                break;
            case "BombDiagonalPU":
                this.strategy = new ExplosionDiagonal();
                break;
        }
    }


    public void movePlayer(Move move) {
        player.move(move);
        if (floor.collisionWithBlock(player)){
            player.moveBack(move);
        }
        if(floor.collisionWithBombs(player)){
            player.moveBack(move);
        }
        if(floor.collisionWithEnemies()){
            floor.setIsGameOver(true);
        }
        floor.checkIfPlayerLeftBomb();
        floor.collisionWithPowerup();
        floor.notifyListeners();
    }

    public void dropBomb(int row, int col, int bombCount) {
        if (!floor.squareHasBomb(row, col) && floor.getBombListSize() < bombCount) {
//            Bomb bomb;
//            if (this.esAmpliada()) {
//                bomb = new Bomb(row, col, new ExplosionAmpliada());
//                this.setEsAmpliada(false);
//            } else {
//                bomb = new Bomb(row, col, new ExplosionNormal());
//            }
//            floor.addToBombList(bomb);
            Bomb bomb = new Bomb(row, col);
            bomb.setExplosionStrategy(this.strategy);
            floor.addToBombList(bomb);
            this.strategy = new ExplosionNormal();
        }
        floor.notifyListeners();
    }
}
