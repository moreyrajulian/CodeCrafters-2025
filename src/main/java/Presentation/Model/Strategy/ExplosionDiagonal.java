package Presentation.Model.Strategy;

import Presentation.Controller.Floor;
import Presentation.Model.Bomb;
import Presentation.Model.Explosion;

public class ExplosionDiagonal implements ExplosionStrategy {

    private final int explosionRadius;

    public ExplosionDiagonal() {
        this.explosionRadius = 1; // Distancia exacta del anillo
    }

    @Override
    public int getExplosionRadius() {
        return explosionRadius;
    }

    @Override
    public void explode(Bomb bomb, Floor floor) {
        int eRow = bomb.getRowIndex();
        int eCol = bomb.getColIndex();

        // Centro (no se agrega explosión ahí)
        // floor.addExplosion(new Explosion(eRow, eCol)); // Si querés incluir el centro, descomentá

        // Posiciones a distancia EXACTA igual al radio
        int r = this.getExplosionRadius();

        // Norte
        if (eRow - r >= 0) {
            floor.bombCoordinateCheck(eRow - r, eCol, true);
        }

        // Sur
        if (eRow + r < floor.getHeight()) {
            floor.bombCoordinateCheck(eRow + r, eCol, true);
        }

        // Oeste
        if (eCol - r >= 0) {
            floor.bombCoordinateCheck(eRow, eCol - r, true);
        }

        // Este
        if (eCol + r < floor.getWidth()) {
            floor.bombCoordinateCheck(eRow, eCol + r, true);
        }
    }
}
