package Domain;

/**
 * This Interface class is excluded to one method, floorChanged which is implemented in Presentation.View.BombermanComponent
 * where the method repaints the game.
 */
public interface FloorListener
{
    void floorChanged();
}
