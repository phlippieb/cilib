package simgen.function;

public class Rosenbrock extends Function {
	public void setName() {
		this.name = "rosenbrock";
	}

	public void setDomains() {
		this.domainMin = -2.048;
		this.domainMax =  2.048;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Rosenbrock\"/>";
	}
}