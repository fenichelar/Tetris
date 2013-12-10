import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

// TODO: Auto-generated Javadoc
/**
 * A Tetris piece. Can be rotated, moved, and settled on to a board.
 * 
 * @version 2.0
 * @author Eric
 * @author Sundeep
 * @author Alec Fenichel
 */

public abstract class Piece {

    /** The color. */
    private final Color color;
    
    /** The model. */
    private final boolean[][][] model;
    
    /** The field. */
    private final PlayingField field;
    
    /** The rotation index. */
    private int rotationIndex = 0;
    
    /** The y. */
    private int x, y;

    /**
     * Make a piece.
     * 
     * @param field
     *            the playing field this piece is on.
     * @param x
     *            the initial x position of the top left of the piece.
     * @param y
     *            the initial x position of the top left of the piece.
     * @param model
     *            a 4-length array of 2D boolean arrays that map out the piece.
     *            Each 2D array describes the shape of the piece in one of its 4
     *            rotations.
     * @param color
     *            The desired color.
     */
    
    public Piece(PlayingField field, int x, int y, boolean[][][] model, Color color)
    {
		this.field = field;
		this.x = x;
		this.y = y;
        this.color = color;
        this.model = model;
    }

    /**
     * Attempt to move a piece to a given location, with it's current rotation.
     * If able to move, go ahead and move the piece. If not, only return false
     * 
     * @param newX the new x position to move to
     * @param newY the new y position to move to
     * @return true if move was successful, false if blocked.
     */
    
    public boolean attemptMove(int newX, int newY) {
        boolean collision = false;

        for (int i = 0; i < model[rotationIndex].length && !collision; i++) {
            for (int j = 0; j < model[rotationIndex][0].length && !collision; j++) {
                if (model[rotationIndex][i][j] && !field.isTileVacant(newX + i, newY + j))
				{
                    collision = true;
                }
            }
        }

        if (!collision)
        {
            x = newX;
            y = newY;
        }
        field.checkForClears();
        return !collision;
    }

    /**
     * Attempt to move to the next rotation index of the piece.
     * If able to, rotate. If not, return false
     * 
     * @return true if the rotation was successful.
     */
    
    public boolean attemptRotation() {
        int oldRot = rotationIndex;
        rotationIndex = (rotationIndex + 1) % 4;
        if (attemptMove(x, y))
        {
			return true;
		}
		else
		{
			rotationIndex = oldRot;
			return false;
		}
    }

    /**
     * Map a piece to its given board. This will fill in the tile colors on the
     * board. Make sure to trash the piece after this, or it will collide with
     * the tiles it just colored!
     */
    
    public void settlePiece() {
        for (int i = 0; i < model[rotationIndex].length; i++)
            for (int j = 0; j < model[rotationIndex][0].length; j++)
                if (model[rotationIndex][i][j])
                    field.setTileColor(x + i, y + j, color);
    }

    /**
     * Draw the piece as a set of colored rectangles, assuming a certain
     * tileDim;.
     *
     * @param g graphics context.
     * @param tileDimension the pixel-wise dimension of a tile.
     */
    
    public void draw(Graphics g, Dimension tileDimension) {
        g.setColor(color);
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < model[0].length; j++)
                if (model[rotationIndex][i][j])
                    g.fill3DRect((x+i) * tileDimension.width, (y + j) * tileDimension.height,
                            tileDimension.width, tileDimension.height, true);
    }

	/**
	 * Gets the color.
	 *
	 * @return the color of the piece
	 */
    
    public Color getColor() {
        return color;
    }

    /**
     * Gets the x.
     *
     * @return the x position of the piece
     */
    
    public int getX() {
        return x;
    }
    
    /**
     * Gets the y.
     *
     * @return the y position of the piece
     */
    
    public int getY() {
        return y;
    }
    
    /**
     * Sets the x.
     *
     * @param x the new x position
     */
    
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Sets the y.
     *
     * @param y the new y position
     */
    
    public void setY(int y) {
    	this.y = y;
    }
    
    /**
     * To string.
     *
     * @return the name of the piece
     */
    
    public String toString() {
    	return "Piece";
    }
}
