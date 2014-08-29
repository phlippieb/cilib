/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.simulator;

import com.google.common.base.Strings;
import net.sourceforge.cilib.algorithm.ProgressEvent;
import net.sourceforge.cilib.algorithm.ProgressListener;

/**
 * Implements a text progress meter.
 */
final class ProgressText implements ProgressListener {

    private boolean printedDone;
    private final int simulations;

    /**
     * Creates new form ProgressFrame.
     *
     * @param simulations The number of simulations in total.
     * */
    ProgressText(int simulations) {
        this.simulations = simulations;
        printedDone = false;
    }

    @Override
    public void handleProgressEvent(ProgressEvent event) {
        if (printedDone) {
            return;
        }

        double percentage = (int) (1000 * event.getPercentage()) / 10.0;
        int nequals = (int) (50 * event.getPercentage());
        String spaces = Strings.repeat(" ", percentage < 10 ? 2 : percentage < 100 ? 1 : 0);
        StringBuilder sb = new StringBuilder(String.format("\rProgress (%3.1f) %s|", percentage, spaces));
        sb.append(Strings.repeat("=", nequals));
        sb.append(Strings.repeat(" ", 50 - nequals));
        sb.append("|");

        if (nequals == 50) {
            printedDone = true;
            // what would happen if we remove \n below?
            // hopefully, ``starting simulation'' would appear on same line as last progress bar.
            // if we first clear the line, this could create nicer output
            // clear can be done more or less like this:
            // for (int i=0; i<1000  /* or some safely high number */; i++) {
            //      System.out.print("\b \b");
            // } // this backspaces each character from the back of the line until it reaches the front, at which point it can't go back.
            // which would also leave the caret at the start of the line already. Nifty!
            // Then we could print done, without the \n, so that each complete simulation has that. For inner peace.
        
            for (int i=0; i<80; i++) {
                sb.append("\b \b");
            }
            sb.append("Done. ");
        }

        System.out.print(sb.toString());
    }

    public void setSimulation(int simulation) {
        System.out.println("Starting simulation " + (simulation + 1) + " of " + simulations + ".");
        printedDone = false;
    }
}
