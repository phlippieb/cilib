package simgen.function;

public class Quadric extends Function {
	public void setName() {
		this.name = "quadric";
	}

	public void setDomains() {
		this.domainMin = -100;
		this.domainMax =  100;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Quadric\" />";
	}
}