package Class;

import Abstract.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.EnumMap;


public class BombermanComponent extends JComponent implements FloorListener
{
    // Constants are static by definition.
    private final static int SQUARE_SIZE = 40; //Tamaño de los cuadrados
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 15;
    private final static int SQUARE_MIDDLE = SQUARE_SIZE/2;
    private final static int BOMB_ADJUSTMENT_1 =5;
    private final static int BOMB_ADJUSTMENT_2 =10;

	// Defining painting parameters
    private final static int PAINT_PARAMETER_13 = 13;
    private final static int PAINT_PARAMETER_15 = 15;
    private final static int PAINT_PARAMETER_17 = 17;
    private final static int PAINT_PARAMETER_18 = 18;
    private final static int PAINT_PARAMETER_19 = 19;
    private final static int PAINT_PARAMETER_20 = 20;
    private final static int PAINT_PARAMETER_24 = 24;
    private final Floor floor;
    private final AbstractMap<FloorTile, Image> imageMap;

    public BombermanComponent(Floor floor) {
	this.floor = floor;

	imageMap = new EnumMap<>(FloorTile.class);
	try{
		imageMap.put(FloorTile.FLOOR, ImageIO.read(getClass().getResource("/Tiles/suelo.png")));
		imageMap.put(FloorTile.UNBREAKABLEBLOCK, ImageIO.read(getClass().getResource("/Tiles/pared.png")));
		imageMap.put(FloorTile.BREAKABLEBLOCK, ImageIO.read(getClass().getResource("/Tiles/breakableblock.png")));
	} catch (IOException e) {
		throw new RuntimeException(e);
	}

    }

    // This method is static since each square has the same size.
    public static int getSquareSize() {
	return SQUARE_SIZE;
    }

    // This method is static since each square has the same size.
    public static int getSquareMiddle() {
	return SQUARE_MIDDLE;
    }

	/**
	 * Este metodo sobreescribe {@code getPreferredSize()} de JComponent y devuelve una Dimension correspondiente
	 * al producto de {@code getWidth} por SQUARE_SIZE y {@code getHeigth} por SQUARE_SIZE
	 * @return Dimension
	 */

	@Override
	public Dimension getPreferredSize() {
		//super.getPreferredSize(); //No le encuentro el uso
		return new Dimension(this.floor.getWidth() * SQUARE_SIZE, this.floor.getHeight() * SQUARE_SIZE);
    }

	/**
	 * Este metodo llama a repaint() el cual es un metodo de Component que internamente llama a
	 * paintComponent
	 */
    public void floorChanged() {
		repaint();
    }

	/**
	 * Este metodo sobreescribe al metodo {@code paintComponent} de la super clase indicando como debe dibujarse
	 * los componentes
	 * @param g the <code>Graphics</code> object to protect
	 */
    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g; //Castea like Eschoyez

		//Los for anidados recorren la matriz de bloques del suelo
		for (int rowIndex = 0; rowIndex < floor.getHeight(); rowIndex++) {
			for (int colIndex = 0; colIndex < floor.getWidth(); colIndex++) {

				FloorTile tile = floor.getFloorTile(rowIndex, colIndex);
				Image img = imageMap.get(tile);

				//g2d funciona como un pincel. Le cargo la pintura y despues pinto
				//g2d.setColor(imageMap.get(this.floor.getFloorTile(rowIndex, colIndex)));//Aca le cargo la pintura segun el mapa de colores

				//Si el bloque en [rowIndex][colIndex] es "rompible" llama a paintBreakableBlock
				if(floor.getFloorTile(rowIndex, colIndex)==FloorTile.BREAKABLEBLOCK){
					//paintBreakableBlock(rowIndex, colIndex, g2d);
					if (img != null) {
						g2d.drawImage(img, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
					}
				}
				//Si el bloque en [rowIndex][colIndex] es "irrompible" llama a paintUnbreakableBlock
				else if(floor.getFloorTile(rowIndex, colIndex)==FloorTile.UNBREAKABLEBLOCK){
					//paintUnbreakableBlock(rowIndex, colIndex, g2d);
					if (img != null) {
						g2d.drawImage(img, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
					}
				}
				//Si el bloque en [rowIndex][colIndex] es "comun" llama a paintFloor
				else{
					//paintFloor(rowIndex, colIndex, g2d);
					if (img != null) {
						g2d.drawImage(img, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
					}
				}
			}
		}
		// Paint player:
		paintPlayer(floor.getPlayer(), g2d);

		//Paint enemies
		for (Enemy e: floor.getEnemyList()) {
			paintEnemy(e, g2d);
		}

		//Paint powerups
		for (AbstractPowerUp p: floor.getPowerupList()) {
			if (p.getName().equals("BombCounter")) {
			g2d.setColor(Color.BLACK);
			} else if (p.getName().equals("BombRadius")) {
			g2d.setColor(Color.RED);
			}
			g2d.fillOval(p.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, p.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, p.getPowerUpSize(), p.getPowerUpSize());
		}

		//Paint bombs
		for (Bomb b: floor.getBombList()) {
			g2d.setColor(Color.RED);
			int bombX = floor.squareToPixel(b.getColIndex());
			int bombY = floor.squareToPixel(b.getRowIndex());
			g2d.fillOval(bombX + BOMB_ADJUSTMENT_1, bombY + BOMB_ADJUSTMENT_1, Bomb.getBOMBSIZE(), Bomb.getBOMBSIZE());
			g2d.setColor(Color.ORANGE);
			g2d.fillOval(bombX + BOMB_ADJUSTMENT_2, bombY + BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_2);
		}

		//Paint explosions
		g2d.setColor(Color.ORANGE);
        //AGREGUE ANIMACION EN LA EXPLOSIONES
        for (Explosion tup: floor.getExplosionCoords()) {
            Color fireColor = new Color(
                    255,
                    (int)(Math.random() * 150),
                    0
            ); // naranja variable
            g2d.setColor(fireColor);
            g2d.fillOval(
                    floor.squareToPixel(tup.getColIndex()) + 5,
                    floor.squareToPixel(tup.getRowIndex()) + 5,
                    Bomb.getBOMBSIZE(),
                    Bomb.getBOMBSIZE()
            );
        }

        //Muestra cuantos enemigos quedan en pantalla
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Enemigos restantes: " + floor.getEnemyList().size(), 10, 20);


    }

    private void paintBreakableBlock(int rowIndex, int colIndex, Graphics g2d){
		g2d.setColor(Color.lightGray);
		g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		g2d.setColor(Color.BLUE);
		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+10, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+10);
		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE);
		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10);
		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);

		g2d.drawLine(colIndex* SQUARE_SIZE+10, rowIndex*SQUARE_SIZE+1, colIndex*SQUARE_SIZE+10, rowIndex*SQUARE_SIZE+10);
		g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_MIDDLE+10, rowIndex*SQUARE_SIZE+1, colIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, rowIndex*SQUARE_SIZE+10);

		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+10, colIndex*SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE);
		g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_MIDDLE+1, rowIndex*SQUARE_SIZE+10, colIndex*SQUARE_SIZE+SQUARE_MIDDLE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE);

		g2d.drawLine(colIndex* SQUARE_SIZE+10, rowIndex*SQUARE_SIZE+1+SQUARE_MIDDLE, colIndex*SQUARE_SIZE+10, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10);
		g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_MIDDLE+10, rowIndex*SQUARE_SIZE+1+SQUARE_MIDDLE, colIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10);

		g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex*SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
		g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_MIDDLE+1, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex*SQUARE_SIZE+SQUARE_MIDDLE+1, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
    }

    private void paintUnbreakableBlock(int rowIndex, int colIndex, Graphics g2d){
	g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	g2d.setColor(Color.DARK_GRAY);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
    }

    private void paintFloor(int rowIndex, int colIndex, Graphics g2d){
	g2d.setColor(Color.white);
	g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	g2d.setColor(Color.CYAN);
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+10, colIndex * SQUARE_SIZE + 10, rowIndex * SQUARE_SIZE + 5);
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE, colIndex * SQUARE_SIZE + SQUARE_MIDDLE, rowIndex * SQUARE_SIZE + 5);
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex * SQUARE_SIZE + SQUARE_MIDDLE + 10, rowIndex * SQUARE_SIZE + 5);
    }

    private void paintEnemy(Enemy e, Graphics g2d){
	// Paint body
	g2d.setColor(Color.orange);
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, e.getSize(), e.getSize());
	// Paint brows
	g2d.setColor(Color.BLACK);
	// Paint eyes
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+4, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+9, 7, 7);
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_19, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+9, 7, 7);
	// Paint mouth
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+5, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, PAINT_PARAMETER_20, 2);
	// Fill eyes
	g2d.setColor(Color.RED);
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+5, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, 5, 5);
	g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, 5, 5);

    }

    private void paintPlayer(Player player, Graphics g2d){
	// Paint hat
	g2d.setColor(Color.BLUE);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_15, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT-2, PAINT_PARAMETER_15, PAINT_PARAMETER_15);
	// Paint body
	g2d.setColor(Color.LIGHT_GRAY);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
	// Paint face
	g2d.setColor(Color.PINK);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+3, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+3, player.getSize()-6, player.getSize()-6);
	// Paint eyes
	g2d.setColor(Color.BLACK);
	g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
	g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
    }
}
