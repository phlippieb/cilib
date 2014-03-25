package simgen.algorithm;

public class Gbest extends Algorithm {
	public String getName() {
		return "gbest";
	}

	public String getDefinition() {
		return "<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                "\t<entityNumber value=\"25\"/>\n" +
                "\t<entityType class=\"pso.particle.StandardParticle\"/>\n" +
            	"</initialisationStrategy>\n" +
            	"<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                "\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\" />\n" +
            	"</iterationStrategy>\n" +
            	"<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"2000\"/>\n";
	}
}