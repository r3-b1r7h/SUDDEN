package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;

import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForPotentialExtensions;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         Returns true iff each of the given set of agents has contributed to
 *         the given potential extension. Note that this can be used together
 *         with a not to provide a blacklist facility.
 */

public class ContainsPEsFromTest extends ConcreteCompiledPredicate {

	private LinkedList<String> agentNamesToContain;

	public ContainsPEsFromTest(AbsPredicate predicateToConvert) {
		this.agentNamesToContain = new LinkedList<String>();

		AbsAggregate names = (AbsAggregate) predicateToConvert
				.getAbsTerm(QueryForPotentialExtensions.COMPANIES);
		Iterator overNames = names.iterator();
		while (overNames.hasNext()) {
			agentNamesToContain.add(((AbsPrimitive) overNames.next())
					.getString());
		}
	}

	@Override
	public boolean evaluateMatch(Object toBeMatched) {
		PotentialExtension PEtoBeMatched = (PotentialExtension) toBeMatched;

		LinkedList<String> agentNames = new LinkedList<String>(PEtoBeMatched
				.getAgentsContributing());

		return agentNames.containsAll(agentNamesToContain);
	}

}
