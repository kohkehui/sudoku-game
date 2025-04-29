package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;

import javax.swing.*;

import java.util.TimerTask;


/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   JMenuBar MenuBar = new JMenuBar(); // the menu-bar
   JMenu SubMenu;
   JMenuItem MenuItem;
   JPanel SubPanel = new JPanel();
   //WelcomePage WelcomePage = new WelcomePage(); // welcome page

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      setJMenuBar(MenuBar);

      cp.add(board, BorderLayout.CENTER);
      cp.add(SubPanel, BorderLayout.SOUTH);
      
      
      // Add a button to the south to re-start the game
      JButton btnRestart = new JButton("Restart");
      SubPanel.add(btnRestart);
      btnRestart.addActionListener(new ActionListener() {
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  ImageIcon warnIcon = new ImageIcon("warn1.gif");
    		  String filepath = "boink.wav";
    		  Object[] options = { "Yes", "No" };
 	    	  JLabel restart = new JLabel();
 	    	  restart.setText("Start a new game? \nAll the progress of current game will not be saved!");
    		  restart.setFont(new Font("Actor", Font.BOLD, 20));
    		  
    		  int n = JOptionPane.showOptionDialog(null, restart,
    				  "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, warnIcon, options, options[0]);
    		  if (n == JOptionPane.YES_OPTION) {
    			  board.Reset();
    		  } else {
    			  setDefaultCloseOperation(JOptionPane.CANCEL_OPTION); 
    	      }
          }
          });
          
      
      //File Menu
      JMenu Menu = new JMenu("File"); // each menu in the menu-bar
      MenuBar.add(Menu); // menu is added into menu button
      MenuItem = new JMenuItem("New Game");
      Menu.add(MenuItem); // the menu adds this item
      MenuItem.addActionListener(new ActionListener() {
    	  @Override
    	  public void actionPerformed(ActionEvent e) {		          
    		Object[] levels = { "Easy", "Advanced", "Expert", "Hell" };
    		String difficulty = (String) JOptionPane.showInputDialog(null,
    				"Current game progress will be lost! \nSelect Game Mode:", "New Game",
    				JOptionPane.PLAIN_MESSAGE, null, levels, levels[0]);
    		// If a string was returned, restart game to difficulty chosen.
    		if ((difficulty != null) && (difficulty.length() > 0)) {
    			if(difficulty == levels[0]) {
    				board.newGame();
				} if(difficulty == levels[1]) {
					board.Advanced();
				} if(difficulty == levels[2]) {
					board.Expert();
				} if(difficulty == levels[3]) {
					board.Hell();
				} return;
			}
		  }
		  });
      
      Menu.addSeparator();
      
      MenuItem = new JMenuItem("Exit");
      Menu.add(MenuItem); // the menu adds this item
      MenuItem.addActionListener(new ActionListener() {
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  
    		  ImageIcon quesIcon = new ImageIcon("lose2.gif");
    		  Object[] options = { "See you soon", "Not yet" };
    		  int n = JOptionPane.showOptionDialog(null, "You will exit the game now!\nBye!", "SEE YOU AGAIN?",
    				  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, quesIcon, options, options[0]);
    		  if (n == JOptionPane.YES_OPTION) {
		    	System.exit(0);
		      } else {
					setDefaultCloseOperation(JOptionPane.CANCEL_OPTION);
			  }
		  }
	  });
      
      Menu = new JMenu("Theme");
      MenuBar.add(Menu);
      
      MenuItem = new JMenuItem("Light Mode");
      Menu.add(MenuItem);
	  MenuItem.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			board.lightcolor();
			
	        }
		});
		
	  MenuItem = new JMenuItem("Dark Mode");
	  Menu.add(MenuItem);
	  MenuItem.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			board.darkcolor();
			
		}
		});
		
	  Menu = new JMenu("Hints");
	  MenuBar.add(Menu);
	  
	  MenuItem = new JMenuItem("Reveal All");
	  Menu.add(MenuItem);
	  MenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			board.RevealAll();
			ImageIcon LoseIcon = new ImageIcon("");
		    JOptionPane.showMessageDialog(null, "Try Again!", null, JOptionPane.INFORMATION_MESSAGE, LoseIcon);
			
		}
		});
		

	  MenuItem = new JMenuItem("Reveal One");
	  Menu.add(MenuItem);
	  MenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			board.RevealOne();
			
		}

		});
		
	  //menu for Help
	  Menu = new JMenu("Help");
	  MenuBar.add(Menu);
	  MenuItem = new JMenuItem("How to Play");
	  Menu.add(MenuItem);
	  MenuItem.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			ImageIcon quesIcon = new ImageIcon("howtoplay1.jpg");
			JOptionPane.showMessageDialog(null, "Each row, column and 3 by 3 sub grids\n cannot have the same number.", null,JOptionPane.INFORMATION_MESSAGE, quesIcon);
			
	     }
	     });
	
	  Menu.add(MenuItem);
	  board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true);
      
      
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
	
      // [TODO 1] Check "Swing program template" on how to run
      //  the constructor of "SudokuMain"
	  // Run GUI codes in Event-Dispatching thread for thread-safety
	  SwingUtilities.invokeLater(new Runnable() {
	     @Override
	     public void run() {
	        new SudokuMain();  // Let the constructor do the job
	     }
	  });
   }
}