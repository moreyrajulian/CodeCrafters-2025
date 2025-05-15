package Abstract;
import Class.*;

/**
 * Clase abstracta que representa un personaje dentro del juego.
 * <p>
 * Define comportamiento básico de movimiento y posicionamiento,
 * y debe ser extendida por personajes concretos del juego.
 * </p>
 *
 * <p>El personaje se mueve en una grilla de píxeles, y su posición
 * se gestiona en términos de coordenadas (x, y).</p>
 */
public abstract class AbstractCharacter {

    /** Tamaño del personaje en píxeles. */
    private final static int SIZE = 30;
    private int x;
    private int y;
    private int pixelsPerStep;

    /**
     * Crea un personaje con posición inicial y paso de movimiento definido.
     *
     * @param x coordenada horizontal inicial (en píxeles)
     * @param y coordenada vertical inicial (en píxeles)
     * @param pixelsPerStep cantidad de píxeles que se mueve por paso
     */
    protected AbstractCharacter(int x, int y, int pixelsPerStep) {
        this.x = x;
        this.y = y;
        this.pixelsPerStep = pixelsPerStep;
    }

    //Crear enum
    public enum Move {
        DOWN(0, 1),
        UP(0, -1),
        RIGHT(1, 0),
        LEFT(-1, 0);

        private final int deltaX;
        private final int deltaY;
        Move(final int deltaX, final int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }

    /**
     * Mueve el personaje en la dirección especificada.
     *
     * @param move dirección en la que se moverá
     */
    public void move(Move move) {
        y += move.deltaY * pixelsPerStep;
        x += move.deltaX * pixelsPerStep;
    }

    /**
     * Mueve al personaje en la dirección opuesta a la especificada.
     *
     * @param currentDirection dirección actual que se desea revertir
     */
    public void moveBack(Move currentDirection) {
        if (currentDirection == Move.DOWN) {
            move(Move.UP);
        } else if (currentDirection == Move.UP) {
            move(Move.DOWN);
        } else if (currentDirection == Move.LEFT) {
            move(Move.RIGHT);
        } else if (currentDirection == Move.RIGHT) {
            move(Move.LEFT);
        }
    }

    /**
     * Devuelve el tamaño del personaje en píxeles.
     *
     * @return tamaño en píxeles
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene la posición horizontal actual del personaje.
     *
     * @return coordenada x en píxeles
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la posición vertical actual del personaje.
     *
     * @return coordenada y en píxeles
     */
    public int getY() {
        return y;
    }

    /**
     * Devuelve la columna del mapa en la que se encuentra el personaje.
     *
     * @return índice de columna (según conversión de píxeles a casilla)
     */
    public int getColIndex() {
        return Floor.pixelToSquare(x);
    }

    /**
     * Devuelve la fila del mapa en la que se encuentra el personaje.
     *
     * @return índice de fila (según conversión de píxeles a casilla)
     */
    public int getRowIndex() {
        return Floor.pixelToSquare(y);
    }

}
