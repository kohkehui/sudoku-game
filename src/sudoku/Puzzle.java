
package sudoku;
/**
 * The Sudoku number hardcodedNumbers to be solved
 */
public class Puzzle {
   // All variables have package access
   // The numbers on the hardcodedNumbers
   int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   // The clues - isGiven (no need to guess) or need to guess
   boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

   // Constructor
   public Puzzle() {
      super();
   }

   // Generate a new hardcodedNumbers given the number of cells to be guessed, which can be used
   //  to control the difficulty level.
   // This method shall set (or update) the arrays numbers and isGiven
   public void newPuzzle(int cellsToGuess) {
      // I hardcode a hardcodedNumbers here for illustration and testing.
      int[][] hardcodedNumbers =
         {{5, 3, 4, 6, 7, 8, 9, 1, 2},
          {6, 7, 2, 1, 9, 5, 3, 4, 8},
          {1, 9, 8, 3, 4, 2, 5, 6, 7},
          {8, 5, 9, 7, 6, 1, 4, 2, 3},
          {4, 2, 6, 8, 5, 3, 7, 9, 1},
          {7, 1, 3, 9, 2, 4, 8, 5, 6},
          {9, 6, 1, 5, 3, 7, 2, 8, 4},
          {2, 8, 7, 4, 1, 9, 6, 3, 5},
          {3, 4, 5, 2, 8, 6, 1, 7, 9}};
      
    //randomise hardcodedNumbers
      int random = (int) (Math.random() * 9);
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
          for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        	  int num = hardcodedNumbers[row][col];
        	  if (num+random == 9) {
        		  hardcodedNumbers[row][col] = 9;
        	  }else {
        		  hardcodedNumbers[row][col] = (num+random)%9;
        	  }
          }
      }

      // Copy from hardcodedNumbers into the array "numbers"
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            numbers[row][col] = hardcodedNumbers[row][col];
         }
      }

      // Need to use input parameter cellsToGuess!
      // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
      boolean[][] hardcodedIsGiven =
    	  {{false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false},
    	   {false, false, false, false, false, false, false, false, false}};

      //randomise position of hardcodedIsGiven
      int i=0;
      while (i< cellsToGuess) {
    	  int r, c;
    	  r = (int)(Math.random() * 9);
		  c = (int)(Math.random() * 9);
		  if (!hardcodedIsGiven[r][c]) {
			  hardcodedIsGiven[r][c]=true;
			  ++i;
		  }
      }

      // Copy from hardcodedIsGiven into array "isGiven"
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            isGiven[row][col] = hardcodedIsGiven[row][col];
         }
      }
   }

   //(For advanced students) use singleton design pattern for this class
}
