package simgen.function;

public class Michalewicz extends Function {
	public void setName() {
		this.name = "michalewicz";
	}

	public void setDomains() {
		this.domainMin = -0;
		this.domainMax =  3.141592653589793;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Michalewicz\" />";
	}
}