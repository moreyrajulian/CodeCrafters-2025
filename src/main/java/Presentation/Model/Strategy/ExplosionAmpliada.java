package Presentation.Model.Strategy;

import Presentation.Controller.Floor;
import Presentation.Model.Bomb;
import Presentation.Model.Explosion;

public class ExplosionAmpliada implements ExplosionStrategy {

    private int explosionRadius;

    public ExplosionAmpliada() {
        this.explosionRadius = 1;
    }

    public int getExplosionRadius() {
        return this.explosionRadius;
    }

    @Override
    public void explode(Bomb bomb, Floor floor) {
        int eRow = bomb.getRowIndex();
        int eCol = bomb.getColIndex();
        boolean northOpen = true;
        boolean southOpen = true;
        boolean westOpen = true;
        boolean eastOpen = true;
        floor.addExplosion(new Explosion(eRow, eCol));

        // Corrige los límites para sur y este, y amplía el radio en +1 respecto a ExplosionNormal
        for (int i = 1; i < this.getExplosionRadius() + 2; i++) {
            if (eRow - i >= 0 && northOpen) {
                northOpen = floor.bombCoordinateCheck(eRow - i, eCol, northOpen);
            }
            if (eRow + i < floor.getHeight() && southOpen) {
                southOpen = floor.bombCoordinateCheck(eRow + i, eCol, southOpen);
            }
            if (eCol - i >= 0 && westOpen) {
                westOpen = floor.bombCoordinateCheck(eRow, eCol - i, westOpen);
            }
            if (eCol + i < floor.getWidth() && eastOpen) {
                eastOpen = floor.bombCoordinateCheck(eRow, eCol + i, eastOpen);
            }
        }
    }
}
