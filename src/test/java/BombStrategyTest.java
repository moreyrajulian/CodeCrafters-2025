import Presentation.Controller.Floor;
import Presentation.Model.Bomb;
import Presentation.Model.Strategy.ExplosionAmpliada;
import Presentation.Model.Strategy.ExplosionDiagonal;
import Presentation.Model.Strategy.ExplosionNormal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class BombStrategyTest {
    private Floor floor;
    private int width = 7;
    private int height = 7;

    @BeforeEach
    public void setUp() {
        Floor.resetFloor();
        floor = Floor.getInstance(width, height, 0);
    }

    @Test
    public void testExplosionNormal() {
        Floor mockFloor = mock(Floor.class);
        when(mockFloor.getHeight()).thenReturn(7);
        when(mockFloor.getWidth()).thenReturn(7);
        // Permite que la explosión se propague en todas las direcciones
        when(mockFloor.bombCoordinateCheck(anyInt(), anyInt(), anyBoolean())).thenReturn(true);
        Bomb bomb = new Bomb(3, 3);
        bomb.setExplosionStrategy(new ExplosionNormal());
        bomb.explode(mockFloor);
        verify(mockFloor).addExplosion(argThat(e -> e.getRowIndex() == 3 && e.getColIndex() == 3));
        verify(mockFloor).bombCoordinateCheck(2, 3, true); // norte
        verify(mockFloor).bombCoordinateCheck(4, 3, true); // sur
        verify(mockFloor).bombCoordinateCheck(3, 2, true); // oeste
        verify(mockFloor).bombCoordinateCheck(3, 4, true); // este
    }

    @Test
    public void testExplosionAmpliada() {
        Floor mockFloor = mock(Floor.class);
        when(mockFloor.getHeight()).thenReturn(7);
        when(mockFloor.getWidth()).thenReturn(7);
        // Permite que la explosión se propague en todas las direcciones
        when(mockFloor.bombCoordinateCheck(anyInt(), anyInt(), anyBoolean())).thenReturn(true);
        Bomb bomb = new Bomb(3, 3);
        bomb.setExplosionStrategy(new ExplosionAmpliada());
        bomb.explode(mockFloor);
        verify(mockFloor).addExplosion(argThat(e -> e.getRowIndex() == 3 && e.getColIndex() == 3));
        verify(mockFloor).bombCoordinateCheck(2, 3, true); // norte 1
        verify(mockFloor).bombCoordinateCheck(1, 3, true); // norte 2
        verify(mockFloor).bombCoordinateCheck(4, 3, true); // sur 1
        verify(mockFloor).bombCoordinateCheck(5, 3, true); // sur 2
        verify(mockFloor).bombCoordinateCheck(3, 2, true); // oeste 1
        verify(mockFloor).bombCoordinateCheck(3, 1, true); // oeste 2
        verify(mockFloor).bombCoordinateCheck(3, 4, true); // este 1
        verify(mockFloor).bombCoordinateCheck(3, 5, true); // este 2
    }

    @Test
    public void testExplosionDiagonal() {
        Floor mockFloor = mock(Floor.class);
        when(mockFloor.getHeight()).thenReturn(7);
        when(mockFloor.getWidth()).thenReturn(7);
        // Permite que la explosión se propague en todas las direcciones
        when(mockFloor.bombCoordinateCheck(anyInt(), anyInt(), anyBoolean())).thenReturn(true);
        Bomb bomb = new Bomb(3, 3);
        bomb.setExplosionStrategy(new ExplosionDiagonal());
        bomb.explode(mockFloor);
        verify(mockFloor, never()).addExplosion(any());
        verify(mockFloor).bombCoordinateCheck(2, 3, true); // norte
        verify(mockFloor).bombCoordinateCheck(4, 3, true); // sur
        verify(mockFloor).bombCoordinateCheck(3, 2, true); // oeste
        verify(mockFloor).bombCoordinateCheck(3, 4, true); // este
    }
}
