package simgen.function;

public class Elliptic extends Function {
	public void setName() {
		this.name = "elliptic";
	}

	public void setDomains() {
		this.domainMin = -100;
		this.domainMax =  100;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Elliptic\" />";
	}
}