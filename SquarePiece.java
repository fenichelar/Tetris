import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * A Tetris piece model for the square piece.
 *
 * @version 2.0
 * @author Eric
 * @author Sundeep
 * @author Alec Fenichel
 */

public class SquarePiece extends Piece{
	
    /** The Constant rotation0. */
    private final static boolean[][] rotation0 = new boolean[][] {
                {false, false, false, false},
                {true, true, false, false},
                {true, true, false, false},
                {false, false, false, false}             
        };
    
    /** The Constant model. */
    private final static boolean[][][] model = {rotation0, rotation0, rotation0, rotation0};
        
    /**
     * Instantiates a new square piece.
     *
     * @param field the field
     * @param x the x
     * @param y the y
     */
    public SquarePiece(PlayingField field, int x, int y)
    {
        super(field, x, y, model, Color.YELLOW);
    }
    
    /* (non-Javadoc)
     * @see Piece#toString()
     */
    public String toString() {
    	return "Square Piece";
    }
}
