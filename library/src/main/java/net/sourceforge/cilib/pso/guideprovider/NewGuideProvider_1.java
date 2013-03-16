
/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.pso.guideprovider;

import net.sourceforge.cilib.pso.particle.Particle;
import net.sourceforge.cilib.pso.particle.ParticleBehavior;
import net.sourceforge.cilib.pso.velocityprovider.VelocityProvider;
import net.sourceforge.cilib.pso.velocityprovider.StandardVelocityProvider;
import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 *	Part one of a set of new Global Guide Providers.
 *  This part calculates the new global guide as:
 *		yHat = (c1*y + c2*x)c1 + c2
 */
public class NewGuideProvider_1 implements GuideProvider {

	public NewGuideProvider_1() {
		//nothing to init
	}

	public NewGuideProvider_1(NewGuideProvider_1 copy) {
		//nothing to copy
	}

    @Override
    public NewGuideProvider_1 getClone() {
    	return new NewGuideProvider_1(this);
    }

    /**
     * Selects a guide for {@code particle}.
     * @param particle The particle who's guide will be selected.
     * @return The selected guide.
     */
    @Override
    public StructuredType get(Particle particle) {
    	ParticleBehavior particleBehavior = particle.getParticleBehavior();
    	StandardVelocityProvider velocityProvider = (StandardVelocityProvider)particleBehavior.getVelocityProvider();

    	double c1 = velocityProvider.getCognitiveAcceleration().getParameter();
    	double c2 = velocityProvider.getSocialAcceleration().getParameter();

    	Vector x = (Vector) particle.getPosition();
    	Vector y = (Vector) particle.getBestPosition();
    	Vector yHat = y.multiply(c1).plus(x.multiply(c2)).divide(c1+c2);
    	//yHat = yHat.subtract(Vector.fill(c2, yHat.size())); //not sure why this is here

    	return yHat;
    }
}
