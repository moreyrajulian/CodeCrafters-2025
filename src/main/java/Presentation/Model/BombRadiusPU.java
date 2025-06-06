package Presentation.Model;

import Presentation.Model.Strategy.ExplosionAmpliada;

/**
 * Esta clase extiende de AbstractPowerup y recibe metodos fundamentales como getters para sus coordenadas y tamaño. Esta clase
 * tiene un addToPlayer-method el cual ajusta el bombRadius del jugador.
 */
public class BombRadiusPU extends AbstractPowerUp
{

    public BombRadiusPU(int rowIndex, int colIndex) {
	super(colIndex, rowIndex);
    }

    /**
     * Aumenta el radio de explosion en +1 según el radio anterior
     * @param player el jugador que recibe el PowerUp
     */
    @Override
    public void addToPlayer(Player player) {
        player.setExplosionStrategy(new ExplosionAmpliada());
    }

    public String getName() {
        return "ExplosionAmpliada";
    }
}
