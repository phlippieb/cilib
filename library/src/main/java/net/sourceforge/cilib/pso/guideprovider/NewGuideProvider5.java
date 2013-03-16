
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
import net.sourceforge.cilib.entity.comparator.DescendingFitnessComparator;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.pso.particle.Particle;
import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.math.random.UniformDistribution;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;

public class NewGuideProvider5 implements GuideProvider {

    protected int tournamentSize;

	public NewGuideProvider5() {
        tournamentSize = 5;
	}

	public NewGuideProvider5(NewGuideProvider5 copy) {
        tournamentSize = copy.getTournamentSizeAsInt();
    }

    @Override
    public NewGuideProvider5 getClone() {
        return new NewGuideProvider5(this);
    }

        public int getTournamentSizeAsInt() {
        return tournamentSize;
    }

    public ControlParameter getTournamentSize() {
        return ConstantControlParameter.of((double)getTournamentSizeAsInt());
    }

    public void setTournamentSizeAsInt(int size) {
        System.out.println("    g4: setTournamentSize("+size+")");
        this.tournamentSize = size;
    }

    public void setTournamentSize(ControlParameter ts) {
        setTournamentSizeAsInt((int)ts.getParameter());
    }

    @Override
    public StructuredType get(Particle particle) {
        PSO pso = (PSO) AbstractAlgorithm.get();
        Topology<Particle> topology = pso.getTopology();
        Collections.shuffle(topology);
        List<Particle> tournament = topology.subList(0, tournamentSize);
        Collections.sort(tournament, new DescendingFitnessComparator<Particle>());

        return tournament.get(0).getPosition();
    }
}
