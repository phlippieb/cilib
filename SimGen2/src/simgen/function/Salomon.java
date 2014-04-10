package simgen.function;

public class Salomon extends Function {
	public void setName() {
		this.name = "salomon";
	}

	public void setDomains() {
		this.domainMin = -100;
		this.domainMax =  100;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Salomon\"/>";
	}
}