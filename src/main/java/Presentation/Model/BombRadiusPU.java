package Presentation.Model;

/**
 * Esta clase extiende de AbstractPowerup y recibe metodos fundamentales como getters para sus coordenadas y tamaño. Esta clase
 * tiene un addToPlayer-method el cual ajusta el bombRadius del jugador.
 */
public class BombRadiusPU extends AbstractPowerUp {

    public BombRadiusPU(int rowIndex, int colIndex) {
        super(colIndex, rowIndex);
    }

//    @Override
//    public void addToPlayer(Player player) {
//        player.setPowerUpRadius(true);
//    }

    @Override
    public void addToPlayer(Player player) {}

    public String getName() {
        return "BombRadiusPU";
    }
}
