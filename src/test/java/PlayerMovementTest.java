import Presentation.Controller.Floor;
import Presentation.Model.AbstractCharacter;
import Presentation.Model.BombRadiusPU;
import Presentation.Model.Player;
import Presentation.View.BombermanComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PlayerMovementTest {
    private Floor floor;
    private Player player;
    private int width = 7;
    private int height = 7;

    @BeforeEach
    public void setUp() {
        Floor.resetFloor();
        floor = Floor.getInstance(width, height, 0);
        BombermanComponent bombermanComponent = mock(BombermanComponent.class);
        when(bombermanComponent.getInputMap()).thenReturn(new javax.swing.InputMap());
        when(bombermanComponent.getActionMap()).thenReturn(new javax.swing.ActionMap());
        player = new Player(bombermanComponent, floor);
        floor.createPlayer(bombermanComponent, floor);
    }

    @Test
    public void testPlayerCannotMoveThroughBlock() {
        // Mock Floor y Player
        Floor mockFloor = mock(Floor.class);
        Player mockPlayer = mock(Player.class);
        // Simula que hay colisión con bloque
        when(mockFloor.collisionWithBlock(mockPlayer)).thenReturn(true);
        // Instancia real de PlayerController con mocks
        Presentation.Controller.PlayerController controller = new Presentation.Controller.PlayerController(mockPlayer, mockFloor);
        // Simula movimiento
        controller.movePlayer(AbstractCharacter.Move.RIGHT);
        // Verifica que se consulta la colisión
        verify(mockFloor).collisionWithBlock(mockPlayer);
        // Verifica que se llama a moveBack si hay colisión
        verify(mockPlayer, atLeastOnce()).moveBack(AbstractCharacter.Move.RIGHT);
    }

    @Test
    public void testPlayerCollectsPowerUp() {
        // Mock Floor y Player, instancia real de BombRadiusPU
        Floor mockFloor = mock(Floor.class);
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getFloor()).thenReturn(mockFloor);
        BombRadiusPU realPowerUp = new BombRadiusPU(1, 1);
        // Ejecuta la lógica real de recolección
        realPowerUp.addToPlayer(mockPlayer);
        // Verifica que se notifica a los observers del Floor
        verify(mockFloor).notifyObservers("BombRadiusPU", mockPlayer);
    }
}
