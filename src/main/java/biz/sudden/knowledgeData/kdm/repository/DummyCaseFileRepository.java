package biz.sudden.knowledgeData.kdm.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitService;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

/**
 * Dummy implementation of the case file repository that returns hard-coded
 * values for test purposes.
 */
public class DummyCaseFileRepository implements CaseFileRepository {
	private static final Pattern TEAM_PATTERN = Pattern
			.compile("[\\s]*([\\w /-]+)[\\s]*:[\\s]*([\\w /-]+)[\\s]*\\([\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*,[\\s]*([\\d]*)[\\s]*\\)[\\s]*\\([\\s]*(([\\d]{4}-[\\d]{2}-[\\d]{2})|([\\s]*))[\\s]*,[\\s]*(([\\d]{4}-[\\d]{2}-[\\d]{2})|([\\s]*))[\\s]*\\)");

	private CoordinationFitService coordinationFitService;
	private IOrganizationRepository organizationRepository;

	private Map<Long, CaseFile> caseFiles = null;
	private AtomicLong atomicLong = new AtomicLong(0);

	private static final List<String[]> supplierNames = new ArrayList<String[]>();
	static {
		supplierNames
				.add(new String[] {
						"Airbag:WEBA (50 , , 70, , 10, 20, 15, ) (,)",
						"MechanikElektrik:SlowCo (100, 120, 110, , 20, 25, 23, ) (,)",
						"VolkunstroffTrager:Supplier X (80, 100, 85, , 17, 20, 18, ) (,)",
						"Tragerskelett:Supplier Y (50, 100, 75, , 10, 20, 17, ) (,)",
						"Aussenhaut:BMW (80, 100, 90, , 17, 20, 18, ) (,)",
						"Hybridtrager:Automotive Solutions (100, 120, 115, , 20, 25, 22, ) (,)",
						"Innenverkleidung:Supplier Z (50, 100, 75, , 10, 25, 20, ) (,)" });
		supplierNames
				.add(new String[] {
						"Airbag:WEBA (50 , , 70, , 10, 20, 15, ) (,)",
						"MechanikElektrik:SlowCo (100, 120, 110, , 20, 25, 23, ) (,)",
						"VolkunstroffTrager:Supplier A (100,120, 110, , 20, 25, 23, ) (,)",
						"Tragerskelett:Supplier B (30, 80, 50, , 7, 15, 12, ) (,)",
						"Aussenhaut:BMW (80, 100, 90, , 17, 20, 18, ) (,)",
						"Hybridtrager:Automotive Solutions (100, 120, 115, , 20, 25, 22, ) (,)",
						"Innenverkleidung:Supplier C (70, 100, 85, , 13, 20, 18, ) (,)" });
		supplierNames
				.add(new String[] {
						"Design:Graph Carello (50, 100, 70, 80, 10, 20, 15, 20) ( 2008-02-01 , 2008-03-01)",
						"Part A manufacturing:SlowCo (100,120, 110, 110, 20, 25, 23, 23) ( 2008-02-01 , 2008-03-01)",
						"Part B manufacturing:Supplier X (80, 100, 85, 125, 17, 20, 18, 25) ( 2008-02-01 , 2008-03-01)",
						"Assemply:Supplier Y (50, 100, 75, 125, 10, 20, 17, 25) ( 2008-02-01 , 2008-03-01)",
						"Test:BMW (80, 100, 90, 100, 17, 20, 18, 19) ( 2008-02-01 , 2008-03-01)",
						"Marketing:Automotive Solutions (100, 120, 115, 112, 20, 25, 22, 22) ( 2008-02-01 , 2008-03-01)",
						"Delivery/Production:Supplier Z (50, 100, 75, 100, 10, 25, 20, 25) ( 2008-02-01 , 2008-03-01)" });
		supplierNames
				.add(new String[] {
						"Design:Door Designer CO (50 , 100, 70,	80,	10, 20, 15, 20) ( 2008-02-01 , 2008-03-01)",
						"Part A manufacturing:Safety Rod manufacturer (100,120, 110, 110, 20, 25, 23, 23) ( 2008-02-01 , 2008-03-01)",
						"Part B manufacturing:Supplier A (80, 100, 85, 90, 17, 20, 18, 20) ( 2008-02-01 , 2008-03-01)",
						"Assemply:Supplier B (50, 100, 75, 90, 10, 20, 17, 18) ( 2008-02-01 , 2008-03-01)",
						"Test:Test Dummies CO (80, 100, 90, 100, 17, 20, 18, 19) ( 2008-02-01 , 2008-03-01)",
						"Marketing:Auto- Marketing IMBH (100, 120, 115, 112, 20, 25, 22, 22) ( 2008-02-01 , 2008-03-01)",
						"Delivery/Production:Supplier C (50, 100, 75, 5, 90, 10, 25, 20) ( 2008-02-01 , 2008-03-01)" });
	}

	public void setCoordinationFitService(
			CoordinationFitService coordinationFitService) {
		this.coordinationFitService = coordinationFitService;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public Long create(CaseFile d) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public void delete(CaseFile d) {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public CaseFile retrieve(Long id) {
		makeDummyCaseFiles();
		if (id == null)
			throw new RuntimeException("id should not be null.");

		if (0 == id.longValue())
			return null; // emulate that the case was not found

		return caseFiles.get(id);
	}

	@Override
	public void update(CaseFile d) {
	}

	@Override
	public List<CaseFile> retrieveAll() {
		makeDummyCaseFiles();
		return new ArrayList<CaseFile>(caseFiles.values());
	}

	public List<CaseFile> retrieveByKeyword(String name, String sundryInfo) {
		return retrieveAll();
	}

	private void makeDummyCaseFiles() {
		if (caseFiles == null) {
			caseFiles = new HashMap<Long, CaseFile>();

			CaseFile caseFile1 = makeDummyCaseFile(
					"New BMW car door",
					"the car door should contain a central locking subsystem, locking system and safety rod, front speakers etcv",
					false, null, Math.random() + 6.5, 0, 0, supplierNames
							.subList(0, 2));

			caseFiles.put(caseFile1.getId(), caseFile1);

			CaseFile caseFile2 = makeDummyCaseFile("Graph Carello mini car",
					"Graph Carello mini car", true, supplierNames.get(2), 7d,
					0.6d, 0.55d, supplierNames.subList(1, 2));
			caseFiles.put(caseFile2.getId(), caseFile2);

			CaseFile caseFile3 = makeDummyCaseFile("BMW car door",
					"BMW car door", true, supplierNames.get(3), 6, 0.8, 0.75,
					supplierNames.subList(1, 3));
			caseFiles.put(caseFile3.getId(), caseFile3);
		}
	}

	private CaseFile makeDummyCaseFile(String boName, String description,
			boolean completed, String[] finalTeam, double tfRating,
			double inTimeRating, double inBudgetRating, List<String[]> tempTeams) {
		Long id = atomicLong.addAndGet(1);
		CaseFile caseFile = new CaseFile();
		caseFile.setId(id);
		if (completed)
			caseFile.setPhase((short) 5);

		BusinessOpportunity bo = makeDummyBO(boName, description);
		caseFile.setBo(bo);

		ArrayList<ConcreteSupplyNetwork> tempTeamCSNs = new ArrayList<ConcreteSupplyNetwork>();
		if (finalTeam != null) {
			ConcreteSupplyNetwork finalTeamCSN = makeConcreteSupplyNetwork(
					null, tfRating, finalTeam);
			finalTeamCSN.getCoordinationFitResults().setInTimeEfficiencyRating(
					inTimeRating);
			finalTeamCSN.getCoordinationFitResults()
					.setInBudgetEfficiencyRating(inBudgetRating);

			caseFile.setAsnFinalTeam(finalTeamCSN.getASN());
			caseFile.setFinalTeam(finalTeamCSN);

			tempTeamCSNs.add(finalTeamCSN);
		}

		if (tempTeams != null) {
			for (String[] team : tempTeams) {
				double random = Math.random() + 6.5;

				ConcreteSupplyNetwork tempTeam = makeConcreteSupplyNetwork(
						null, random, team);
				tempTeam.getCoordinationFitResults().setInTimeEfficiencyRating(
						inTimeRating);
				tempTeam.getCoordinationFitResults()
						.setInBudgetEfficiencyRating(inBudgetRating);
				tempTeamCSNs.add(tempTeam);
			}
		}
		caseFile.setTempTeams(tempTeamCSNs);

		return caseFile;
	}

	private ConcreteSupplyNetwork makeConcreteSupplyNetwork(Long id,
			double tfRank, String[] team) {
		List<TeamMember> parsedTeam = new ArrayList<TeamMember>();
		for (String teamMember : team) {
			Matcher matcher = TEAM_PATTERN.matcher(teamMember);
			if (!matcher.matches())
				throw new IllegalArgumentException("team member '" + teamMember
						+ "' does not match pattern " + TEAM_PATTERN.toString());

			parsedTeam.add(new TeamMember(matcher.group(1), matcher.group(2),
					matcher.group(3), matcher.group(5), matcher.group(4),
					matcher.group(6), matcher.group(7), matcher.group(9),
					matcher.group(8), matcher.group(10), matcher.group(11),
					matcher.group(14)));
		}

		AbstractSupplyNetwork asn = new AbstractSupplyNetwork();

		ASNInitialNode initialNode = new ASNInitialNode(null);
		asn.addNewNode(initialNode);

		ASNRoleNode aNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(0).getTaskName()));
		asn.addNewNode(aNode);
		asn.addNewDependcy(new ASNMaterialDependency(initialNode, aNode));

		ASNRoleNode bNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(1).getTaskName()));
		asn.addNewNode(bNode);
		asn.addNewDependcy(new ASNMaterialDependency(aNode, bNode));

		ASNRoleNode cNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(2).getTaskName()));
		asn.addNewNode(cNode);
		asn.addNewDependcy(new ASNMaterialDependency(aNode, cNode));

		ASNRoleNode dNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(3).getTaskName()));
		asn.addNewNode(dNode);
		asn.addNewDependcy(new ASNMaterialDependency(bNode, dNode));
		asn.addNewDependcy(new ASNMaterialDependency(cNode, dNode));

		ASNRoleNode eNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(4).getTaskName()));
		asn.addNewNode(eNode);
		asn.addNewDependcy(new ASNMaterialDependency(dNode, eNode));

		ASNRoleNode fNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(5).getTaskName()));
		asn.addNewNode(fNode);
		asn.addNewDependcy(new ASNMaterialDependency(dNode, fNode));

		ASNRoleNode gNode = new ASNRoleNode(null,
				makeQualificationProfile(parsedTeam.get(6).getTaskName()));
		asn.addNewNode(gNode);
		asn.addNewDependcy(new ASNMaterialDependency(eNode, gNode));
		asn.addNewDependcy(new ASNMaterialDependency(fNode, gNode));

		// TODO- briefly disabled for a few reasons. Hopefully can now hook
		// coord fit up to HBO directly?
		// Product p = null;
		// asn.addFinalNode(p);
		// asn.addNewDependcy(new ASNMaterialDependency(gNode,
		// asn.getFinalNode()));

		Map<ASNRoleNode, Supplier> candidateSuppliers = new HashMap<ASNRoleNode, Supplier>();
		candidateSuppliers.put(aNode, setupSupplier(parsedTeam.get(0)));
		candidateSuppliers.put(bNode, setupSupplier(parsedTeam.get(1)));
		candidateSuppliers.put(cNode, setupSupplier(parsedTeam.get(2)));
		candidateSuppliers.put(dNode, setupSupplier(parsedTeam.get(3)));
		candidateSuppliers.put(eNode, setupSupplier(parsedTeam.get(4)));
		candidateSuppliers.put(fNode, setupSupplier(parsedTeam.get(5)));
		candidateSuppliers.put(gNode, setupSupplier(parsedTeam.get(6)));

		ConcreteSupplyNetwork concreteSupplyNetwork = new ConcreteSupplyNetwork(
				asn, candidateSuppliers);
		concreteSupplyNetwork.setId(id);
		concreteSupplyNetwork.setRanking(new Double(tfRank));

		if (concreteSupplyNetwork.isReadyForCF()) {
			concreteSupplyNetwork
					.setCoordinationFitResults(coordinationFitService
							.evaluate(concreteSupplyNetwork));
		}

		return concreteSupplyNetwork;
	}

	private QualificationProfile makeQualificationProfile(String productName) {
		SimpleProduct productIn = new SimpleProduct();
		productIn.setName(productName);

		QualificationProfile qualificationProfile = new QualificationProfile();
		qualificationProfile.addProductType(productIn.getName());

		return qualificationProfile;
	}

	private Supplier setupSupplier(TeamMember teamMember) {
		Supplier supplier = setupEmptySupplier(teamMember.getSupplierName());

		supplier.setMinCostProposal(teamMember.getMinCost());
		supplier.setAvgCostProposal(teamMember.getAvgCost());
		supplier.setMaxCostProposal(teamMember.getMaxCost());
		supplier.setCostActual(teamMember.getActualCost());

		supplier.setMinDurationProposal(teamMember.getMinDuration());
		supplier.setAvgDurationProposal(teamMember.getAvgDuration());
		supplier.setMaxDurationProposal(teamMember.getMaxDuration());
		supplier.setDurationActual(teamMember.getActualDuration());

		supplier.setStartDate(teamMember.getStartDate());
		supplier.setActualsForDate(teamMember.getActualsDate());

		return supplier;
	}

	private Supplier setupEmptySupplier(String name) {
		Organization organization = organizationRepository.retrieveByFieldName(
				"name", name);
		if (organization == null) {
			organization = new Organization();
			organization.setName(name);
			organizationRepository.create(organization);
		}

		return new Supplier(organization);
	}

	private BusinessOpportunity makeDummyBO(String name, String description) {
		BusinessOpportunity bo = new BusinessOpportunity();
		bo.setName(name);
		bo.setDescription(description);
		return bo;
	}

	@Override
	public CaseFile retrieveByFieldName(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CaseFile retrieveByFieldNameContains(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CaseFile retrieveByFieldNameContainsLowerCase(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CaseFile retrieveByFieldNameLowerCase(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> List<S> retrieveAllByType(Class<S> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseFile> retrieveAllCompleted() {
		List<CaseFile> results = new LinkedList<CaseFile>();

		for (CaseFile caseFile : caseFiles.values()) {
			if (caseFile.getPhase() == 5)
				results.add(caseFile);
		}
		return results;
	}

	static class TeamMember {

		private final String taskName;
		private final String supplierName;
		private final Long minDuration;
		private final Long avgDuration;
		private final Long maxDuration;
		private final Long actualDuration;
		private final Double minCost;
		private final Double avgCost;
		private final Double maxCost;
		private final Double actualCost;

		private final Date startDate;
		private final Date endDate;

		public TeamMember(String taskName, String supplierName,
				String minDuration, String avgDuration, String maxDuration,
				String actualDuration, String minCost, String avgCost,
				String maxCost, String actualCost, String startDate,
				String endDate) {
			this.taskName = taskName;
			this.supplierName = supplierName;

			this.minDuration = minDuration.length() == 0 ? null : Long
					.valueOf(minDuration);
			this.avgDuration = avgDuration.length() == 0 ? null : Long
					.valueOf(avgDuration);
			this.maxDuration = maxDuration.length() == 0 ? null : Long
					.valueOf(maxDuration);
			this.actualDuration = actualDuration.length() == 0 ? null : Long
					.valueOf(actualDuration);

			this.minCost = minCost.length() == 0 ? null : Double
					.valueOf(minCost);
			this.avgCost = avgCost.length() == 0 ? null : Double
					.valueOf(avgCost);
			this.maxCost = maxCost.length() == 0 ? null : Double
					.valueOf(maxCost);
			this.actualCost = actualCost.length() == 0 ? null : Double
					.valueOf(actualCost);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (startDate != null && startDate.length() != 0) {
				try {
					this.startDate = dateFormat.parse(startDate);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			} else
				this.startDate = null;

			if (endDate != null && endDate.length() != 0) {
				try {
					this.endDate = dateFormat.parse(endDate);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			} else
				this.endDate = null;
		}

		public String getTaskName() {
			return taskName;
		}

		public String getSupplierName() {
			return supplierName;
		}

		public Long getMinDuration() {
			return minDuration;
		}

		public Long getAvgDuration() {
			return avgDuration;
		}

		public Long getMaxDuration() {
			return maxDuration;
		}

		public Long getActualDuration() {
			return actualDuration;
		}

		public Double getMinCost() {
			return minCost;
		}

		public Double getAvgCost() {
			return avgCost;
		}

		public Double getMaxCost() {
			return maxCost;
		}

		public Double getActualCost() {
			return actualCost;
		}

		public Date getStartDate() {
			return startDate;
		}

		public Date getActualsDate() {
			return endDate;
		}

	}

	@Override
	public boolean containsObject(CaseFile object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CaseFile retrieve(BusinessOpportunity bo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseFile> retrieveAllByFieldName(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseFile> retrieveAllByFieldNameContains(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseFile> retrieveAllByFieldNameContainsLowerCase(
			String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CaseFile> retrieveAllByFieldNameLowerCase(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> List<S> retrieveAllChildren(CaseFile parent,
			Class<S> childrenType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> S retrieveByTypeAndId(Class<S> type, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, String> retrieveAllCaseFileNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
