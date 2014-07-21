package simgen.function;

public class Quartic extends Function {
	public void setName() {
		this.name = "quartic";
	}

	public void setDomains() {
		this.domainMin = -1.28;
		this.domainMax =  1.28;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Quartic\" />";
	}
}