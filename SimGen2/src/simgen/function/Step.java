package simgen.function;

public class Step extends Function {
	public void setName() {
		this.name = "step";
	}

	public void setDomains() {
		this.domainMin = -25;
		this.domainMax =  25;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Step\"/>";
	}
}