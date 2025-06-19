package Presentation.Model;

/**
 * PowerUp que congela a todos los enemigos del piso actual del jugador
 * durante un tiempo determinado.
 */

public class FreezeEnemiesPU extends AbstractPowerUp {

    /** Duración del efecto de congelamiento en milisegundos */
private static final int duracion = 5000;

    /**
     * Constructor del PowerUp Presentation.Model.FreezeEnemiesPU que define su posición.
     *
     * @param x Posición en X donde aparece el PowerUp.
     * @param y Posición en Y donde aparece el PowerUp.
     */


    public FreezeEnemiesPU(int x, int y) {
        super(x, y);  // Llama al constructor de Presentation.Model.AbstractPowerUp con x, y
    }

    /**
     * Aplica el efecto de congelar a todos los enemigos del piso actual
     * del jugador, deteniendo su movimiento temporalmente.
     * Después de la duración establecida, se revierte el efecto.
     *
     * @param player El jugador al que se le aplicará el PowerUp.
     */

    @Override
    public void addToPlayer(Player player) {
        player.getFloor().notifyObservers(getName(), player);
    }

    /**
     * Obtiene el nombre del PowerUp.
     *
     * @return El nombre "FreezeEnemies".
     */
    public String getName() {
        return "FreezeEnemiesPU";
    }

}
