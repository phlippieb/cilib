package simgen.algorithm;

public class CognitiveOnly extends Algorithm {
	public String getName() {
		return "cpso";
	}

	public String getDefinition() {
		return  "\t\t<initialisationStrategy class=\"algorithm.initialisation.ClonedPopulationInitialisationStrategy\">\n" +
                        "\t\t\t<entityNumber value=\"25\"/>\n" +
                        "\t\t\t<entityType class=\"pso.particle.StandardParticle\">\n" +
                        "\t\t\t\t<velocityProvider class=\"pso.velocityprovider.ClampingVelocityProvider\">\n" +
                        "\t\t\t\t\t<delegate class=\"pso.velocityprovider.StandardVelocityProvider\">\n" +
                        "\t\t\t\t\t\t<socialAcceleration class=\"controlparameter.ConstantControlParameter\" parameter=\"0\"/>\n" +
                        "\t\t\t\t\t\t<cognitiveAcceleration class=\"controlparameter.ConstantControlParameter\" parameter=\"1.5\" />\n" +
                        "\t\t\t\t\t\t<inertiaWeight class=\"controlparameter.ConstantControlParameter\" parameter=\"0.99\" />\n" +
                        "\t\t\t\t\t</delegate>\n" +
                        "\t\t\t\t\t<vMax class=\"controlparameter.ConstantControlParameter\" parameter=\"100\"/>\n" +
                        "\t\t\t\t</velocityProvider>\n" +
                        "\t\t\t\t<velocityInitialisationStrategy class=\"entity.initialisation.RandomBoundedInitialisationStrategy\">\n" +
                        "\t\t\t\t\t<lowerBound class=\"controlparameter.ConstantControlParameter\" parameter=\"1.0\"/>\n" +
                        "\t\t\t\t\t<upperBound class=\"controlparameter.ConstantControlParameter\" parameter=\"100.0\"/>\n" +
                        "\t\t\t\t</velocityInitialisationStrategy>\n" +
                        "\t\t\t</entityType>\n" +
                        "\t\t</initialisationStrategy>\n" +
                        "\t\t<iterationStrategy class=\"pso.iterationstrategies.SynchronousIterationStrategy\">\n" +
                        "\t\t\t<boundaryConstraint class=\"problem.boundaryconstraint.RandomBoundaryConstraint\"/>\n" +
                        "\t\t</iterationStrategy>\n" +
                        "\t\t<!-- irrelevant --> <!--topology class=\"entity.topologies.VonNeumannTopology\"/-->\n" +
                        "\t\t<addStoppingCondition class=\"stoppingcondition.MeasuredStoppingCondition\" target=\"" + iterations + "\"/>\n";
	}
}