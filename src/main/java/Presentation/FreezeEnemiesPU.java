package Presentation;

import Presentation.Model.AbstractPowerUp;
import Presentation.Model.Enemy;

import java.util.Timer;
import java.util.TimerTask; // Importar TimerTask

/**
 * PowerUp que congela a todos los enemigos del piso actual del jugador
 * durante un tiempo determinado.
 */

public class FreezeEnemiesPU extends AbstractPowerUp {

    /** Duración del efecto de congelamiento en milisegundos */
private static final int duracion = 5000;

    /**
     * Constructor del PowerUp Presentation.FreezeEnemiesPU que define su posición.
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
    public void addToPlayer(Player player)
    {
        Floor floor = player.getFloor();

        // Congelar todos los enemigos (velocidad = 0)

        for (Enemy enemy: floor.getEnemyList()){
            enemy.setPixelsPerStep(0);
            enemy.setCambioVelocidadTemp();
        }
        // Programa una tarea para descongelar a los enemigos después de la duración
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // Descongelar enemigos
                for (Enemy enemy : floor.getEnemyList()) {
                    enemy.clearCambioVelocidadTemp();
                }
            }
        }, duracion);

    }
    /**
     * Obtiene el nombre del PowerUp.
     *
     * @return El nombre "FreezeEnemies".
     */
    public String getName() {
        final String name = "FreezeEnemies";
        return name;
    }

}
