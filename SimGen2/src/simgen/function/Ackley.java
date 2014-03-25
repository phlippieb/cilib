package simgen.function;

public class Ackley extends Function {
	public void setName() {
		this.name = "ackley";
	}

	public void setDomains() {
		this.domainMin = -32;
		this.domainMax =  32;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Ackley\"/>";
	}
}