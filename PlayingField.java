import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayingField.
 * 
 * @version 2.0
 * @author Alec Fenichel
 */
public class PlayingField {
	
    /** The board. */
    private final Color[][] board;
    
    /** The empty tile. */
    private Color emptyTile = Color.GRAY;
    
    /** The level. */
    private int level = 1;
    
    /** The score. */
    private int score = 0;

    /** The HEIGHT. */
    public final int WIDTH, HEIGHT;

    /**
     * Create a playing field.
     * 
     * @param cols number of columns in the field (width)
     * @param rows number of rows in the width (height)
     */
    public PlayingField(int cols, int rows) {

		WIDTH = cols;
		HEIGHT = rows;
		
        board = new Color[WIDTH][HEIGHT];
        
    }

    /**
     * Checks if the tile at position is free.
     * 
     * @param x the x position to check
     * @param y the y position to check
     * @return true if piece is legal and vacant to move to.
     */
    public boolean isTileVacant(int x, int y) {
        if (x < 0 || x >= board.length || y >= board[0].length)
            return false;

        if (y < 0)
            return true;

        return board[x][y] == null;
    }

    /**
     * Set the tile at a given position. Tiles wont be set if they are off-sceen
     * 
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @param color color to set tile to.
     */
    
    public void setTileColor(int x, int y, Color color) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length)
            board[x][y] = color;
    }

    /**
     * Check the top row for a loss condition.
     * 
     * @return true if loss detected, false otherwise.
     */
    
    public boolean isGameOver() {
        for (int x = 0; x < board.length; x++) {
            if (board[x][0] != null) //check top row
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Clear the board.
     */
    
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Draw the game board.
     *
     * @param g Graphics context to draw on (from BoardPanel)
     * @param tileDim the dimensions of an individual tile
     */
    
    public void drawBoard(Graphics g, Dimension tileDim) {

		boolean drawGrid = false;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == null)
                {
                    g.setColor(emptyTile);
                    if (drawGrid)
						g.draw3DRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height, true);
                    else
						g.drawRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height);
                }
				else
                {
                    g.setColor(board[x][y]);
                    g.fill3DRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height, true);
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	String board = "";
    	for(int r = 0; r < HEIGHT; r++) {
    		for(int c = 0; c < WIDTH; c++) {
    			if(this.board[c][r] == null) 
    				board+= "0 ";
    			if(this.board[c][r] == Color.CYAN)
    				board+= "1 ";
    			if(this.board[c][r] == Color.YELLOW)
    				board+= "2 ";
    			if(this.board[c][r] == Color.MAGENTA)
    				board+= "3 ";
    			if(this.board[c][r] == Color.RED)
    				board+= "4 ";
    			if(this.board[c][r] == Color.GREEN)
    				board+= "5 ";
    			if(this.board[c][r] == Color.ORANGE)
    				board+= "6 ";
    			if(this.board[c][r] == Color.BLUE)
    				board+= "7 ";
    		}
    	}
    	return board;
    }
    
    /**
     * Check for clears.
     */
    public void checkForClears() {
    	int counter = 0;
    	int temp = 0;
    	int multiplyer = 0;
    	for(int y = HEIGHT-1; y > 0; y--) {
    		for(int x = 0; x < WIDTH; x++) {
    			if(board[x][y] != null)
    				counter++;
    			if(counter == WIDTH) {
    				temp += 250 + (level-1)*100;
    				multiplyer++;
    				for(int i = y; i > 0; i--)
    					for(int j = 0; j < WIDTH-1; j++) {
    						board[j][i] = board[j][i-1];
    				}
    			}
    		}
    		counter = 0;
    	}
    	score += temp*multiplyer;
    }
    
    /**
     * Gets the board.
     *
     * @return the board
     */
    public Color[][] getBoard() {
    	return board;
    }
    
    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
    	return level;
    }
    
    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
    	return score;
    }
    
    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(int level) {
    	this.level = level;
    }
    
    /**
     * Sets the score.
     *
     * @param score the new score
     */
    public void setScore(int score) {
    	this.score = score;
    }
}
