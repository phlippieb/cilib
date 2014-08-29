package simgen.algorithm;

public class Gbest extends Algorithm {
	public String getName() {
		return "gbest";
	}

	public String getDefinition() {
		return  "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                "\t\t\t<entityNumber value=\"25\"/>\n" +
                "\t\t\t<entityType class=\"pso.particle.StandardParticle\"/>\n" +
            	"\t\t</initialisationStrategy>\n" +
            	"\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\" />\n" +
            	"\t\t</iterationStrategy>\n" +
            	"\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"" + iterations + "\"/>\n";
	}
}