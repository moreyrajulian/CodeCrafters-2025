import Presentation.Model.BombCounterPU;
import Presentation.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BombCounterTest {

    private BombCounterPU powerUp;

    @BeforeEach
    void setUp() {
        powerUp = new BombCounterPU(2, 3); // rowIndex=2, colIndex=3
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
    void testGetName_returnsCorrectName() {
        assertEquals("BombCounterPU", powerUp.getName());
    }
}
