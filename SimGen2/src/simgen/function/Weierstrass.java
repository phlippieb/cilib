package simgen.function;

public class Weierstrass extends Function {
	public void setName() {
		this.name = "weierstrass";
	}

	public void setDomains() {
		this.domainMin = -0.5;
		this.domainMax =  0.5;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Weierstrass\"/>";
	}
}