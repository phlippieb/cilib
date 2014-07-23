package simgen.function;

/**
 * !! Note: restricted to 2 dimensions!
 */

public class SixHumpCamelBack extends Function {
	public void setName() {
		this.name = "sixhump";
	}

	public void setDomains() {
		this.domainMin = -5;
		this.domainMax =  5;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.SixHumpCamelBack\"/>";
	}
}