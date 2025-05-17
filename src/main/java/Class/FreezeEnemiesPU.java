package Class;

import Abstract.AbstractPowerUp;
import Class.Player;

import java.util.Timer;
import java.util.TimerTask; // Importar TimerTask


public class FreezeEnemiesPU extends AbstractPowerUp {

    public FreezeEnemiesPU(int x, int y) {
        super(x, y);  // Llama al constructor de AbstractPowerUp con x, y
    }

    @Override
    public void addToPlayer(Player player)
    {
        Floor floor = player.getFloor();
        for (Enemy enemy: floor.getEnemyList()){
            enemy.freeze();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // Descongelar enemigos
                for (Enemy enemy : floor.getEnemyList()) {
                    enemy.unfreeze();
                }
            }
        }, 5000);

    }


}
