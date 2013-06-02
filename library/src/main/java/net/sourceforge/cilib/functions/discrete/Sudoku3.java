/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.functions.discrete;

import net.sourceforge.cilib.functions.DiscreteFunction;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.type.types.Int;

public class Sudoku3 implements DiscreteFunction {

	int [][] board = new int [9][9];
	boolean [][] fixedPositions = new boolean [9][9];

	public Sudoku3 () {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fixedPositions[i][j] = false;
			}
		}
	}

	@Override
	public Integer apply (Vector input) {
		int fitness = 0;

		//test input validity
		if (input.size() != 81) {
			throw new RuntimeException ("Invalid soduko size! Must be ^81");
		}

		//map input to 9x9 soduko board
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!fixedPositions[i][j]) {
					board[i][j] = ( input.get((9*i)+j) ).intValue();
				}
			}
		}

		//penalize values outside range
		int v = 1;
		for (int i=0; i<81; i++) {
			v = input.get(i).intValue();
			if (v < 1) {
				fitness += Math.pow(Math.abs(1-v) +10, 2);
			} else if (9 < v) {
				fitness += Math.pow(Math.abs(v-9) +10, 2);
			}
		}


		boolean numberIsPresentInRow = false;
		boolean numberIsPresentInCol = false;
		boolean rowIsCorrect = false;
		boolean colIsCorrect = false;
		for (int i = 0; i < 9; i++) {
			rowIsCorrect = true;
			colIsCorrect = true;
			for (int requiredNumber = 1; requiredNumber < 10; requiredNumber++) {
				numberIsPresentInRow = false;
				numberIsPresentInCol = false;
				for (int j = 0; j < 9; j++) {
					if (board[i][j] == requiredNumber) {
						numberIsPresentInRow = true;
					}
					if (board[j][i] == requiredNumber) {
						numberIsPresentInCol = true;
					}
				}
				if (!numberIsPresentInRow) {
					fitness++;
					rowIsCorrect = false;
				}
				if (!numberIsPresentInCol) {
					fitness++;
					colIsCorrect = false;
				}
			}
			if (!rowIsCorrect) {
				fitness+=9;
			}
			if (!colIsCorrect) {
				fitness+=9;
			}
		}


		boolean numberIsPresentInBlock = false;
		boolean blockIsCorrect = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				blockIsCorrect = true;
				for (int requiredNumber = 1; requiredNumber < 10; requiredNumber++) {
					numberIsPresentInBlock = false;
					for (int x = 0; x < 3; x++) {
						for (int y = 0; y < 3; y++) {
							if (board[(i*3)+x][(j*3)+y] == requiredNumber) {
								numberIsPresentInBlock = true;
								break;
							}
						}
					}
					if (!numberIsPresentInBlock) {
						fitness++;
						blockIsCorrect = false;
					}
				}
				if (!blockIsCorrect) {
					fitness+=9;
				}
			}
		}

		return fitness;

	}

	public void setFixedValue(int row, int col, int val) {
		if (-1 < row && row < 9 && -1 < col && col < 9 && 0 < val && val < 10) {
			fixedPositions[row][col] = true;
			board[row][col] = val;
		}
		else {
			throw new RuntimeException ("Invalid fixed value in soduko! Must be row,col E[0,8]; val E[1,9]");
		}
	}

	public void setFixedValue (SodukoFixedValue val) {
		this.setFixedValue (val.x, val.y, val.v);
	}
}



