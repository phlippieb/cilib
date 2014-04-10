package simgen.function;

public class Zakharov extends Function {
	public void setName() {
		this.name = "zakharov";
	}

	public void setDomains() {
		this.domainMin = -5;
		this.domainMax =  10;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Zakharov\"/>";
	}
}