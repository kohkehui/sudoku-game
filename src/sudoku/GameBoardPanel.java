package sudoku;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import sudoku.SoundEffects;


public class GameBoardPanel extends JPanel {
private static final long serialVersionUID = 1L;  // to prevent serial warning


// Define named constants for UI sizes
public static final int CELL_SIZE = 60;   // Cell width/height in pixels
public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
											// Board width/height in pixels

// Define properties
/** The game board composes of 9x9 Cells (customized JTextFields) */
private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
/** It also contains a Puzzle with array numbers and isGiven */
private Puzzle puzzle = new Puzzle();
private int[][] board = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE]; // This array will hold the state of the Sudoku board

/** Constructor */
	public GameBoardPanel() {
		super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

		// Allocate the 2D array of Cell, and added into JPanel.
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col] = new Cell(row, col);
				cells[row][col].addActionListener(e -> {
				Cell source = (Cell)e.getSource();
				board[source.row][source.col] = Integer.parseInt(source.getText());
				checkAllConflicts();
				});
				add(cells[row][col]);
			}
		}

	// [TODO 3] Allocate a common listener as the ActionEvent listener for all the
	//  Cells (JTextFields)
	CellInputListener listener = new CellInputListener();

	// [TODO 4] Adds this common listener to all editable cells
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
		for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
			if (cells[row][col].isEditable()) {
				cells[row][col].addActionListener(listener);   // For all editable rows and cols
			}
		}
		}

		super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
	}

	// Method to check conflicts for all cells
    private void checkAllConflicts() {
    for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            boolean conflict = cells[row][col].checkForConflicts(board);
            if (conflict) {
                cells[row][col].highlightConflict();
            }
        }
    }
}

	public void lightcolor() {
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].color = false;
				cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
				
			}
	}
}

public void darkcolor() {
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].color = true;
				cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
			}
	}
}

/**
	* Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
	* You can call this method to start a new game.
	*/
public void newGame() {
		// Get a new puzzle
		puzzle.newPuzzle(60);

		// Based on the puzzle, initialize all the cells.
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
			}
		}
	}

public void Reset() {
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
				for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
					cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
							
						}
					}
				}

// Difficulty levels
public void Advanced() {
	//Get a new Puzzle
	puzzle.newPuzzle(45);
		
	// Based on the puzzle, initialize all the cells.
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
		for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
			}
		} 
}

public void Expert() {
	//Get a new Puzzle
	puzzle.newPuzzle(35);
	
	// Based on the puzzle, initialize all the cells.
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
		for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
			cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
			}
		} 
}

public void Hell() {
	//Get a new Puzzle
	puzzle.newPuzzle(25);

	// Based on the puzzle, initialize all the cells.
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
		for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
			cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
			}
		} 
}

//Hints
public void RevealAll() {
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				if(puzzle.isGiven[row][col] == false) {
					cells[row][col].setText(puzzle.numbers[row][col] + "");
					cells[row][col].setEditable(false);
					cells[row][col].setBackground(new Color(207, 207, 196));
					cells[row][col].setForeground(Color.BLACK);
					
				}
				
			}
		}
	}

public void RevealOne() {
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				if(puzzle.isGiven[row][col] == false) {
					puzzle.isGiven[row][col] = true;
					cells[row][col].setText(puzzle.numbers[row][col] + "");
					cells[row][col].setEditable(false);
					cells[row][col].setBackground(new Color(207, 207, 196));
					cells[row][col].setForeground(Color.BLACK);
					
					return;
				}	 
				
			}
		}
}

/**
	* Return true if the puzzle is solved
	* i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
	*/
public boolean isSolved() {
	for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
		for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
			if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
			return false;
			}
		}
	}
	return true;
}

// [TODO 2] Define a Listener Inner Class for all the editable Cells
private class CellInputListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		Cell sourceCell = (Cell)e.getSource();
		// Retrieve the int entered
		int numberIn = Integer.parseInt(sourceCell.getText());
		// For debugging
		System.out.println("You entered " + numberIn);

		SoundEffects soundEffects = new SoundEffects();
	/*
          * [TODO 5] (later - after TODO 3 and 4)
          * Check the numberIn against sourceCell.number.
          * Update the cell status sourceCell.status,
          * and re-paint the cell via sourceCell.paint().
          */
		if (numberIn == sourceCell.number) {
			sourceCell.status = CellStatus.CORRECT_GUESS;
			sourceCell.setEditable(false);
			soundEffects.playSound("correct.wav");  // Assuming you have a 'correct.wav' in your project folder
		} else {
			sourceCell.status = CellStatus.WRONG_GUESS;
			soundEffects.playSound("wrong.wav");  // Assuming you have a 'wrong.wav' in your project folder
		}
		sourceCell.paint();  // re-paint this cell based on its status
	
		if(isSolved()) {
			soundEffects.playSound("won.wav");  // Play a congratulatory sound effect
			// Your existing code for displaying congratulations
		}
		
		
		/*
		* [TODO 6] (later)
		* Check if the player has solved the puzzle after this move,
		*   by calling isSolved(). Put up a congratulation JOptionPane, if so.
		*/
		if(isSolved()) {
			ImageIcon congratsIcon = new ImageIcon("giphy.gif");
			JLabel label = new JLabel();
			label.setText("Congratulations!");
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.TOP);
			label.setFont(new Font("Actor", Font.BOLD, 35));
		
			label.setIcon(congratsIcon);
			label.setIconTextGap(25);
			label.setBackground(Color.yellow);
			label.setVerticalAlignment(JLabel.CENTER);
			label.setHorizontalAlignment(JLabel.CENTER);
		
			JOptionPane.showMessageDialog(null, label, "WIN!", JOptionPane.INFORMATION_MESSAGE);
			//SoundEffects.WON.play();
			String filepath = "won.wav";
			//SoundEffects congratsSoundEffects = new SoundEffects();
			//congratsSoundEffects.playSound(filepath);
		}
	}
}
}