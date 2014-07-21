package simgen.function;

public class Rastrigin extends Function {
	public void setName() {
		this.name = "rastrigin";
	}

	public void setDomains() {
		this.domainMin = -5.12;
		this.domainMax =  5.12;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Rastrigin\"/>";
	}
}