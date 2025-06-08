package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

/**
 * 
 * @author mcassmc
 * 
 *         With the actual tests :)
 */

public class Testclass {

	public static void main(String args[]) {
		System.out.println(ObjectGenerator.getState());
		AbsStateVisualiser ov = new AbsStateVisualiser(ObjectGenerator
				.getState());
		ov.makeVisible();
	}
}
