package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ConstrainedProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * @author mcassmc
 * 
 *         A class containing a few statically accessible noticeboard
 *         descriptions and the like. (Since they can be quite wordy to
 *         produce.).
 */

// TODO - all products have been reduced to just one type. So it's probably not
// testing what it should be doing :)
public class TestNoticeboardDescriptions {

	public static AbsConcept getNoticeboardDescription() {
		AbsConcept noticeboardDescription = new AbsConcept(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);

		AbsConcept noticeboardUtility = new AbsConcept(
				CreateNoticeboardOntology.NOTICEBOARD_UTILITY);
		noticeboardUtility
				.set(
						CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE,
						AbsPrimitive.wrap("ExampleCompany"));
		noticeboardUtility.set(
				CreateNoticeboardOntology.NOTICEBOARD_UTILITY_EXPECTED_COST,
				AbsPrimitive.wrap(10));

		AbsConcept range = new AbsConcept(CreateNoticeboardOntology.RANGE);
		range.set(CreateNoticeboardOntology.RANGE_MAX, AbsPrimitive.wrap(5));
		range.set(CreateNoticeboardOntology.RANGE_MIN, AbsPrimitive.wrap(15));

		noticeboardUtility.set(
				CreateNoticeboardOntology.NOTICEBOARD_UTILITY_AMOUNT_REQUIRED,
				range);

		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_UTILITY,
				noticeboardUtility);
		return noticeboardDescription;
	}

	public static AbsConcept getInitialProcessDescription() {
		AbsConcept initialProcess = new AbsConcept(
				CreateNoticeboardOntology.INITIAL_PROCESS);
		AbsConcept goalState = new AbsConcept(ProcessOntology.STATE);

		AbsAggregate goalResources = new AbsAggregate(SL0Vocabulary.SET);

		AbsConcept constrainedProductType = new AbsConcept(
				ConstrainedProductOntology.PRODUCT);
		constrainedProductType.set(
				ConstrainedProductOntology.PRODUCT_INT_ATTRIBUTE, 1);
		constrainedProductType.set(
				ConstrainedProductOntology.PRODUCT_STRING_ATTRIBUTE, "b");

		AbsConcept CONSTRAINEDResourceSet1 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet1.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(5));
		CONSTRAINEDResourceSet1.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		goalResources.add(CONSTRAINEDResourceSet1);

		goalState.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				goalResources);

		AbsConcept initialState = new AbsConcept(ProcessOntology.STATE);

		AbsAggregate initialResources = new AbsAggregate(SL0Vocabulary.SET);

		AbsConcept CONSTRAINEDResourceSet2 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet2.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(1));
		CONSTRAINEDResourceSet2.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		initialResources.add(CONSTRAINEDResourceSet2);

		initialState.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				initialResources);

		AbsConcept intermediateState = new AbsConcept(ProcessOntology.STATE);

		AbsAggregate intermediateResources = new AbsAggregate(SL0Vocabulary.SET);

		AbsConcept CONSTRAINEDResourceSet3 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet3.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(8));
		CONSTRAINEDResourceSet3.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		intermediateResources.add(CONSTRAINEDResourceSet3);

		AbsConcept CONSTRAINEDResourceSet4 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet4.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(7));
		CONSTRAINEDResourceSet4.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		intermediateResources.add(CONSTRAINEDResourceSet4);

		intermediateState.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				intermediateResources);

		AbsConcept intermediateStateWithOrder = new AbsConcept(
				CreateNoticeboardOntology.STATE_WITH_ORDER);
		intermediateStateWithOrder.set(
				CreateNoticeboardOntology.STATE_WITH_ORDER_MAIN_STATE,
				intermediateState);

		AbsConcept intermediateState2 = new AbsConcept(ProcessOntology.STATE);

		AbsAggregate intermediateResources2 = new AbsAggregate(
				SL0Vocabulary.SET);

		AbsConcept CONSTRAINEDResourceSet5 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet5.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(8));
		CONSTRAINEDResourceSet5.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		intermediateResources2.add(CONSTRAINEDResourceSet5);

		intermediateState2.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				intermediateResources2);

		AbsConcept intermediateStateWithOrder2 = new AbsConcept(
				CreateNoticeboardOntology.STATE_WITH_ORDER);
		intermediateStateWithOrder2.set(
				CreateNoticeboardOntology.STATE_WITH_ORDER_MAIN_STATE,
				intermediateState2);

		AbsAggregate statesGreaterThan = new AbsAggregate(SL0Vocabulary.SET);
		statesGreaterThan.add(AbsPrimitive.wrap(0));
		intermediateStateWithOrder2.set(
				CreateNoticeboardOntology.STATE_WITH_ORDER_STATES_GREATER_THAN,
				statesGreaterThan);

		AbsAggregate intermediateStates = new AbsAggregate(SL0Vocabulary.SET);
		intermediateStates.add(intermediateStateWithOrder);
		intermediateStates.add(intermediateStateWithOrder2);

		AbsAggregate declaredCapabilities = new AbsAggregate(SL0Vocabulary.SET);

		AbsConcept declaredCap = new AbsConcept(
				ProcessOntology.DECLARED_CAPABILITY);

		AbsAggregate decCapResourcesIn = new AbsAggregate(SL0Vocabulary.SET);
		AbsConcept CONSTRAINEDResourceSet6 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet6.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(8));
		CONSTRAINEDResourceSet6.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		decCapResourcesIn.add(CONSTRAINEDResourceSet6);
		decCapResourcesIn.add(CONSTRAINEDResourceSet2);

		AbsAggregate decCapResourcesOut = new AbsAggregate(SL0Vocabulary.SET);
		AbsConcept CONSTRAINEDResourceSet7 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet7.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(8));
		CONSTRAINEDResourceSet7.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		decCapResourcesOut.add(CONSTRAINEDResourceSet7);

		declaredCap.set(ProcessOntology.DECLARED_CAPABILITY_PROVIDER,
				AbsPrimitive.wrap("ExampleCompanyWseMustUse"));
		declaredCap.set(ProcessOntology.DECLARED_CAPABILITY_INPUT,
				decCapResourcesIn);
		declaredCap.set(ProcessOntology.DECLARED_CAPABILITY_OUTPUT,
				decCapResourcesOut);

		AbsConcept declaredCap2 = new AbsConcept(
				ProcessOntology.DECLARED_CAPABILITY);

		AbsAggregate decCapResourcesIn2 = new AbsAggregate(SL0Vocabulary.SET);
		decCapResourcesIn2.add(CONSTRAINEDResourceSet7);

		AbsAggregate decCapResourcesOut2 = new AbsAggregate(SL0Vocabulary.SET);
		AbsConcept CONSTRAINEDResourceSet8 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		CONSTRAINEDResourceSet8.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(6));
		CONSTRAINEDResourceSet8.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProductType);
		decCapResourcesOut2.add(CONSTRAINEDResourceSet7);

		declaredCap2.set(ProcessOntology.DECLARED_CAPABILITY_PROVIDER,
				AbsPrimitive.wrap("TheSecondCompany"));
		declaredCap2.set(ProcessOntology.DECLARED_CAPABILITY_INPUT,
				decCapResourcesIn2);
		declaredCap2.set(ProcessOntology.DECLARED_CAPABILITY_OUTPUT,
				decCapResourcesOut2);

		declaredCapabilities.add(declaredCap);
		declaredCapabilities.add(declaredCap2);

		initialProcess
				.set(CreateNoticeboardOntology.INITIAL_PROCESS_GOAL_STATE,
						goalState);
		initialProcess.set(
				CreateNoticeboardOntology.INITIAL_PROCESS_INITIAL_STATE,
				initialState);
		initialProcess
				.set(
						CreateNoticeboardOntology.INITIAL_PROCESS_INTERMEDIATE_STATES_WITH_ORDER,
						intermediateStates);
		initialProcess
				.set(
						CreateNoticeboardOntology.INITIAL_PROCESS_STARTING_DECLARED_CAPABILITIES,
						declaredCapabilities);
		return initialProcess;
	}

}
