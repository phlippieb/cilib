package simgen.function;

public class SchwefelProblem1_2 extends Function {
	public void setName() {
		this.name = "schwefel1_2";
	}

	public void setDomains() {
		this.domainMin = -100;
		this.domainMax =  100;
	}

	public void setDefinition() {
        this.definition = "<function class=\"functions.continuous.SchwefelProblem1_2\"/>";
	}
}