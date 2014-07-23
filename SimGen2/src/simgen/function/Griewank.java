package simgen.function;

public class Griewank extends Function {
	public void setName() {
		this.name = "griewank";
	}

	public void setDomains() {
		this.domainMin = -600;
		this.domainMax =  600;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.Griewank\" />";
	}
}