package Class;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class FreezeEnemiesPUTest {

    @Test
    void testFreezeAndUnfreezeEnemies() throws InterruptedException {
        // Crear mocks
        Floor mockFloor = mock(Floor.class);
        Enemy mockEnemy1 = mock(Enemy.class);
        Enemy mockEnemy2 = mock(Enemy.class);
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.getFloor()).thenReturn(mockFloor);
        when(mockFloor.getEnemyList()).thenReturn(java.util.List.of(mockEnemy1, mockEnemy2));

        // Ejecutar el power-up
        FreezeEnemiesPU powerUp = new FreezeEnemiesPU(0, 0);
        powerUp.addToPlayer(mockPlayer);

        // Verificar que los enemigos fueron congelados
        verify(mockEnemy1).freeze();
        verify(mockEnemy2).freeze();

        // Esperar 6 segundos
        Thread.sleep(6000);

        // Verificar que fueron descongelados
        verify(mockEnemy1).unfreeze();
        verify(mockEnemy2).unfreeze();
    }
}
