import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * Runs a game of Tetris.
 * 
 * @version 2.0
 * @author Eric
 * @author Alec Fenichel
 */

public class Tetris {
	
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Object[] possibilities = {"New", "Load", "Quit"};
		int n = JOptionPane.showOptionDialog(null,
		"What would you like to do?",
		"Menu",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		possibilities,
		possibilities[2]);
		
		if (n == 0) {
			Object[] options = {"Easy",
            "Hard"};
			int m = JOptionPane.showOptionDialog(null,
			"Please choose a difficulty.",
			"Difficulty",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]);
			BoardPanel boardPanel;
			if (m == 1)
				boardPanel = new BoardPanel(10, 15, frame);
			else 
				boardPanel = new BoardPanel(10, 25, frame);

	        frame.add(boardPanel);

	        frame.pack();
	        frame.setVisible(true);

	        boardPanel.start();
	        
		} else if (n==1) {
			BoardPanel boardPanel = new BoardPanel(10, 25, frame);

	        frame.add(boardPanel);

	        frame.pack();
	        frame.setVisible(true);

	        boardPanel.loadGame();
	        
		} else if (n==2) {
			System.exit(0);
		}
    }
}
