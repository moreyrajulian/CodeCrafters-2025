package Presentation.Model;

public class BombCounterPU extends AbstractPowerUp
{

    public BombCounterPU(int rowIndex, int colIndex) {
	super(colIndex, rowIndex);
    }

    /**
     * Aumenta la capacidad de bombas en +1 según su capacidad anterior
     * @param player el jugador que recibe el PowerUp
     */

    @Override
    public void addToPlayer(Player player) {
	    int currentBombCount = player.getBombCount();
        player.setBombCount(currentBombCount + 1);
    }

    public String getName() {
	    final String name = "BombCounter";
	    return name;
    }
}
