package Presentation.Controller;

import Presentation.Model.Player;
import Presentation.Model.AbstractCharacter.Move;
import Presentation.Model.Bomb;


public class PlayerController {
    private Player player;
    private Floor floor;


    public PlayerController(Player player, Floor floor) {
        this.player = player;
        this.floor = floor;
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

    public void dropBomb(int row, int col, int explosionRadius, int bombCount) {
        if (!floor.squareHasBomb(row, col) && floor.getBombListSize() < bombCount) {
            floor.addToBombList(new Bomb(row, col, explosionRadius));
        }
        floor.notifyListeners();
    }

}
