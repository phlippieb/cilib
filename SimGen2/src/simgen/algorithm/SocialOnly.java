package simgen.algorithm;

public class SocialOnly extends Algorithm {
	public String getName() {
		return "spso";
	}

	public String getDefinition() {
		return  "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                        "\t\t\t<entityNumber value=\"25\"/>\n" +
                        "\t\t\t<entityType class=\"pso.particle.StandardParticle\">\n" +
                        "\t\t\t\t<velocityProvider class=\"pso.velocityprovider.StandardVelocityProvider\">\n" +
                        "\t\t\t\t\t<cognitiveAcceleration class=\"controlparameter.ConstantControlParameter\" parameter=\"0\"/>\n" +
                        "\t\t\t\t</velocityProvider>\n" +
                        "\t\t\t</entityType>\n" +
                        "\t\t</initialisationStrategy>\n" +
                        "\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                        "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\"/>\n" +
                        "\t\t</iterationStrategy>\n" +
                        "\t\t<topology class=\"entity.topologies.GBestTopology\"/>\n" +
                        "\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"2000\"/>\n";
	}
}