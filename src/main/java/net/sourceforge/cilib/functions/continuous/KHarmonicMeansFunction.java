package net.sourceforge.cilib.functions.continuous;

import net.sourceforge.cilib.problem.dataset.ClusterableDataSet.Pattern;
import net.sourceforge.cilib.type.types.container.Vector;

public class KHarmonicMeansFunction extends ClusteringFitnessFunction {
	private static final long serialVersionUID = 2680037315045146954L;

	public KHarmonicMeansFunction() {
		super();
	}

	@Override
	public double calculateFitness() {
		double harmonicMean = 0.0;

		for (Pattern pattern : dataset.getPatterns()) {
			double sumOfReciprocals = 0.0;
			for (Vector centroid : arrangedCentroids) {
				sumOfReciprocals += 1.0 / Math.max(calculateDistance(pattern.data, centroid), Double.MIN_VALUE);		// if the distance == 0.0, use a very small value
			}
			harmonicMean += clustersFormed / sumOfReciprocals;
		}
		return harmonicMean;
	}
}
