package Presentation.Model;

/**
 * Esta clase representa la explosion, o las "bolas de fuego", aquellas capaces de matar a un Presentation.Model.Enemy o a un Presentation.Model.Player,
 * así como de destruir BREAKABLEBLOCKS. Necesita un indice de fila y columna que se usa para la lógica y para el pintado.
 * La duracion representa cuantos timesteps existirá antes de ser removida.
 */
public class Explosion
{
    private int rowIndex;
    private int colIndex;
    private int duration = 5;

    public Explosion(int rowIndex, int colIndex)
    {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
