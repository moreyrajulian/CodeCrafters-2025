import Presentation.Controller.Floor;

import Presentation.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FloorPowerUpTest {
    private Floor floor;
    private int width = 10;
    private int height = 8;

    @BeforeEach
    public void setUp() throws Exception {
        Floor.resetFloor();
        floor = Floor.getInstance(width, height, 1);
        // Mockear la lista interna de powerups
        Collection<AbstractPowerUp> mockPowerupList = Mockito.mock(Collection.class);
        Field field = Floor.class.getDeclaredField("powerupList");
        field.setAccessible(true);
        field.set(floor, mockPowerupList);
    }

    @Test
    public void testNoPowerUpOnMapBorder() throws Exception {
        // Usar reflexión para acceder a spawnPowerup
        Method spawnPowerup = Floor.class.getDeclaredMethod("spawnPowerup", int.class, int.class);
        spawnPowerup.setAccessible(true);
        // Crear una nueva instancia de Floor para este test (sin mock)
        Floor.resetFloor();
        Floor realFloor = Floor.getInstance(width, height, 1);
        // Probar todas las posiciones del borde
        for (int i = 0; i < width; i++) {
            spawnPowerup.invoke(realFloor, 0, i); // fila superior
            spawnPowerup.invoke(realFloor, height - 1, i); // fila inferior
        }
        for (int i = 0; i < height; i++) {
            spawnPowerup.invoke(realFloor, i, 0); // columna izquierda
            spawnPowerup.invoke(realFloor, i, width - 1); // columna derecha
        }
        // Verificar que no se generó ningún power-up en el borde
        for (AbstractPowerUp pu : realFloor.getPowerupList()) {
            int x = pu.getX();
            int y = pu.getY();
            int col = x / Presentation.View.BombermanComponent.getSquareSize();
            int row = y / Presentation.View.BombermanComponent.getSquareSize();
            assertTrue(row > 0 && row < height - 1 && col > 0 && col < width - 1,
                    "PowerUp generado en el borde: fila " + row + ", columna " + col);
        }
    }

    @Test
    public void testNoPowerUpOnMapBorderWithMock() throws Exception {
        Method spawnPowerup = Floor.class.getDeclaredMethod("spawnPowerup", int.class, int.class);
        spawnPowerup.setAccessible(true);
        // Probar todas las posiciones del borde
        for (int i = 0; i < width; i++) {
            spawnPowerup.invoke(floor, 0, i); // fila superior
            spawnPowerup.invoke(floor, height - 1, i); // fila inferior
        }
        for (int i = 0; i < height; i++) {
            spawnPowerup.invoke(floor, i, 0); // columna izquierda
            spawnPowerup.invoke(floor, i, width - 1); // columna derecha
        }
        // Verificar que nunca se llamó a add en el mock
        Field field = Floor.class.getDeclaredField("powerupList");
        field.setAccessible(true);
        Collection<AbstractPowerUp> mockPowerupList = (Collection<AbstractPowerUp>) field.get(floor);
        verify(mockPowerupList, never()).add(any(AbstractPowerUp.class));
    }
}
