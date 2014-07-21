package simgen.function;

public class EggHolder extends Function {
	public void setName() {
		this.name = "eggholder";
	}

	public void setDomains() {
		this.domainMin = -512;
		this.domainMax =  512;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.EggHolder\" />";
	}
}