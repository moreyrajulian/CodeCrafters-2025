package Presentation.Model;

import Presentation.Controller.Floor;
import Presentation.Controller.PlayerController;
import Presentation.View.BombermanComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;


public class Player extends AbstractCharacter
{

    private final static int PLAYER_START_X = 60;
    private final static int PLAYER_START_Y = 60;
    private final static int PLAYER_PIXELS_BY_STEP = 5;
    private int bombCount;
    private Floor floor;
	private PlayerController playerController;
	private Set<String> teclasPresionadas = new HashSet<>();
    public Action up = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.UP);
	}
    };
 
    public Action right = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.RIGHT);

	}
    };
   
    public Action down = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.DOWN);

	}
    };
  
    public Action left = new AbstractAction() {
	public void actionPerformed(ActionEvent e) {
	    movePlayer(Move.LEFT);

	}
    };


	public Action dropBomb = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			playerController.dropBomb(getRowIndex(), getColIndex(), getBombCount());
		}
	};

    public Player(BombermanComponent bombermanComponent, Floor floor) {
		super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);

		bombCount = 1;
	this.floor = floor;
		this.playerController = new PlayerController(this, floor);
		floor.addObserver(playerController);
		setPlayerButtons(bombermanComponent);
    }

	public void setPlayerButtons(BombermanComponent bombermanComponent){
		// Presionar
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("pressed RIGHT"), "pressRight");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("pressed LEFT"), "pressLeft");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("pressed UP"), "pressUp");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("pressed DOWN"), "pressDown");

		// Soltar
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("released RIGHT"), "releaseRight");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("released LEFT"), "releaseLeft");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("released UP"), "releaseUp");
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("released DOWN"), "releaseDown");

		// Acciones para presionar
		bombermanComponent.getActionMap().put("pressRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.add("RIGHT"); }
		});
		bombermanComponent.getActionMap().put("pressLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.add("LEFT"); }
		});
		bombermanComponent.getActionMap().put("pressUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.add("UP"); }
		});
		bombermanComponent.getActionMap().put("pressDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.add("DOWN"); }
		});

		// Acciones para soltar
		bombermanComponent.getActionMap().put("releaseRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.remove("RIGHT"); }
		});
		bombermanComponent.getActionMap().put("releaseLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.remove("LEFT"); }
		});
		bombermanComponent.getActionMap().put("releaseUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.remove("UP"); }
		});
		bombermanComponent.getActionMap().put("releaseDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { teclasPresionadas.remove("DOWN"); }
		});

		// Bomba (solo al presionar)
		bombermanComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("pressed SPACE"), "dropBomb");
		bombermanComponent.getActionMap().put("dropBomb", dropBomb);
	}

	public void updateMovement() {
		if (teclasPresionadas.contains("RIGHT")) {
			movePlayer(Move.RIGHT);
		}
		if (teclasPresionadas.contains("LEFT")) {
			movePlayer(Move.LEFT);
		}
		if (teclasPresionadas.contains("UP")) {
			movePlayer(Move.UP);
		}
		if (teclasPresionadas.contains("DOWN")) {
			movePlayer(Move.DOWN);
		}
	}

    public int getBombCount() {
		return bombCount;
    }

    public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
    }

    private void movePlayer(Move move) {
		playerController.movePlayer(move);
    }

	public Floor getFloor() {
		return floor;
	}
}
