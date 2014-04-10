package simgen.function;

public class SchwefelProblem2_22 extends Function {
	public void setName() {
		this.name = "schwefel2_22";
	}

	public void setDomains() {
		this.domainMin = -10;
		this.domainMax =  10;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.unconstrained.SchwefelProblem2_22\"/>";
	}
}