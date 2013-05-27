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

public class Soduko implements DiscreteFunction {

	int [][] board = new int [9][9];
	boolean [][] fixedPositions = new boolean [9][9];

	public Soduko () {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fixedPositions[i][j] = false;
			}
		}
	}

	@Override
	public Integer apply (Vector input) {

		//test input validity
		if (input.size() != 81) {
			throw new RuntimeException ("Invalid soduko size!");
		}

		//map input to 9x9 soduko board
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!fixedPositions[i][i]) {
					board[i][j] = ( input.get((9*i)+j) ).intValue();
				}
			}
		}

		int fitness = 0;

/*
		//rows and cols fitness
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = j+1; k < 9; k++) {
					//rows
					if (board[i][j].equals(board[j][k])) {
						fitness++;
					}
					//cols
					if (board[j][i].equals(board[k][i])) {
						fitness++;
					}
				}
			}
		}

		//blocks fitness
		for (int i = 0; i < 9; i+=3) {
			for (int j = 0; j < 9; j+=3) {
				for (int q = 0; q < 3; q++) {
					for (int r = 0; r < 3; r++) {
						Object o = board[i+q][j+r];
						for (int a = q+1; a < 3; a++) {
							for (int b = r+1; b < 3; b++) {
								if (o.equals(board[i+a][j+b])) {
									fitness++;
								}
							}
						}
					}
				}
			}
		}
*/

		//alternative row and col fitness
		boolean numberIsPresentInRow = false;
		boolean numberIsPresentInCol = false;
		for (int i = 0; i < 9; i++) {
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
				}
				if (!numberIsPresentInCol) {
					fitness++;
				}
			}
		}

		//alternative block fitness
		boolean numberIsPresentInBlock = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int requiredNumber = 1; requiredNumber < 10; requiredNumber++) {
					numberIsPresentInBlock = false;
					for (int x = 0; x < 3; x++) {
						for (int y = 0; y < 3; y++) {
							if (board[(i*3)+x][(j*3)+y] == requiredNumber) {
								numberIsPresentInBlock = true;
							}
						}
					}
					if (!numberIsPresentInBlock) {
						fitness++;
					}
				}
			}
		}

		System.out.println(fitness);
		return fitness;

	}

	public void fix(int r, int c, int v) {
		if (-1 < r && r < 9 && -1 < c && c < 9 && 0 < v && v < 10) {
			fixedPositions[r][c] = true;
			board[r][c] = v;
		}
		else {
			throw new RuntimeException ("Invalid fixed value in soduko! Must be r,c E[0,8]; v E[1,9]");
		}
	}
}