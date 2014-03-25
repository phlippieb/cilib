package simgen.algorithm;

public abstract class Algorithm implements Cloneable {

	int population;

	public Algorithm() {
	}

	@Override
	public Algorithm clone() {
		try {
			Algorithm a = (Algorithm)getClass().getDeclaredConstructor().newInstance();
			a.setPopulation(this.population);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Clone failed - you should probably not continue using this program.");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ie) {}
		}
		return null;
	}

	@Override
	public String toString() {
		String result = "";

		result += "<algorithm ";
		result += "id=\"" + getId() + "\" ";
		result += "class=\"pso.PSO\">\n";
		result += getDefinition() + "\n";
		result += "</algorithm>\n";

		return result;
	}

	public String getId() {
		return getName() + "." + getPopulation();
	}

	public abstract String getName();
	public Integer getPopulation(){
		return this.population;
	}
	public abstract String getDefinition();
	public void setPopulation(int p) {
		this.population = p;
	}
}