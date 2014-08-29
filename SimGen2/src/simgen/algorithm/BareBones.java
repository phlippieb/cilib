package simgen.algorithm;

public class BareBones extends Algorithm {
	public String getName() {
		return "bb";
	}

	public String getDefinition() {
		return  "\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                        "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\"/>\n" +
                        "\t\t</iterationStrategy>\n" +
                        "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                        "\t\t\t<entityNumber value=\"25\"/>\n" +
                        "\t\t\t<entityType class=\"pso.particle.StandardParticle\">\n" +
                        "\t\t\t\t<positionProvider class=\"pso.positionprovider.LinearPositionProvider\"/>\n" +
                        "\t\t\t\t<velocityProvider class=\"pso.velocityprovider.BareBonesVelocityProvider\">\n" +
                        "\t\t\t\t\t<exploitProbability class=\"controlparameter.ConstantControlParameter\" parameter=\"0\"/>\n" +
                        "\t\t\t\t</velocityProvider>\n" +
                        "\t\t\t</entityType>\n" +
                        "\t\t</initialisationStrategy>\n" +
                        "\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"" + iterations + "\"/>\n";
	}
}
