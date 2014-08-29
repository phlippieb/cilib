package simgen.algorithm;

public class GbestGC extends Algorithm {
	public String getName() {
		return "gbestgc";
	}

	public String getDefinition() {
		return  "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                        "\t\t\t<entityNumber value=\"25\"/>\n" +
                        "\t\t\t<entityType class=\"pso.particle.StandardParticle\">\n" +
                        "\t\t\t<velocityProvider class=\"pso.velocityprovider.GCVelocityProvider\"/>\n" +
                        "\t\t\t</entityType>\n" +
                        "\t\t</initialisationStrategy>\n" +
                        "\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                        "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\" />\n" +
                        "\t\t</iterationStrategy>\n" +
                        "\t\t<topology class=\"entity.topologies.GBestTopology\"/>\n" +
                        "\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"" + iterations + "\"/>\n";
	}
}