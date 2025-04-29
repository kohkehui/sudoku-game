package sudoku;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.Timer;

/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for JTextField's colors and fonts
   //  to be chosen based on CellStatus
   // Light mode colours
   public static final Color BG_GIVEN0 = new Color(105, 200, 205); // filled cells pale yellow
   public static final Color FG_GIVEN0 = new Color (32, 32, 32); // filled text black
   public static final Color FG_NOT_GIVEN0 = new Color(65, 75, 86); // entered text white
   public static final Color BG_TO_GUESS0 = new Color(210, 214, 217); // empty cells yellow
   public static final Color BG_CORRECT_GUESS0 = new Color(0, 204, 102); // correct entry mint
   public static final Color BG_WRONG_GUESS0 = new Color(255,153,153); // wrong entry pastel red
   	
   // Dark mode colours
   public static final Color BG_GIVEN1 = new Color(34, 31, 31); // filled cells dark blue
   public static final Color FG_GIVEN1 = new Color(211, 211, 211); // filled text off-white
   public static final Color FG_NOT_GIVEN1 = new Color(132, 165, 184); // entered text 
   public static final Color BG_TO_GUESS1 = new Color(65, 75, 86); // empty cells brown
   public static final Color BG_CORRECT_GUESS1 = new Color(0, 78, 56); // correct entry dark green
   public static final Color BG_WRONG_GUESS1 = new Color(156,39,6); // wrong entry dark red
   
   public static final Font FONT_NUMBERS = new Font("Lato", Font.PLAIN, 28);

   private Timer highlightTimer;

   // Border and grid colours 
   Border rowupGrid = BorderFactory.createMatteBorder(0,0,5,0,new Color(112,128,144)); //slate grey
   Border rowdownGrid = BorderFactory.createMatteBorder(5,0,0,0, new Color(112,128,144));
   Border colleftGrid = BorderFactory.createMatteBorder(0,0,0,5, new Color(112,128,144));
   Border colrightGrid = BorderFactory.createMatteBorder(0,5,0,0, new Color(112,128,144));
   Border downright = BorderFactory.createMatteBorder(0,0,5,5, new Color(112,128,144)); 
   Border upright = BorderFactory.createMatteBorder(5,0,0,5, new Color(112,128,144));
   Border upleft = BorderFactory.createMatteBorder(5,5,0,0, new Color(112,128,144));
   Border downleft = BorderFactory.createMatteBorder(0,5,5,0, new Color(112,128,144));
   Border inner = BorderFactory.createMatteBorder(3,3,3,3, Color.WHITE);
   
   // Define properties (package-visible)
   /** The row and column number [0-8] of this cell */
   int row, col;
   /** The puzzle number [1-9] for this cell */
   int number;
   /** The status of this cell defined in enum CellStatus */
   CellStatus status;
   /** The status of dark mode */
   boolean color = true;

   /** Constructor */
   public Cell(int row, int col) {
      super();   // JTextField
      this.row = row;
      this.col = col;
      // Inherited from JTextField: Beautify all the cells once for all
      super.setHorizontalAlignment(JTextField.CENTER);
      super.setFont(FONT_NUMBERS);
      // Initialize timer with a delay of 3000 milliseconds (3 seconds)
      highlightTimer = new Timer(3000, e -> resetBackground());
      highlightTimer.setRepeats(false);  // Ensure the timer only runs once per event
   }

   public void highlightConflict() {
      setBackground(Color.RED);
      if (highlightTimer.isRunning()) {
         highlightTimer.restart();  // Restart the timer if it's already running
      } else {
         highlightTimer.start();  // Start the timer
      }
   }

   public void resetBackground() {
      // Reset background based on cell status or color mode
      if (status == CellStatus.GIVEN) {
         setBackground(color ? BG_GIVEN1 : BG_GIVEN0);
      } else if (status == CellStatus.TO_GUESS) {
         setBackground(color ? BG_TO_GUESS1 : BG_TO_GUESS0);
      } else {
         setBackground(getBackground());  // Keep current background if not specified
      }
   }

   // Method to check for conflicts
   public boolean checkForConflicts(int[][] board) {
      int currentValue;
      try {
         currentValue = Integer.parseInt(getText().trim());
      } catch (NumberFormatException e) {
         currentValue = 0; // Handle non-integer or empty input as '0' or another invalid number
      }

      if (currentValue == 0) { // Skip conflict checking for empty or invalid cells
         return false; // Return false because there can't be a conflict without a valid number
      }

      boolean conflict = false;
      // Check row and column for conflicts
      for (int i = 0; i < 9; i++) {
         if ((currentValue == board[row][i] && i != col) || 
            (currentValue == board[i][col] && i != row)) {
            conflict = true;
            break;
         }
      }

      // Check 3x3 block
      if (!conflict) { // Only check the block if no conflict found in row or column
         int startRow = row - row % 3;
         int startCol = col - col % 3;
         for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                  if (currentValue == board[i][j] && (i != row || j != col)) {
                     conflict = true;
                     break;
                  }
            }
            if (conflict) {
                  break; // Break outer loop if conflict is found
            }
         }
      }

      // Highlight if conflict is found
      if (conflict) {
         setBackground(Color.RED);
      }
      
      return conflict; // Return the conflict status
   }


  

   /** Reset this cell for a new game, given the puzzle number and isGiven */
   public void newGame(int number, boolean isGiven) {
      this.number = number;
      status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
      paint();    // paint itself
   }

   /** This Cell (JTextField) paints itself based on its status */
   public void paint() {
	   	super.setBorder(inner);
	   	/** Sets the colour for the outside borders and inner grid */
	   	if ((row == 2 && col == 2) || (row == 2 && col == 5) || (row == 5 && col == 2) || (row == 5 && col == 5) || (row == 8 && col == 2) || (row == 8 && col ==5) || (row == 8 && col == 8) || (row == 2 && col == 8) || (row == 5 && col == 8)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(downright ,inner));
	   	}
	   	if((row == 0 && col == 2 || row == 0 && col == 5) || (row == 0 && col == 8)) { 
	   		super.setBorder(BorderFactory.createCompoundBorder(upright ,inner));
	   	}   	
	   	if(row == 0 && col == 0) {
	   		super.setBorder(BorderFactory.createCompoundBorder(upleft ,inner));
	   	}   	
	   	if((row == 8 && col == 0) || (row == 2 && col == 0) || (row == 5 && col == 0)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(downleft ,inner));
	   	}
	   	if ((row == 8 && col == 1) || (row == 8 && col == 3)  || (row == 8 && col == 4) || (row == 8 && col == 6) || (row == 8 && col == 7) || (row == 2 && col == 1) || (row == 2 && col == 3)  || (row == 2 && col == 4) || (row == 2 && col == 6) || (row == 2 && col == 7) || (row == 5 && col == 1) || (row == 5 && col == 3) ||  (row == 5 && col == 4) || (row == 5 && col == 6) || (row == 5 && col == 7)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(rowupGrid ,inner));
	   	}
	   	if ((row == 1 && col == 2) || (row == 3 && col == 2) || (row == 4 && col == 2) || (row == 6 && col == 2) || (row == 7 && col == 2) || (row == 1 && col == 5) || (row == 3 && col == 5) || (row == 4 && col == 5) || (row == 6 && col == 5) || (row == 7 && col == 5) || (row == 1 && col == 8) || (row == 3 && col == 8) || (row == 4 && col == 8) || (row == 6 && col == 8) || (row == 7 && col == 8)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(colleftGrid ,inner));
	   	}
	   	if ((row == 0 && col == 1) || (row == 0 && col == 3)  || (row == 0 && col == 4) || (row == 0 && col == 6) || (row == 0 && col == 7)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(rowdownGrid ,inner));
	   	}
	   	if ((row == 1 && col == 0) || (row == 3 && col == 0)  || (row == 4 && col == 0) || (row == 6 && col == 0) || (row == 7 && col == 0)) {
	   		super.setBorder(BorderFactory.createCompoundBorder(colrightGrid ,inner));
	   	} 
	  
      if (status == CellStatus.GIVEN) {
         // Inherited from JTextField: Set display properties
         if (color == true) {
        	 super.setText(number + "");
             super.setEditable(false);
             super.setBackground(BG_GIVEN1);
             super.setForeground(FG_GIVEN1);
         } else {
        	 super.setText(number + "");
             super.setEditable(false);
             super.setBackground(BG_GIVEN0);
             super.setForeground(FG_GIVEN0);
         }
      } else if (status == CellStatus.TO_GUESS) {
         // Inherited from JTextField: Set display properties
         if (color == true) {
        	 super.setText("");
        	 super.setEditable(true);
             super.setBackground(BG_TO_GUESS1);
             super.setForeground(FG_NOT_GIVEN1);
         } else {
        	 super.setText("");
        	 super.setEditable(true);
             super.setBackground(BG_TO_GUESS0);
             super.setForeground(FG_NOT_GIVEN0);
         }
      } else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
    	  if (color == true) {
    		  super.setEditable(true);
    	      super.setBackground(BG_CORRECT_GUESS1);
    	  } else {
    		  super.setEditable(true);
    	      super.setBackground(BG_CORRECT_GUESS0);
    	  }
      } else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
    	  if (color == true) {
    	      super.setBackground(BG_WRONG_GUESS1);
    	  } else {
    	      super.setBackground(BG_WRONG_GUESS0);
    	  }
      }
   }
}