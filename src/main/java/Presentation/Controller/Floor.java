package Presentation.Controller;

import Presentation.Model.*;
import Presentation.Model.Observer.Observable;
import Presentation.Model.Observer.Observador;
import Presentation.View.BombermanComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Floor implements Observable {
    // Constants are static by definition.
    private final static double CHANCE_FOR_BREAKABLE_BLOCK = 0.3;
    private final static double CHANCE_FOR_RADIUS_POWERUP = 0.2;
    private final static double CHANCE_FOR_COUNTER_POWERUP = 0.8;
    private final FloorTile[][] tiles;
    private int width;
    private int height;
    private Player player = null;
    private EnemyManager enemyManager;
    private BombManager bombManager;
    private PowerUpManager powerUpManager = new PowerUpManager();
    private boolean isGameOver = false;
    private List<Observador> observers = new ArrayList<>();
    private static Floor instance;
    private SoundManager soundManager = new SoundManager();
    private List<FloorListener> floorListeners = new ArrayList<>();

    private Floor(int width, int height, int nrOfEnemies) {
        this.width = width;
        this.height = height;
        this.tiles = new FloorTile[height][width];
        placeBreakable();
        placeUnbreakableAndGrass();
        this.enemyManager = new EnemyManager();
        this.enemyManager.spawnEnemies(nrOfEnemies, width, height, tiles, this);
        addObserver(enemyManager);
        this.bombManager = new BombManager(soundManager);
    }

    public static Floor getInstance(int width, int height, int nrOfEnemies) {
        if (instance == null) {
            instance = new Floor(width, height, nrOfEnemies);
        }
        return instance;
    }

    public static void resetFloor() {
        instance = null;
    }

    @Override
    public void addObserver(Observador o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observador o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String s, Player player) {
        for (Observador o : observers) {
            o.update(s, player);
        }
    }

    public static int pixelToSquare(int pixelCoord) {
        return ((pixelCoord + BombermanComponent.getSquareSize() - 1) / BombermanComponent.getSquareSize()) - 1;
    }

    public FloorTile getFloorTile(int rowIndex, int colIndex) {
        return tiles[rowIndex][colIndex];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Collection<Enemy> getEnemyList() {
        return enemyManager.getEnemies();
    }

    public Iterable<Bomb> getBombList() {
        return bombManager.getBombList();
    }

    public int getBombListSize() {
        return bombManager.getBombListSize();
    }

    public Iterable<AbstractPowerUp> getPowerupList() {
        return powerUpManager.getPowerupList();
    }

    public Iterable<Explosion> getExplosionCoords() {
        return bombManager.getExplosionCoords();
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(boolean value) {
        isGameOver = value;
    }

    public void addToBombList(Bomb bomb) {
        bombManager.addBomb(bomb);
    }

    public void createPlayer(BombermanComponent bombermanComponent, Floor floor) {
        player = new Player(bombermanComponent, floor);
    }

    public int squareToPixel(int squareCoord) {
        return squareCoord * BombermanComponent.getSquareSize();
    }

    public void moveEnemies() {
        enemyManager.moveEnemies(this);
    }

    /**
     * This method creates a bomb if the given demands are satisfied.
     */
    public void bombCountdown() {
        bombManager.bombCountdown();
    }

    public void explosionHandler() {
        bombManager.explosionHandler(this);
    }

    public void addExplosion(Explosion explosion) {
        bombManager.addExplosion(explosion);
    }

    public void playerInExplosion() {
        for (Explosion tup : bombManager.getExplosionCoords()) {
            if (collidingCircles(player, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))) {
                isGameOver = true;
            }
        }
    }

    public void enemyInExplosion() {
        for (Explosion tup : bombManager.getExplosionCoords()) {
            Collection<Enemy> enemiesToBeRemoved = new ArrayList<>();
            for (Enemy e : enemyManager.getEnemies()) {
                if (collidingCircles(e, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))) {
                    enemiesToBeRemoved.add(e);
                    soundManager.playSound("muerte_de_enemigo.wav");
                }
            }
            for (Enemy e : enemiesToBeRemoved) {
                enemyManager.removeEnemy(e);
            }
        }
    }

    public void characterInExplosion() {
        playerInExplosion();
        enemyInExplosion();
    }

    private void placeBreakable() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double r = Math.random();
                if (r < CHANCE_FOR_BREAKABLE_BLOCK) {
                    tiles[i][j] = FloorTile.BREAKABLEBLOCK;
                }
            }
        }
        clearSpawn();
    }

    private void clearSpawn() {
        tiles[1][1] = FloorTile.FLOOR;
        tiles[1][2] = FloorTile.FLOOR;
        tiles[2][1] = FloorTile.FLOOR;
    }

    private void spawnPowerup(int rowIndex, int colIndex) {

        // Evitar el contorno del mapa (filas y columnas no accesibles)
        if (rowIndex <= 0 || rowIndex >= (height - 1) || colIndex <= 0 || colIndex >= (width - 1)) {
            System.out.println("Contorno del mapa, no se coloca powerup " + rowIndex + ", " + colIndex);
            return;
        }
        double r = Math.random();
        int x = squareToPixel(rowIndex) + BombermanComponent.getSquareMiddle();
        int y = squareToPixel(colIndex) + BombermanComponent.getSquareMiddle();
        if (r < 0.25) {
            powerUpManager.addPowerUp(new BombRadiusPU(x, y));
            System.out.println("Powerup BombRadiusPU colocado en: (" + rowIndex + ", " + colIndex + ")");
        } else if (r < 0.5) {
            powerUpManager.addPowerUp(new BombCounterPU(x, y));
            System.out.println("Powerup BombCounterPU colocado en: (" + rowIndex + ", " + colIndex + ")");
        } else if (r < 0.75) {
            powerUpManager.addPowerUp(new FreezeEnemiesPU(x, y));
            System.out.println("Powerup FreezeEnemiesPU colocado en: (" + rowIndex + ", " + colIndex + ")");
        } else {
            powerUpManager.addPowerUp(new BombDiagonalPU(x, y));
            System.out.println("Powerup BombDiagonalPU colocado en: (" + rowIndex + ", " + colIndex + ")");
        }
    }


	private void placeUnbreakableAndGrass () {
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		//Makes frame of unbreakable
		if ((i == 0) || (j == 0) || (i == height - 1) || (j == width - 1) || i % 2 == 0 && j % 2 == 0) {
		    tiles[i][j] = FloorTile.UNBREAKABLEBLOCK;
		    //Every-other unbreakable
		} else if (tiles[i][j] != FloorTile.BREAKABLEBLOCK) {
		    tiles[i][j] = FloorTile.FLOOR;
		}
	    }
	}
    }


    public boolean collisionWithEnemies() {
        for (Enemy enemy : enemyManager.getEnemies()) {
            if (collidingCircles(player, enemy.getX() - BombermanComponent.getSquareMiddle(), enemy.getY() - BombermanComponent.getSquareMiddle())) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionWithBombs(AbstractCharacter abstractCharacter) {
        boolean playerLeftBomb = true;

        for (Bomb bomb : bombManager.getBombList()) {
            if (abstractCharacter instanceof Player) {
                playerLeftBomb = bomb.isPlayerLeft();
            }
            if (playerLeftBomb && collidingCircles(abstractCharacter, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))) {
                return true;
            }
        }
        return false;
    }


    public boolean collisionWithBlock(AbstractCharacter abstractCharacter) {
        //Maybe create if statements to only check nearby squares
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getFloorTile(i, j) != FloorTile.FLOOR) {
                    boolean isIntersecting = squareCircleInstersect(i, j, abstractCharacter);
                    if (isIntersecting) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void collisionWithPowerup() {
        powerUpManager.checkCollisionWithPlayer(player);
    }


    public boolean squareHasBomb(int rowIndex, int colIndex) {
        for (Bomb b : bombManager.getBombList()) {
            if (b.getRowIndex() == rowIndex && b.getColIndex() == colIndex) {
                return true;
            }
        }
        return false;
    }


    public void checkIfPlayerLeftBomb() {
        for (Bomb bomb : bombManager.getBombList()) {
            if (!bomb.isPlayerLeft()) {
                if (!collidingCircles(player, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))) {
                    bomb.setPlayerLeft(true);
                }
            }
        }
    }

    public boolean bombCoordinateCheck(int eRow, int eCol, boolean open) {
        if (tiles[eRow][eCol] == FloorTile.BREAKABLEBLOCK) {
            tiles[eRow][eCol] = FloorTile.FLOOR;
            spawnPowerup(eRow, eCol);
        }

        if (tiles[eRow][eCol] != FloorTile.UNBREAKABLEBLOCK) {
            bombManager.addExplosion(new Explosion(eRow, eCol));
        }

        return tiles[eRow][eCol] == FloorTile.FLOOR ? open : false;
    }


    private boolean collidingCircles(AbstractCharacter abstractCharacter, int x, int y) {
        int a = abstractCharacter.getX() - x - BombermanComponent.getSquareMiddle();
        int b = abstractCharacter.getY() - y - BombermanComponent.getSquareMiddle();
        int a2 = a * a;
        int b2 = b * b;
        double c = Math.sqrt(a2 + b2);
        return (abstractCharacter.getSize() > c);
    }

    private boolean squareCircleInstersect(int row, int col, AbstractCharacter abstractCharacter) {
        //http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
        int characterX = abstractCharacter.getX();
        int characterY = abstractCharacter.getY();

        int circleRadius = abstractCharacter.getSize() / 2;
        int squareSize = BombermanComponent.getSquareSize();
        int squareCenterX = (col * squareSize) + (squareSize / 2);
        int squareCenterY = (row * squareSize) + (squareSize / 2);

        int circleDistanceX = Math.abs(characterX - squareCenterX);
        int circleDistanceY = Math.abs(characterY - squareCenterY);

        if (circleDistanceX > (squareSize / 2 + circleRadius)) {
            return false;
        }
        if (circleDistanceY > (squareSize / 2 + circleRadius)) {
            return false;
        }

        if (circleDistanceX <= (squareSize / 2)) {
            return true;
        }
        if (circleDistanceY <= (squareSize / 2)) {
            return true;
        }

        int cornerDistance = (circleDistanceX - squareSize / 2) ^ 2 +
                (circleDistanceY - squareSize / 2) ^ 2;

        return (cornerDistance <= (circleRadius ^ 2));
    }

    public void addFloorListener(FloorListener bl) {
        floorListeners.add(bl);
    }

    public void notifyListeners() {
        for (FloorListener b : floorListeners) {
            b.floorChanged();
        }
    }
}
