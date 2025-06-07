package Presentation.Model;

import Presentation.Controller.Floor;
import Presentation.Controller.PlayerController;
import Presentation.View.BombermanComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class Player extends AbstractCharacter
{

    private final static int PLAYER_START_X = 60;
    private final static int PLAYER_START_Y = 60;
    private final static int PLAYER_PIXELS_BY_STEP = 6;
    private int explosionRadius;
    private int bombCount;
    private Floor floor;
	private PlayerController playerController;
	private boolean powerUpRadius = false;

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
			playerController.dropBomb(getRowIndex(), getColIndex(), getExplosionRadius(), getBombCount());
		}
	};

    public Player(BombermanComponent bombermanComponent, Floor floor) {
		super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
		explosionRadius = 1;
		bombCount = 1;
		this.floor = floor;
		this.playerController = new PlayerController(this, floor);
		setPlayerButtons(bombermanComponent);
    }

    public void setPlayerButtons(BombermanComponent bombermanComponent){
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
		bombermanComponent.getActionMap().put("moveRight", right);
		bombermanComponent.getActionMap().put("moveLeft", left);
		bombermanComponent.getActionMap().put("moveUp", up);
		bombermanComponent.getActionMap().put("moveDown", down);
		bombermanComponent.getActionMap().put("dropBomb", dropBomb);
    }

    public int getBombCount() {
	return bombCount;
    }

    public void setBombCount(int bombCount) {
	this.bombCount = bombCount;
    }

    public int getExplosionRadius() {
	return explosionRadius;
    }

    private void movePlayer(Move move) {
	playerController.movePlayer(move);
    }

	public Floor getFloor() {
		return floor;
	}

	public boolean hasPowerUpRadius() {
		return powerUpRadius;
	}

	public void setPowerUpRadius(boolean value) {
		this.powerUpRadius = value;
	}
}
