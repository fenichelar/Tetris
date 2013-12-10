import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * A Tetris piece model for the square piece.
 *
 * @version 2.0
 * @author Alec Fenichel
 */

public class SPiece extends Piece{

	/** The Constant rotation0. */
	private final static boolean[][] rotation0;
	
	/** The Constant rotation1. */
	private final static boolean[][] rotation1;
	
	/** The Constant rotation2. */
	private final static boolean[][] rotation2;
	
	/** The Constant rotation3. */
	private final static boolean[][] rotation3;
	
	
	static {

        rotation0 = new boolean[][]
        {
				{false, false, false, false},
                {true, false, false, false},
                {true, true, false, false},
                {false, true, false, false}                               
        };
        
		rotation1 = new boolean[][] {
                {false, false, false, false},
                {false, false, false, false},
                {false,   true, true, false},
                {true, true, false, false} 
        };
        
		rotation3 = new boolean[][] {
                {false, false, false, false},
                {false, true, true, false},
                {true, true, false, false},
                {false, false, false, false} 
        };
        
        rotation2 = new boolean[][]
        {
				{false, false, false, false},
                {false, true, false, false},
                {false, true, true, false},
                {false, false, true, false}                               
        };
        
    }
	
	/** The Constant model. */
	private final static boolean[][][] model = {rotation0, rotation1, rotation2, rotation3};
	
	/**
	 * Instantiates a new s piece.
	 *
	 * @param field the field
	 * @param x the x
	 * @param y the y
	 */
	public SPiece(PlayingField field, int x, int y) {
        super(field, x, y, model, Color.GREEN);
    }
	
	/* (non-Javadoc)
	 * @see Piece#toString()
	 */
	public String toString() {
    	return "Z Piece";
    }
}
