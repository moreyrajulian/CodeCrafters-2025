package GameLauncher;

import Presentation.Controller.Floor;
import Presentation.Model.MP3Player;
import Presentation.View.BombermanFrame;
import Presentation.View.SkinSelector;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameLauncher {
    private static final int TIME_STEP = 30;
    private static int width = 25;
    private static int height = 15;
    private static int nrOfEnemies = 5;
    private static Timer clockTimer = null;
    private static MP3Player music;
    private static SkinSelector skinSelector;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showSkinSelector();
            startGame();
        });

    }

    private static void showSkinSelector() {
        JFrame dummyFrame = new JFrame(); // sólo para centrar el diálogo
        dummyFrame.setUndecorated(true);  // no se muestra como ventana visible
        dummyFrame.setSize(0, 0);
        dummyFrame.setLocationRelativeTo(null);
        dummyFrame.setVisible(true);

        SkinSelector selector = new SkinSelector(dummyFrame);
        selector.setVisible(true); // Esto bloquea hasta que se cierre el selector

        dummyFrame.dispose(); // ya no se necesita
    }


    public static void startGame() {
        Floor floor = Floor.getInstance(width, height, nrOfEnemies);
        BombermanFrame frame = new BombermanFrame("Bomberman", floor);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        floor.addFloorListener(frame.getBombermanComponent());

        Action doOneStep = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e) {
                tick(frame, floor);
            }
        };



        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
        music = new MP3Player("background.mp3");
        music.start();

    }

    private static void gameOver(BombermanFrame frame, Floor floor) {
        if(music!=null){
            music.stop();
        }
        clockTimer.stop();
        JOptionPane.showMessageDialog(null, "¡Game Over!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
        Floor.resetFloor();
        startGame();
    }

    private static void tick(BombermanFrame frame, Floor floor) {
        if (floor.getIsGameOver()) {
            gameOver(frame, floor);
        } else {
            floor.moveEnemies();
            floor.bombCountdown();
            floor.explosionHandler();
            floor.characterInExplosion();
            floor.notifyListeners();
            floor.getPlayer().updateMovement();


            //VERIFICA SI GANASTE MOSTRANDO MENSAJE
            if (floor.getEnemyList().isEmpty()) {
                if (music != null) music.stop();
                clockTimer.stop();
                JOptionPane.showMessageDialog(null, "¡Ganaste!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                Floor.resetFloor();
                startGame();
            }
        }
    }
}
