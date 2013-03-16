
/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.pso.guideprovider;

import net.sourceforge.cilib.pso.particle.Particle;
import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.math.random.UniformDistribution;
import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.entity.Topologies;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.comparator.SocialBestFitnessComparator;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;

/**
 *	Part two of a set of new global guide providers for PSO.
 *	This part produces a new global guide as a discreet cross-over
 *	on the new particle position and the personal best position:
 * 		yHat i j (t+1) =	{ y i j (t+1)	if U(0,1) < pr
 *							{ x i j (t+1)	otherwise
 *	where pr is the probability of a crossover.
 */
public class NewGuideProvider3 implements GuideProvider {

	protected UniformDistribution distribution;
	protected double probability;
    protected NBestGuideProvider delegate = new NBestGuideProvider();

	public NewGuideProvider3() {
		distribution = new UniformDistribution();
		probability = 0.5;
	}

	public NewGuideProvider3(NewGuideProvider3 clone) {
		this.distribution = clone.getDistribution();
		this.probability = clone.getProbability();
	}

    @Override
    public NewGuideProvider3 getClone() {
    	return new NewGuideProvider3(this);
    }

    public UniformDistribution getDistribution() {
    	return this.distribution;
    }

    public double getProbability() {
    	return this.probability;
    }

    public ControlParameter getCrossoverProbability () {
        return ConstantControlParameter.of(probability);
    }

    public void setUniformDistribution(UniformDistribution u) {
    	this.distribution = u;
    }

    public void setProbability(double p) {
    	this.probability = p;
    }

    public void setCrossoverProbability (ConstantControlParameter crossoverProbability) {
        this.setProbability(crossoverProbability.getParameter());
    }

    /**
     * Selects a guide for {@code particle}.
     * @param particle The particle who's guide will be selected.
     * @return The selected guide.
     */
    public StructuredType get(Particle particle) {
    	Vector x = (Vector) particle.getPosition();
        Vector yHat = (Vector) particle.getNeighbourhoodBest().getPosition();

    	Vector.Builder builder = Vector.newBuilder();
    	for (int i = 0; i < x.size(); i++) {
    		double r = distribution.getRandomNumber();
    		double value = r < probability
                ? yHat.doubleValueOf(i)
                : x.doubleValueOf(i);
    		builder.add(value);
    	}
    	return builder.build();
    }
}
