package biz.sudden.knowledgeData.competencesManagement.domain;

public class GapClass {
	public static final int undefined = -9999;

	public static final int gap = -100;
	public static final int potentialGap = -50;
	public static final int fit = 0;
	public static final int potentialOverqualification = 50;
	public static final int overqualification = 100;

	public static boolean isGap(int gapClass) {
		return gapClass == gap || gapClass == potentialGap;
	}
}
