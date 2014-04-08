package simgen.algorithm;

public class Lbest extends Algorithm {
	public String getName() {
		return "lbest";
	}

	public String getDefinition() {
		return "initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">" +
                "<entityNumber value=\"25\"/>\n" +
                "<entityType class=\"pso.particle.StandardParticle\"/>\n" +
                "</initialisationStrategy>\n" +
                "<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                "<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\" />\n" +
                "</iterationStrategy>\n" +
                "<topology class=\"entity.topologies.LBestTopology\"/>\n" +
                "<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"2000\"/>\n";
	}
}