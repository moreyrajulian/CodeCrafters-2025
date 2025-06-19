import Presentation.Model.BombCounterPU;
import Presentation.Model.BombDiagonalPU;
import Presentation.Model.FreezeEnemiesPU;
import Presentation.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BombCounterTest {

    private BombCounterPU powerUp;
    private FreezeEnemiesPU powerUp1;
    private BombDiagonalPU powerUp2;

    @BeforeEach
    void setUp() {
        powerUp = new BombCounterPU(2, 3); // rowIndex=2, colIndex=3
        powerUp1 = new FreezeEnemiesPU(2, 3);
        powerUp2 = new BombDiagonalPU(2, 4);
    }

    @Test
    void testAddToPlayer_increasesBombCount() {
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.getBombCount()).thenReturn(2);

        powerUp.addToPlayer(mockPlayer);

        verify(mockPlayer).getBombCount();
        verify(mockPlayer).setBombCount(3); // 2 + 1
    }

    @Test
    void testGetName_returnsCorrectNamePU1() {
        assertEquals("BombCounterPU", powerUp.getName());
    }

    @Test
    void testGetName_returnsCorrectNamePU2() {
        assertEquals("FreezeEnemiesPU", powerUp1.getName());
    }

    @Test
    void testGetName_returnsCorrectNamePU3() {
        assertEquals("BombDiagonalPU", powerUp2.getName());
    }
}
