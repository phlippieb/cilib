/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.functions.discrete;

public class SodukoFixedValue {
	int x, y, v;
	
	public SodukoFixedValue() {
		x = y = v = -1;
	}

	public void setRow (double r) {
		this.x = (int)r;
	}

	public void setCol (double c) {
		this.y = (int)c;
	}

	public void setVal (double v) {
		this.v = (int)v;
	}
}
