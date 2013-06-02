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

/**
 * Interprets input as scale of permutation on rows.
 * Expects 9-dimensional input (one per row) in range 0 to 362 880 inclusive.
 */
public class Sudoku4 implements DiscreteFunction {

	int [][] board = new int [9][9];
	boolean [][] fixedPositions = new boolean [9][9];
	boolean solved;

	public Sudoku4 () {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fixedPositions[i][j] = false;
			}
		}
		solved = false;
	}

	@Override
	public Integer apply (Vector input) {
		int fitness = 0;

		if (input.size() != 9) {
			throw new RuntimeException ("Sudoku input must be in 9 dimensions/genes/whatever");
		}

		//apply the permutations
		for (int row = 0; row < 9; row++) {
			putPermutationInRow ( permute ( getPermutablesForRow (row), input.get(row).intValue()), row);
		}

		//calculate fitness
		boolean numberIsPresentInCol = false;
		boolean colIsCorrect = false;
		for (int i = 0; i < 9; i++) {
			colIsCorrect = true;
			for (int requiredNumber = 1; requiredNumber < 10; requiredNumber++) {
				numberIsPresentInCol = false;
				for (int j = 0; j < 9; j++) {
					if (board[j][i] == requiredNumber) {
						numberIsPresentInCol = true;
					}
				}
				if (!numberIsPresentInCol) {
					fitness++;
					colIsCorrect = false;
				}
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

		if (fitness == 0 && !solved) {
			//printBoard();
			solved = true;
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

	int [] getPermutablesForRow(int row) {
		int [] permutables = new int [9];
		int permutablesLength = 0;
		boolean numIsFixed;
		for (int num = 1; num < 10; num++) {
			numIsFixed = false;
			for (int col = 0; col < 9; col++) {
				if (fixedPositions[row][col]
					&& board[row][col] == num) {
					numIsFixed = true;
				}
			}
			if (!numIsFixed) {
				permutables[permutablesLength++] = num;
			}
		}
		int [] result = new int [permutablesLength];
		for (int i = 0; i < permutablesLength; i++) {
			result [i] = permutables [i];
		}
		return result;
	}

	void putPermutationInRow(int [] permutation, int row) {
		int i = 0;
		for (int col = 0; col < 9 && i < permutation.length; col++) {
			if (!fixedPositions[row][col]) {
				board[row][col] = permutation [i++];
			}
		}
	}

	public void setFixedValue (SodukoFixedValue val) {
		this.setFixedValue (val.x, val.y, val.v);
	}	

	//permute the input.
	//the results of two calls to this function will differ if the scale differs
	int [] permute (int [] input, int by) {

		by = by % factorial (input.length);

		Comparable [] result = new Integer[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Integer(input[i]);
		}
		for (int i = 0; i < by; i++) {
			result = nextPermutation (result);
			if (result == null) {
				result = new Integer[input.length];
				for (int j = 0; j < result.length; j++) {
					result[j] = new Integer(input[j]);
				}		
			}
		}

		int [] intResults = new int [result.length];
		for (int i = 0; i < result.length; i++) {
			intResults [i] = ((Integer)result[i]);
		}
		return intResults;

	}

	//example application for reference
	// simply prints all permutation - to see how it works
	/*private static void printPermutations( Comparable[] c ) {
		System.out.println( Arrays.toString( c ) );
		while ( ( c = nextPermutation( c ) ) != null ) {
			System.out.println( Arrays.toString( c ) );
		}
	}*/

	// modifies c to next permutation or returns null if such permutation does not exist
	private static Comparable[] nextPermutation( final Comparable[] c ) {
		// 1. finds the largest k, that c[k] < c[k+1]
		int first = getFirst( c );
		if ( first == -1 ) return null; // no greater permutation
		// 2. find last index toSwap, that c[k] < c[toSwap]
		int toSwap = c.length - 1;
		while ( c[ first ].compareTo( c[ toSwap ] ) >= 0 )
			--toSwap;
		// 3. swap elements with indexes first and last
		swap( c, first++, toSwap );
		// 4. reverse sequence from k+1 to n (inclusive) 
		toSwap = c.length - 1;
		while ( first < toSwap )
			swap( c, first++, toSwap-- );
		return c;
	}

	// finds the largest k, that c[k] < c[k+1]
	// if no such k exists (there is not greater permutation), return -1
	private static int getFirst( final Comparable[] c ) {
		for ( int i = c.length - 2; i >= 0; --i )
			if ( c[ i ].compareTo( c[ i + 1 ] ) < 0 )
				return i;
		return -1;
	}

	// swaps two elements (with indexes i and j) in array 
	private static void swap( final Comparable[] c, final int i, final int j ) {
		final Comparable tmp = c[ i ];
		c[ i ] = c[ j ];
		c[ j ] = tmp;
	}


	void printBoard () {
		System.out.println();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	int factorial (int x) {
		int result = 1;
		for (int i = x; i > 1; i--) {
			result *= i;
		}
		return result;
	}

}