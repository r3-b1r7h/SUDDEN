package biz.sudden.knowledgeData.competencesManagement.domain;

@SuppressWarnings("serial")
public class NormalizedTick extends Tick {

	protected float min;
	protected float max;

	public NormalizedTick(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public float getNormalizedTickNumValue() {
		return (getTNumValue() - min) / (max - min);
	}

}