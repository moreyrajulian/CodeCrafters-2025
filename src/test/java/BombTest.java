import Presentation.Model.Bomb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BombTest {

    private Bomb bomb;

    @BeforeEach
    void setUp() {
        bomb = new Bomb(5, 10);
    }

    @Test
    void testConstructorSetsFieldsCorrectly() {
        Assertions.assertEquals(5, bomb.getRowIndex());
        Assertions.assertEquals(10, bomb.getColIndex());
        Assertions.assertEquals(3, bomb.getExplosionRadius());
        Assertions.assertFalse(bomb.isPlayerLeft());
        Assertions.assertEquals(100, bomb.getTimeToExplosion());
    }

    @Test
    void testGetBOMBSIZE() {
        Assertions.assertEquals(30, Bomb.getBOMBSIZE());
    }

    @Test
    void testSetAndGetTimeToExplosion() {
        bomb.setTimeToExplosion(50);
        Assertions.assertEquals(50, bomb.getTimeToExplosion());
    }

    @Test
    void testSetAndGetPlayerLeft() {
        bomb.setPlayerLeft(true);
        Assertions.assertTrue(bomb.isPlayerLeft());
    }
}
