import Presentation.Controller.Floor;
import Presentation.Model.FreezeEnemiesPU;
import Presentation.Model.Player;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class FreezeEnemiesPUTest {

    @Test
    void testFreezeAndUnfreezeEnemies() {
        // Crear mocks
        Floor mockFloor = mock(Floor.class);
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.getFloor()).thenReturn(mockFloor);

        // Ejecutar el power-up
        FreezeEnemiesPU powerUp = new FreezeEnemiesPU(0, 0);
        powerUp.addToPlayer(mockPlayer);

        // Verificar que se notificó a los observers (EnemyManager)
        verify(mockFloor).notifyObservers("FreezeEnemiesPU", mockPlayer);
    }
}