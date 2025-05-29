package Domain;

/**
 * Clase abstracta que representa un PowerUp dentro del juego.
 * <p>
 * Define propiedades básicas como posición, tamaño y nombre.
 * Las subclases deben implementar el comportamiento concreto
 * del PowerUp al aplicarse al jugador.
 * </p>
 */
public abstract class AbstractPowerUp {

    /** Tamaño estándar del PowerUp en píxeles. */
    private final static int POWERUP_SIZE = 30;
    private final int x;
    private final int y;
    private String name = null;

    /**
     * Crea un PowerUp con posición fija en el mapa.
     *
     * @param x coordenada horizontal del PowerUp (en píxeles)
     * @param y coordenada vertical del PowerUp (en píxeles)
     */
    public AbstractPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metodo que debe ser sobrescrito por las subclases para definir
     * el efecto del PowerUp cuando se aplica a un jugador.
     *
     * @param player el jugador que recibe el PowerUp
     */
    public void addToPlayer(Player player) {
    }

    /**
     * Devuelve el tamaño estándar del PowerUp en píxeles.
     *
     * @return tamaño del PowerUp
     */
    public int getPowerUpSize() {
        return POWERUP_SIZE;
    }

    /**
     * Obtiene la coordenada horizontal del PowerUp.
     *
     * @return coordenada x en píxeles
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada vertical del PowerUp.
     *
     * @return coordenada y en píxeles
     */
    public int getY() {
        return y;
    }

    /**
     * Devuelve el nombre del PowerUp, si ha sido definido.
     *
     * @return nombre del PowerUp o {@code null} si no tiene
     */
    public String getName() {
        return name;
    }
}
