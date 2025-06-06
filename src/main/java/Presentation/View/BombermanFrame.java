package Presentation.View;

import Presentation.Controller.Floor;
import Presentation.Controller.FloorListener;
import Presentation.Model.Strategy.ExplosionStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

//Un JFrame es una ventana de Java Swing
public class BombermanFrame extends JFrame {
    private Floor floor;
    private BombermanComponent bombermanComponent;
	private ExplosionStrategy strategy;

    public BombermanFrame(final String title, Floor floor, ExplosionStrategy strategy) throws HeadlessException {
		super(title); //Titulo en la ventana
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //La funcion setDefaultCloseOperation de JFrame
																		 //determina que hacer cuando se cierra la ventana
		bombermanComponent = new BombermanComponent(floor);
		this.floor = floor;
		this.strategy = strategy;
		floor.createPlayer(bombermanComponent, floor, strategy);
		setKeyStrokes();

		this.setLayout(new BorderLayout());
		this.add(bombermanComponent, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
    }

    public FloorListener getBombermanComponent() {
		return bombermanComponent;
    }

    private boolean askUser(String question) {
	return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void setKeyStrokes() {

	KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	bombermanComponent.getInputMap().put(stroke, "q");
	bombermanComponent.getActionMap().put("q", quit);
    }

    private final Action quit = new AbstractAction()
    {
	public void actionPerformed(ActionEvent e) {
		dispose();
	    
	}
    };
}

