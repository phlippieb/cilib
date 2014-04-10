package simgen.function;

public class Spherical extends Function {
	public void setName() {
		this.name = "spherical";
	}

	public void setDomains() {
		this.domainMin = -100;
		this.domainMax =  100;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Spherical\"/>";
	}
}