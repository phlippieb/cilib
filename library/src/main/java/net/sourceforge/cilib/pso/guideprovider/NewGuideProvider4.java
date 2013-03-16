
/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.pso.guideprovider;

import java.util.List;
import java.util.Collections;
import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.entity.Topologies;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.comparator.DescendingBestFitnessComparator;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.pso.particle.Particle;
import net.sourceforge.cilib.pso.particle.StandardParticle;
import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.math.random.UniformDistribution;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;

public class NewGuideProvider4 implements GuideProvider {

    protected int tournamentSize;

	public NewGuideProvider4() {
        tournamentSize = 5;
	}

	public NewGuideProvider4(NewGuideProvider4 copy) {
        tournamentSize = copy.getTournamentSizeAsInt();
    }

    @Override
    public NewGuideProvider4 getClone() {
        return new NewGuideProvider4(this);
    }

    public int getTournamentSizeAsInt() {
        return tournamentSize;
    }

    public ControlParameter getTournamentSize() {
        return ConstantControlParameter.of((double)getTournamentSizeAsInt());
    }

    public void setTournamentSizeAsInt(int size) {
        this.tournamentSize = size;
    }

    public void setTournamentSize(ControlParameter ts) {
        setTournamentSizeAsInt((int)ts.getParameter());
    }     

    @Override
    public StructuredType get(Particle particle) {
        
        //set up topology:
        PSO pso = (PSO) AbstractAlgorithm.get();
        Topology<Particle> topology = pso.getTopology();
        
        //select a random pool:
        Collections.shuffle(topology);
        List<Particle> tournament = topology.subList(0, tournamentSize);
        
        //select best from pool:
        Collections.sort(tournament, new DescendingBestFitnessComparator<Particle>());
        return tournament.get(0).getBestPosition();
    }
}
