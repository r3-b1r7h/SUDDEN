package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.lang.sl.SL1Vocabulary;
import jade.content.lang.sl.SL2Vocabulary;
import jade.content.lang.sl.SLVocabulary;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for compiling IRE's into a directly applicable form.
 * 
 *         This class takes care of the generic aspects - the attached
 *         compiledPredicateFactory deals with the localised aspects.
 */

public class CompiledIREFactory {

	private CompiledPredicateFactory myCompiledPredicateMaker;

	public CompiledIREFactory(CompiledPredicateFactory predFactoryToUse) {
		this.myCompiledPredicateMaker = predFactoryToUse;
	}

	public CompiledIRE generateCompiledIRE(AbsIRE IREin)
			throws UnknownIRETypeException, predicateNotUnderstoodError {
		/*
		 * Easy stuff really - pick the type straight from the IRE itself, then
		 * compile the proposition (recursive, fixed result for And/Or/Not & a
		 * hashtable look up for more specalised IRE's) & stick it in.
		 */
		CompiledIRE result;

		String IREType = IREin.getTypeName();

		if (IREType.equals(SL2Vocabulary.IOTA)) {
			result = new CompiledIOTAIre();
		} else if (IREType.equals(SL2Vocabulary.ANY)) {
			result = new CompiledAnyIre();
		} else if (IREType.equals(SL2Vocabulary.ALL)) {
			result = new CompiledAllIre();
		} else {
			// Freak out - someone has expanded the SL specification :)
			// This should basically never happen since we've checked that the
			// IRE
			// matches JADE's requirements already.
			throw new UnknownIRETypeException(IREin);
		}
		result.setOriginalIRE(IREin);
		AbsPredicate IREProposition = IREin.getProposition();
		result.setCompiledPredicateNode(getCompiledPredNode(IREProposition));

		return result;
	}

	/*
	 * Tree recursion....
	 */
	private CompiledPredicateNode getCompiledPredNode(AbsPredicate proposition)
			throws predicateNotUnderstoodError {

		CompiledPredicateNode result;

		String absPredicateType = proposition.getTypeName();

		if (absPredicateType.equals(SL1Vocabulary.AND)) {
			result = new AndNode(getCompiledPredNode((AbsPredicate) proposition
					.getAbsObject(SL1Vocabulary.AND_LEFT)),
					getCompiledPredNode((AbsPredicate) proposition
							.getAbsObject(SL1Vocabulary.AND_RIGHT)));
		} else if (absPredicateType.equals(SL1Vocabulary.OR)) {
			result = new OrNode(getCompiledPredNode((AbsPredicate) proposition
					.getAbsObject(SL1Vocabulary.OR_LEFT)),
					getCompiledPredNode((AbsPredicate) proposition
							.getAbsObject(SL1Vocabulary.OR_RIGHT)));

		} else if (absPredicateType.equals(SL1Vocabulary.NOT)) {
			result = new NotNode(getCompiledPredNode((AbsPredicate) proposition
					.getAbsObject(SL1Vocabulary.NOT_WHAT)));
		} else {
			// A concrete node
			result = myCompiledPredicateMaker
					.convertToConcreteNode(proposition);
		}

		return result;
	}
}
