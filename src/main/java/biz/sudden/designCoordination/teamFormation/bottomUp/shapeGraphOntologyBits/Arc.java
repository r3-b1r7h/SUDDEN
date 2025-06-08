package biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits;

/**
 * Protege name: Arc
 * 
 * @author ontology bean generator
 * @version 2006/11/29, 17:09:02
 */
public class Arc extends Thing {

	/**
	 * Protege name: OutputNode
	 */
	private int outputNode;

	public void setOutputNode(int value) {
		this.outputNode = value;
	}

	public int getOutputNode() {
		return this.outputNode;
	}

	/**
	 * Protege name: InputNode
	 */
	private int inputNode;

	public void setInputNode(int value) {
		this.inputNode = value;
	}

	public int getInputNode() {
		return this.inputNode;
	}

	/**
	 * Protege name: DependencyType
	 */
	private Dependency dependencyType;

	public void setDependencyType(Dependency value) {
		this.dependencyType = value;
	}

	public Dependency getDependencyType() {
		return this.dependencyType;
	}

}
