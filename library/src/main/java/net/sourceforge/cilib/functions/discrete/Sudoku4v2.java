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
import java.util.ArrayList;

/**
 * Interprets input as scale of permutation on rows.
 * Expects 9-dimensional input (one per row) in range 0 to 362 880 inclusive.
 */
public class Sudoku4v2 implements DiscreteFunction {

	int [][] board = new int [9][9];
	boolean [][] fixedPositions = new boolean [9][9];
	boolean solved;
	ArrayList<ArrayList<Integer []>> permutations;

	public Sudoku4v2 () {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fixedPositions[i][j] = false;
			}
		}
		solved = false;
		//
		//System.out.println("Generating list of all permutations...");
		generatePermutations();
		//System.out.println("Finished generating list of all permutations");
		//printPermutations();
	}

	@Override
	public Integer apply (Vector input) {
		int fitness = 0;

		if (input.size() != 9) {
			throw new RuntimeException ("Sudoku input must be in 9 dimensions/genes/whatever");
		}

		//apply the permutations
		for (int row = 0; row < input.size(); row++) {
			int [] openCells = getPermutablesForRow(row);
			//System.out.print ("Open values: ");
			//for (int i = 0; i < openCells.length; i++) System.out.print(openCells[i]);System.out.println();

			int [] permutation = permute(openCells, input.get(row).intValue());
			//System.out.print ("permutation by " + input.get(row) + ": ");
			//for (int i = 0; i < permutation.length; i++) System.out.print(permutation[i]);System.out.println();

			//System.out.println("putting permutation in row...");
			putPermutationInRow (permutation, row);
			//putPermutationInRow ( permute ( getPermutablesForRow (row), input.get(row).intValue()), row);
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
		//System.out.print("getting ");
		for (int i = 0; i < permutablesLength; i++) {
			//System.out.print(permutables[i]);
			result [i] = permutables [i];
		}
		//System.out.println();
		return result;
	}

	void putPermutationInRow(int [] permutation, int row) {
		int i = 0;
		//System.out.print("putting ");
		//for (int x = 0; x < permutation.length; x++) System.out.print(permutation[x]);System.out.println();
		for (int col = 0; col < 9 && i < permutation.length; col++) {
			if (!fixedPositions[row][col]) {
				//System.out.print(permutation[i]);
				board[row][col] = permutation [i++];
			} //else System.out.println ("fixed position at " + col);
		}
		//System.out.println("\n");
	}

	public void setFixedValue (SodukoFixedValue val) {
		this.setFixedValue (val.x, val.y, val.v);

		/*System.out.println("Fixing ("+val.x+","+val.y+") at "+val.v+". Board fix state:");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (fixedPositions[i][j]) System.out.print(board[i][j]);
				else System.out.print(" ");
			}
			System.out.println();
		}*/
	}
	

	int factorial (int x) {
		int result = 1;
		for (int i = x; i > 1; i--) {
			result *= i;
		}
		return result;
	}

	int [] permute (int [] r, int by) {
		/*System.out.print("permuting ");
		for (int i = 0; i < r.length; i++) System.out.print (r[i]);System.out.println();*/

		by = by % factorial(r.length);
		int [] result = new int[r.length];
		for (int i = 0; i < r.length; i++) {
			if (by < 0) result[i] = -1;
			else result[i] = r[permutations.get(r.length).get(by)[i]];
		}

		//System.out.print("permutation: ");
		//for (int i = 0; i < result.length; i++) System.out.print(result[i]); System.out.println();
		return result;
	}

	void generatePermutations () {
		permutations = new ArrayList<ArrayList<Integer[]>>();
		for (int i = 0; i <= 9; i++) {
			permutations.add (generatePermutationsForLength(i));
		}
	}

	ArrayList<Integer[]> generatePermutationsForLength (int N) {
		ArrayList<Integer []> result = new ArrayList<Integer []>();
		// initialize permutation
        int[] a  = new int[N];
        for (int i = 0; i < N; i++)
            a[i] = i;

        // print permutations
        result.add(Integerify(a));
        while (hasNext(a))
           result.add(Integerify(a));
       	return result;
    }
    static Integer [] Integerify(int [] a) {
    	Integer [] result = new Integer[a.length];
    	for (int i = 0; i < a.length; i++) {
    		result[i] = new Integer(a[i]);
    	}
    	return result;
    }
    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static boolean hasNext(int[] a) {
        int N = a.length;

        // find rightmost element a[k] that is smaller than element to its right
        int k;
        for (k = N-2; k >= 0; k--)
            if (a[k] < a[k+1]) break;
        if (k == -1) return false;

        // find rightmost element a[j] that is larger than a[k]
        int j = N-1;
        if (!(0 <= j && j < a.length)) return false;
        while (a[k] > a[j])
            j--;
        swap(a, j, k);

        for (int r = N-1, s = k+1; r > s; r--, s++)
            swap(a, r, s);

        return true;
    }

	void printPermutations () {
		int length = 0;
		for (ArrayList<Integer[]> len : permutations) {
			System.out.println("Permutations of length " + length + " (" +len.size()+ " total):\n");
			for (Integer [] per : len) {
				for (int i = 0; i < per.length; i++) {
					System.out.print (per[i]);
				}
				System.out.println();
			}
			length++;
		}
	}	

}