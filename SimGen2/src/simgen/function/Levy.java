package simgen.function;

public class Levy extends Function {
	public void setName() {
		this.name = "levy";
	}

	public void setDomains() {
		this.domainMin = -10;
		this.domainMax =  10;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Levy\" />";
	}
}