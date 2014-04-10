package simgen.algorithm;

public class VonNeumann extends Algorithm {
	public String getName() {
		return "vn";
	}

	public String getDefinition() {
		return  "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                        "\t\t\t<entityNumber value=\"25\"/>\n" +
                        "\t\t\t<entityType class=\"pso.particle.StandardParticle\"/>\n" +
                        "\t\t</initialisationStrategy>\n" +
                        "\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                        "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\" />\n" +
                        "\t\t\t</iterationStrategy>\n" +
                        "\t\t<topology class=\"entity.topologies.VonNeumannTopology\"/>\n" +
                        "\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"2000\"/>\n";
	}
}