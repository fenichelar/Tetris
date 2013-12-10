import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The game board. Will start the game, and respond to user input.
 * 
 * @version 2.0
 * @author Eric Fruchter
 * @author Sundeep Ghuman
 * @author Alec Fenichel
 */

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    /** The field. */
    private PlayingField field;
    
    /** The name. */
    private String name;
    
    /** The num col. */
    private int numCol;
    
    /** The num row. */
    private int numRow;
    
    /** The side width. */
    private int sideWidth;
    
    /** The active piece. */
    private Piece activePiece;
    
    /** The next piece. */
    private Piece nextPiece;
    
    /** The timer. */
    private Timer timer;
    
    /** The frame. */
    private JFrame frame;
    
    /** The tile dimension. */
    private Dimension tileDimension = new Dimension(30, 30);
    
    /** The rand. */
    private Random rand = new Random();
    
    /**
     * Creates a game board and starts the game.
     *
     * @param cols number of columns in the game (width)
     * @param rows number of rows in the game (height)
     * @param frame the frame
     */
    public BoardPanel(int cols, int rows, JFrame frame) {
    	
    	name = "Player";
    	
    	this.frame=frame;
    	
    	numCol = cols;
    	numRow = rows;
    	sideWidth = 200;
    	
        setPreferredSize(new Dimension(numCol*tileDimension.width+sideWidth, numRow * tileDimension.height));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyController());

        field = new PlayingField(cols, rows);

        timer = new Timer(1000, new FallListener());
        
        nextPiece = generateNextPiece();
        generateNewPiece();
    }

    /**
     * Start the Tetris game.
     */
    public void start() {
    	name = (String)JOptionPane.showInputDialog(
                null,
                "Please enter your name.",
                "Player Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
    	
    	if (name==null)
    		name = "Player";
    	else if (name.length()==0)
    		name = "Player";
    	
    	timer.start();
    }
    
    

    /**
     * Set active to a new, random piece.
     */
    private void generateNewPiece() {    	
    	activePiece = nextPiece;
    	nextPiece = generateNextPiece();
    }
    
    /**
     * Generate next piece.
     *
     * @return the piece
     */
    private Piece generateNextPiece() {
    	switch (rand.nextInt(7)) {
        	case 0:
        		return new SquarePiece(field, field.WIDTH / 2 - 2, -1);
        	case 1:
        		return new LinePiece(field, field.WIDTH / 2 - 2, -2);
            case 2:
            	return new JPiece(field, field.WIDTH / 2 - 2, -2);
            case 3:
            	return new TPiece(field, field.WIDTH / 2 - 2, -2);
            case 4:
            	return new ZPiece(field, field.WIDTH / 2 - 2, -2);
            case 5:
            	return new LPiece(field, field.WIDTH / 2 - 2, -2);
            case 6:
            	return new SPiece(field, field.WIDTH / 2 - 2, -2);
		}
    	return new SquarePiece(field, field.WIDTH / 2 - 2, -1);
	}

    /**
     * Move the current piece down and checks for a win/loss or placement
     * situation. If the piece fails to move down anymore (attemptMove returns false),
     * then the piece settles.
     */
    private void moveDownActive() {

        if (!activePiece.attemptMove(activePiece.getX(), activePiece.getY() + 1))
        {
            activePiece.settlePiece();
            generateNewPiece();
        }

        if (field.isGameOver()) {
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(null, "Would you like to play play again?", "You Lost", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION)
            {
				field.resetBoard();
				timer.restart();
			}
			else 
			{
				System.exit(0);
			}
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileDimension.width = (this.getWidth()-sideWidth) / numCol;
        tileDimension.height = this.getHeight() / numRow;
        this.setSize(new Dimension(numCol*tileDimension.width+sideWidth, numRow * tileDimension.height));
        field.drawBoard(g, tileDimension);
        activePiece.draw(g, tileDimension);
        g.setColor(Color.RED);
        g.drawString("Name: ", this.getWidth()-sideWidth + 15, 40);
        g.drawString(name, this.getWidth()-sideWidth + 15, 60);
        g.setColor(Color.CYAN);
        g.drawString("Score: "+ field.getScore(), this.getWidth()-sideWidth + 15, 100);
        g.setColor(Color.ORANGE);
        g.drawString("Level: "+ field.getLevel(), this.getWidth()-sideWidth + 15, 140);
        g.setColor(Color.GREEN);
        g.drawString("Next Piece:", this.getWidth()-sideWidth + 15, 180);
        g.drawString(nextPiece.toString(), this.getWidth()-sideWidth + 15, 200);
        g.setColor(Color.WHITE);
        g.drawString("Right Arrow = Right", this.getWidth()-sideWidth + 15, 260);
        g.drawString("Left Arrow = Left", this.getWidth()-sideWidth + 15, 280);
        g.drawString("Down Arrow = Down", this.getWidth()-sideWidth + 15, 300);
        g.drawString("Escape = Menu", this.getWidth()-sideWidth + 15, 320);
    }

    /**
     * The timer action. Each time step, move the piece down.
     *
     * @see FallEvent
     */
    
    private class FallListener implements ActionListener {    
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent event) {
			if (activePiece != null)
				moveDownActive();
						
			if(field.getScore() >= 1000 && field.getScore() < 2000){
				timer.setDelay(900);
				field.setLevel(2);
			}
			if(field.getScore() >= 2000 && field.getScore() < 3000){
				timer.setDelay(800);
				field.setLevel(3);
			}
			if(field.getScore() >= 3000 && field.getScore() < 4000){
				timer.setDelay(700);
				field.setLevel(4);
			}
			if(field.getScore() >= 4000 && field.getScore() < 5000){
				timer.setDelay(600);
				field.setLevel(5);
			}
			if(field.getScore() >= 5000 && field.getScore() < 6000){
				timer.setDelay(500);
				field.setLevel(6);
			}
			if(field.getScore() >= 6000 && field.getScore() < 7000){
				timer.setDelay(400);
				field.setLevel(7);
			}
			if(field.getScore() >= 7000 && field.getScore() < 8000){
				timer.setDelay(300);
				field.setLevel(8);
			}
			if(field.getScore() >= 8000 && field.getScore() < 9000){
				timer.setDelay(200);
				field.setLevel(9);
			}
			if(field.getScore() >= 9000 && field.getScore() < 10000){
				timer.setDelay(100);
				field.setLevel(10);
			}
			if(field.getScore() >= 10000){
				timer.setDelay(10);
				field.setLevel(11);
			}
			repaint();
		}
	}

    /**
     * Handle keystrokes.
     * 
     * @author eric
     * 
     */
    private class KeyController extends KeyAdapter {
        
        /* (non-Javadoc)
         * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(final KeyEvent key) {
            if (activePiece != null) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        activePiece.attemptMove(activePiece.getX() + 1, activePiece.getY());
                        break;
                    case KeyEvent.VK_LEFT:
                        activePiece.attemptMove(activePiece.getX() - 1, activePiece.getY());
                        break;
                    case KeyEvent.VK_SPACE:
                        activePiece.attemptRotation();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDownActive();
                        break;
                    case KeyEvent.VK_ESCAPE:
                    	menuOptions();
                        break;
                }
            }
        }
    }
    
    
    /**
     * Menu options.
     */
    public void menuOptions() {
    	
    	timer.stop();
		
		Object[] possibilities = {"Save", "Load", "New", "Quit"};
		String s = (String)JOptionPane.showInputDialog(
                null,
                "What would you like to do?",
                "Menu",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Save");
		
		if(s == "Save") {
			try {
				saveGame();
			}catch(Exception e) {
				
			}
		} else if (s == "Load") {
			try {
				loadGame();
			}catch(Exception e) {
				
			}
		} else if (s == "New") {
			frame = new JFrame("Tetris");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
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
	        
		} else if (s == "Quit") {
			System.exit(0);
		} else {
			timer.start();
		}

	repaint();
    }
    
    /**
     * Save game.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void saveGame() throws IOException {
    	
    	FileWriter fw;
        BufferedWriter bw;
        PrintWriter outFile;
    	
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Please enter the file name.",
                "Save",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
		
		if (s==null)
			fw = new FileWriter(name + ".txt");
		else if (s.length()==0)
			fw = new FileWriter(name + ".txt");
		else
			fw = new FileWriter(s + ".txt");
		
    	bw = new BufferedWriter(fw);
    	outFile = new PrintWriter(bw);
    	outFile.print(numCol);
    	outFile.println();
    	outFile.print(numRow);
    	outFile.println();
    	outFile.print(this.getWidth());
    	outFile.println();
    	outFile.print(this.getHeight());
    	outFile.println();
    	outFile.print(field.toString());
    	outFile.println();
		outFile.print(field.getScore());
		outFile.println();
		outFile.print(field.getLevel());
		outFile.println();
		outFile.print(nextPiece.toString());
		outFile.println();
		outFile.print(activePiece.toString());
		outFile.println();
		outFile.print(activePiece.getX());
		outFile.println();
		outFile.print(activePiece.getY());
		outFile.println();
		outFile.print(name);
		outFile.close();
		
		Object[] options = {"Continue",
        "Quit"};
		int m = JOptionPane.showOptionDialog(null,
		"What would you like to do now?",
		"Continue",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,
		options[0]);
		if (m == 0)
			timer.start();
		else 
			System.exit(0);
    }
    
	/**
	 * Load game.
	 */
	public void loadGame() {
		
		File file = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();
        Scanner scanner = null;
        try {
        	scanner = new Scanner(file);
        
        numCol = Integer.parseInt(scanner.nextLine());
        numRow = Integer.parseInt(scanner.nextLine());
        
        field = new PlayingField(numCol, numRow);
        
        int col = Integer.parseInt(scanner.nextLine());
        int row = Integer.parseInt(scanner.nextLine());
        
        this.setSize(new Dimension(col,row));
        frame.setSize(new Dimension(col,row));
        
        for(int y = 0; y < numRow; y++) {
        	for(int x = 0; x < numCol; x++) {
        		int n = scanner.nextInt();
        		if(n==0)
        			field.setTileColor(x, y, null);
        		else if(n==1)
                    field.setTileColor(x, y, Color.CYAN);
        		else if(n==2)
                    field.setTileColor(x, y, Color.YELLOW);
        		else if(n==3)
                    field.setTileColor(x, y, Color.MAGENTA);
        		else if(n==4)
                    field.setTileColor(x, y, Color.RED);
        		else if(n==5)
                	field.setTileColor(x, y, Color.GREEN);
        		else if(n==6)
                    field.setTileColor(x, y, Color.ORANGE);
        		else if(n==7)
                    field.setTileColor(x, y, Color.BLUE);
            }
    	}
        
        scanner.nextLine();
        
        field.setScore(Integer.parseInt(scanner.nextLine()));

        field.setLevel(Integer.parseInt(scanner.nextLine()));

        String p = scanner.nextLine();
        if(p.equals("Line Piece"))
        	nextPiece = new LinePiece(field, field.WIDTH / 2, -2);
        if(p.equals("Square Piece"))
            nextPiece = new SquarePiece(field, field.WIDTH / 2, -2);
        if(p.equals("S Piece"))
            nextPiece = new ZPiece(field, field.WIDTH / 2, -2);
        if(p.equals("Z Piece"))
            nextPiece = new SPiece(field, field.WIDTH / 2, -2);
        if(p.equals("L Piece"))
            nextPiece = new JPiece(field, field.WIDTH / 2, -2);
        if(p.equals("J Piece"))
            nextPiece = new LPiece(field, field.WIDTH / 2, -2);
        if(p.equals("T Piece"))
            nextPiece = new TPiece(field, field.WIDTH / 2, -2);
        
        String a = scanner.nextLine();
        if(a.equals("Line Piece"))
        	activePiece = new LinePiece(field, field.WIDTH / 2, -2);
        if(a.equals("Square Piece"))
        	activePiece = new SquarePiece(field, field.WIDTH / 2, -2);
        if(a.equals("S Piece"))
        	activePiece = new ZPiece(field, field.WIDTH / 2, -2);
        if(a.equals("Z Piece"))
        	activePiece = new SPiece(field, field.WIDTH / 2, -2);
        if(a.equals("L Piece"))
        	activePiece = new JPiece(field, field.WIDTH / 2, -2);
        if(a.equals("J Piece"))
        	activePiece = new LPiece(field, field.WIDTH / 2, -2);
        if(a.equals("T Piece"))
        	activePiece = new TPiece(field, field.WIDTH / 2, -2);
        
        activePiece.setX(Integer.parseInt(scanner.nextLine()));
        activePiece.setY(Integer.parseInt(scanner.nextLine()));
        
        name = scanner.nextLine();
        
        timer.start();
        
        } catch(Exception e) {
        	JOptionPane.showMessageDialog(null,
        		    "Please choose a valid file.",
        		    "Bad File",
        		    JOptionPane.ERROR_MESSAGE);
        	menuOptions();
        }
	}
}

