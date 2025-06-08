package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.ColHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;
import com.extentech.formats.XLS.CellNotFoundException;
import com.extentech.formats.XLS.ColumnNotFoundException;
import com.extentech.formats.XLS.WorkSheetNotFoundException;

public class TestDBDataRealSuppliers {

	// Excel Objects
	private WorkBookHandle book;
	// Repositories
	private ICompetenceInstanceRepository competenceInstanceRepository;
	private ICompetenceRepository competenceRepository;
	private ICVIInstanceRepository cviInstanceRepository;
	private ICVIRepository cviRepository;

	private IDimensionInstanceRepository dimensionInstanceRepository;
	private ColHandle dimensionInstancesColumn;
	private IDimensionRepository dimensionRepository;
	private HashMap<String, Integer> dimensions;
	private ColHandle dimensionsColumn;
	private IOrganizationRepository organizationRepository;
	private IQuestionnaireInstanceRepository questionnaireInstanceRepository;
	private IQuestionnaireRepository questionnaireRepository;
	private WorkSheetHandle sheet1;
	private ITickInstanceRepository tickInstanceRepository;
	private ITickRepository tickRepository;

	private ProductMaterialSupportingServices_Service smService;
	private EnterpriseEvaluationService enterpriseEvalService;

	private void buildCompetenceInstances(
			QuestionnaireInstance questionnaireInstance, Competence competence) {
		CompetenceInstance competenceInstance = new CompetenceInstance();
		competenceInstance.setCompetence(competence);
		competenceInstance.setDate(questionnaireInstance.getDate());
		competenceInstance.setOrganization(questionnaireInstance
				.getOrganization());
		competenceInstance.setWeight(1);

		Iterator<Dimension> iterator = competence.getDimensions().iterator();
		Scope s = enterpriseEvalService.retrieveScopeBy(questionnaireInstance
				.getOrganization().getName());
		while (iterator.hasNext()) {
			Dimension dimension = iterator.next();
			buildDimensionInstances(questionnaireInstance, dimension,
					competenceInstance, s);
		}

		competenceInstance.calculateValue();

		competenceInstanceRepository.addCompetenceInstance(competenceInstance);
		questionnaireInstance.getCompetenceInstances().add(competenceInstance);
	}

	private void buildCVIInstances(QuestionnaireInstance questionnaireInstance,
			CVI cvi, DimensionInstance dimensionInstance) {

		CVIInstance cviInstance = new CVIInstance();
		cviInstance.setCvi(cvi);
		cviInstance.setDate(questionnaireInstance.getDate());
		cviInstance.setOrganization(questionnaireInstance.getOrganization());
		cviInstance.setWeight(1);

		Integer column = new Integer(0);
		String xlsCellData = "NA";
		try {
			column = dimensions.get(dimensionInstance.getDimension().getName());
			CellHandle[] xlsCells = dimensionInstancesColumn.getCells();
			xlsCellData = xlsCells[column].getStringVal();
			System.out.println("[" + column + "]" + xlsCellData);
		} catch (NullPointerException e) {
			System.out.println("[" + column + "]"
					+ " DIMENSION NOT FOUND EXCEPTION   "
					+ dimensionInstance.getDimension().getName());
		}

		Iterator<Tick> iterator = cvi.getTicks().iterator();
		while (iterator.hasNext()) {
			Tick tick = iterator.next();
			cviInstance.getTicks().add(
					buildTickInstances(questionnaireInstance, tick,
							cviInstance, xlsCellData));
		}

		/*
		 * List<TickInstance> tickInstancesRadio = new
		 * ArrayList<TickInstance>(); for (TickInstance tickInstance :
		 * cviInstance.getTicks()) { if (tickInstance.getTick().getType() ==
		 * Tick.TICK_TYPE_RADIO) { tickInstancesRadio.add(tickInstance); } } if
		 * (tickInstancesRadio.size() > 0) { Random rnd = new Random();
		 * TickInstance tickInstanceRadio = tickInstancesRadio.get(rnd
		 * .nextInt(tickInstancesRadio.size()));
		 * tickInstanceRadio.setValue("SELECTED");
		 * tickInstanceRadio.setAutoCalcValue(tickInstanceRadio.getTick()
		 * .getTNumValue()); }
		 */

		for (TickInstance tickInstance : cviInstance.getTicks()) {
			tickInstance.calculateValue();
			tickInstanceRepository.addTickInstance(tickInstance);
		}

		cviInstance.calculateValue();

		cviInstanceRepository.addCVIInstance(cviInstance);
		dimensionInstance.setCviInstance(cviInstance);
	}

	private void buildDimensionInstances(
			QuestionnaireInstance questionnaireInstance, Dimension dimension,
			CompetenceInstance parentInstance, Scope scope) {
		DimensionInstance dimensionInstance = new DimensionInstance();
		dimensionInstance.setDimension(dimension);
		dimensionInstance.setDate(questionnaireInstance.getDate());
		dimensionInstance.setOrganization(questionnaireInstance
				.getOrganization());
		dimensionInstance.setWeight(1);

		buildCVIInstances(questionnaireInstance, dimension.getCvi(),
				dimensionInstance);

		dimensionInstance.calculateValue();

		dimensionInstanceRepository.addDimensionInstance(dimensionInstance);
		parentInstance.getDimensionInstances().add(dimensionInstance);

		// for georgs stuff
		enterpriseEvalService.associateOccurence(dimensionInstance.getValue(),
				dimension, "Double", scope);

	}

	private void buildQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance,
			Questionnaire questionnaire) {
		Iterator<Competence> iterator = questionnaire.getCompetences()
				.iterator();

		while (iterator.hasNext()) {
			Competence competence = iterator.next();
			buildCompetenceInstances(questionnaireInstance, competence);
		}

		questionnaireInstance.calculateValue();
	}

	private TickInstance buildTickInstances(
			QuestionnaireInstance questionnaireInstance, Tick tick,
			CVIInstance parentInstance, String xlsCellData) {

		TickInstance tickInstance = new TickInstance();
		tickInstance.setTick(tick);
		tickInstance.setDate(questionnaireInstance.getDate());
		tickInstance.setOrganization(questionnaireInstance.getOrganization());
		tickInstance.setWeight(1);

		if (xlsCellData != null && !xlsCellData.equals("NA")) {
			xlsCellData = xlsCellData.replace('"', ' ');
			xlsCellData = xlsCellData.trim();
			switch (tick.getType()) {
			case Tick.TICK_TYPE_CHECK:
				tickInstance.setValue("false");
				if (xlsCellData.indexOf(tickInstance.getTick().getTTextValue()) != -1
						&& !xlsCellData.equals("NA")) {
					tickInstance.setValue("true");
					tickInstance.setAutoCalcValue(tick.getTNumValue());
				}
				break;
			case Tick.TICK_TYPE_RADIO:
				tickInstance.setValue("NOT SELECTED");
				if (xlsCellData.indexOf(tickInstance.getTick().getTTextValue()) != -1
						&& !xlsCellData.equals("NA")) {
					tickInstance.setValue("SELECTED");
					tickInstance.setAutoCalcValue(tick.getTNumValue());
				}
				break;
			case Tick.TICK_TYPE_NUMBER:
				tickInstance.setValue(String.valueOf(0));
				if (!xlsCellData.equals("NA")) {
					tickInstance.setValue(xlsCellData);
				}
				try {
					tickInstance.setAutoCalcValue(Float.valueOf(tickInstance
							.getValue()));
				} catch (Exception e) {
					tickInstance.setAutoCalcValue(0);
				}
				break;
			case Tick.TICK_TYPE_STRING:
				tickInstance.setValue("");
				if (!xlsCellData.equals("NA")) {
					tickInstance.setValue(xlsCellData);
				}
				tickInstance.setAutoCalcValue(0);
				break;
			case Tick.TICK_AUTOVALUE_MACHINERY_TYPES:
			case Tick.TICK_AUTOVALUE_MATERIAL_NUMBER:
			case Tick.TICK_AUTOVALUE_MATERIAL_PROCESSING:
			case Tick.TICK_AUTOVALUE_PARTS_NUMBER:
				tickInstance.setValue("");
				if (!xlsCellData.equals("NA")) {
					tickInstance.setValue(xlsCellData);
				}
				try {
					tickInstance.setAutoCalcValue(Float.valueOf(tickInstance
							.getValue()));
				} catch (Exception e) {
					tickInstance.setAutoCalcValue(0);
				}
				break;
			}
		}

		return tickInstance;
	}

	public ICompetenceInstanceRepository getCompetenceInstanceRepository() {
		return competenceInstanceRepository;
	}

	public ICompetenceRepository getCompetenceRepository() {
		return competenceRepository;
	}

	public ICVIInstanceRepository getCviInstanceRepository() {
		return cviInstanceRepository;
	}

	public ICVIRepository getCviRepository() {
		return cviRepository;
	}

	public IDimensionInstanceRepository getDimensionInstanceRepository() {
		return dimensionInstanceRepository;
	}

	public IDimensionRepository getDimensionRepository() {
		return dimensionRepository;
	}

	public IOrganizationRepository getOrganizationRepository() {
		return organizationRepository;
	}

	public IQuestionnaireInstanceRepository getQuestionnaireInstanceRepository() {
		return questionnaireInstanceRepository;
	}

	public IQuestionnaireRepository getQuestionnaireRepository() {
		return questionnaireRepository;
	}

	public ITickInstanceRepository getTickInstanceRepository() {
		return tickInstanceRepository;
	}

	public ITickRepository getTickRepository() {
		return tickRepository;
	}

	private List<String> parseAndAdd(String parseme) {
		List<String> result = new LinkedList<String>();
		String[] tmp = parseme.split(";");
		add(result, tmp);

		tmp = parseme.split(",");
		int pointer = 1;
		// this would split 0,5 also int 0 and 5
		for (int i = 0; (i + pointer) < tmp.length; ++i) {
			if ((tmp[i].charAt(tmp[i].length() - 1) == '0'
					|| tmp[i].charAt(tmp[i].length() - 1) == '1'
					|| tmp[i].charAt(tmp[i].length() - 1) == '2'
					|| tmp[i].charAt(tmp[i].length() - 1) == '3'
					|| tmp[i].charAt(tmp[i].length() - 1) == '4'
					|| tmp[i].charAt(tmp[i].length() - 1) == '5'
					|| tmp[i].charAt(tmp[i].length() - 1) == '6'
					|| tmp[i].charAt(tmp[i].length() - 1) == '7'
					|| tmp[i].charAt(tmp[i].length() - 1) == '8' || tmp[i]
					.charAt(tmp[i].length() - 1) == '9')
					&& (tmp[i + pointer].charAt(0) == '0'
							|| tmp[i + pointer].charAt(0) == '1'
							|| tmp[i + pointer].charAt(0) == '2'
							|| tmp[i + pointer].charAt(0) == '3'
							|| tmp[i + pointer].charAt(0) == '4'
							|| tmp[i + pointer].charAt(0) == '5'
							|| tmp[i + pointer].charAt(0) == '6'
							|| tmp[i + pointer].charAt(0) == '7'
							|| tmp[i + pointer].charAt(0) == '8' || tmp[i
							+ pointer].charAt(0) == '9')) {
				tmp[i] = tmp[i] + ',' + tmp[i + pointer];
				tmp[i + pointer] = null;
				i--;
				pointer++;
			} else {
				pointer = 1;
			}
		}
		add(result, tmp);

		tmp = parseme.split("/");
		add(result, tmp);

		return result;
	}

	private void add(List<String> result, String[] tmp) {
		for (int i = 0; tmp != null && i < tmp.length; ++i) {
			if (tmp[i] != null && tmp[i].length() > 0) {
				int x = 0, y = tmp[i].length();
				while (tmp[i].charAt(x) == '"' || tmp[i].charAt(x) == '\''
						|| tmp[i].charAt(x) == ' ' || tmp[i].charAt(x) == '\t')
					x++;
				while (tmp[i].charAt(y - 1) == '"'
						|| tmp[i].charAt(y - 1) == '\''
						|| tmp[i].charAt(y - 1) == ' '
						|| tmp[i].charAt(y - 1) == '\t')
					y--;
				String substr = tmp[i].substring(x, y);
				if (!result.contains(substr)) {
					result.add(substr);
				}
			}
		}
	}

	private void associate(Organization o, List<String> products,
			List<String> materials, List<String> technologies,
			List<String> machines) {
		List<Item> prodServices = new LinkedList<Item>();
		for (String p : products) {
			if (!p.equalsIgnoreCase("na")) {
				List<biz.sudden.knowledgeData.serviceManagement.domain.System> pp = this.smService
						.retrieveSystem(p);
				if (pp != null && pp.size() > 0)
					prodServices.addAll(pp);
				List<ComplexProduct> cp = this.smService
						.retrieveComplexProduct(p);
				if (cp != null && cp.size() > 0)
					prodServices.addAll(cp);
				List<SimpleProduct> sp = this.smService
						.retrieveSimpleProduct(p);
				if (sp != null && sp.size() > 0)
					prodServices.addAll(sp);
				List<SupportingService> ss = this.smService
						.retrieveSupportingService(p);
				if (ss != null && ss.size() > 0)
					prodServices.addAll(ss);
			}
		}
		List<Material> mat = new LinkedList<Material>();
		for (String m : materials) {
			if (!m.equalsIgnoreCase("na")) {
				List<Material> mm = this.smService.retrieveMaterial(m);
				if (mm != null && mm.size() > 0)
					mat.addAll(mm);
			}

		}
		List<Technology> tec = new LinkedList<Technology>();
		for (String t : technologies) {
			if (!t.equalsIgnoreCase("na")) {
				Technology tt = this.smService.retrieveTechnoloy(t);
				if (tt != null)
					tec.add(tt);
			}
		}
		Scope orgScope = smService.retrieveScope(o.getName());
		if (orgScope == null)
			orgScope = smService.createScope(o.getName());
		for (int i = 0; i < prodServices.size(); ++i) {
			smService.addProductsServices(o, prodServices.get(i));
			for (int ii = 0; ii < mat.size(); ++ii) {
				smService.addMaterial(prodServices.get(i), mat.get(ii),
						orgScope);
				for (int iii = 0; iii < tec.size(); iii++) {
					smService
							.addTechnology(mat.get(ii), tec.get(iii), orgScope);
					for (int iv = 0; iv < machines.size(); iv++) {
						smService.addMachine(tec.get(iii), machines.get(iv),
								orgScope);
					}
				}
			}
		}

	}

	public void insertDBTestDataInstances() {
		List<Questionnaire> questionnaires = questionnaireRepository
				.getAllQuestionnaires();
		List<Organization> organizations = new ArrayList<Organization>();

		// for windows users
		File bookfile = new File("c:/SUDDEN_REAL_SUPPLIERS_DATA.xls");
		System.err.println("Try to load data from: "
				+ bookfile.getAbsolutePath());
		if (!bookfile.canRead()) {
			// now try for linux
			bookfile = new File(System.getProperty("user.home")
					+ "/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// now try for upload folder
			bookfile = new File(System.getProperty("user.home")
					+ "/upload/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// now try for database folder
			bookfile = new File(System.getProperty("user.home")
					+ "/database/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// now try for linux
			bookfile = new File("/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check local.... where ever this is .. should be the same place as
			// the sudden hsql db.
			bookfile = new File("./SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check local../upload/...... where ever this is .. should be the
			// same place as the sudden hsql db.
			bookfile = new File("./upload/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check local../database/...... where ever this is .. should be the
			// same place as the sudden hsql db.
			bookfile = new File("./database/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check D
			bookfile = new File("d:/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check D
			bookfile = new File("d:/SUDDEN/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check D
			bookfile = new File(
					"d:/SUDDEN/upload/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		if (!bookfile.canRead()) {
			// check D
			bookfile = new File(
					"d:/SUDDEN/database/SUDDEN_REAL_SUPPLIERS_DATA.xls");
			System.err.println("Try to load data from: "
					+ bookfile.getAbsolutePath());
		}
		// Create new NameHandle from a CellRange
		book = new WorkBookHandle(bookfile.getAbsolutePath());
		try {
			sheet1 = book.getWorkSheet("Tabelle1");
		} catch (WorkSheetNotFoundException e) {
			e.printStackTrace();
		}
		// never used -> GW commented out
		// CellRange range = null;
		// try {
		// range = new CellRange("Tabelle1!C3:S201", book);
		// } catch (CellNotFoundException e1) {
		// e1.printStackTrace();
		// }

		try {
			dimensionsColumn = sheet1.getCol("A");
		} catch (ColumnNotFoundException e) {
			e.printStackTrace();
		}

		if (questionnaires.size() > 0) {
			// to allow kurt or nik to configure sudden with this sheet, we need
			// a bit more dynamics here.
			// we walk through the sheet and look at row 14
			// as long as there is something in, then we have some organisation
			boolean someOrgValues = true;
			for (int c0 = 'C', c1 = 0; someOrgValues; c0++, c1++) {
				try {
					if (sheet1.getCell(13, c1 + 2).getStringVal().length() > 0) {
						Organization organization = organizationRepository
								.addOrganization("RO Company Manager "
										+ Integer.toString(c0 + 1), "", "",
										"RO Company Manager Name "
												+ Integer.toString(c0 + 1), 0,
										sheet1.getCell(13, c1 + 2)
												.getStringVal());// 'Real'
																	// organisation
																	// names
						organizations.add(organization);
						// get Products and Materials ... and associate these
						// with the organisation
						associate(organization, parseAndAdd(sheet1.getCell(34,
								c1 + 2).getStringVal()), // products Row 35
								parseAndAdd(sheet1.getCell(36, c1 + 2)
										.getStringVal()), // materials Row 37
								parseAndAdd(sheet1.getCell(35, c1 + 2)
										.getStringVal()), // technologies Row 36
								parseAndAdd(sheet1.getCell(37, c1 + 2)
										.getStringVal())); // machines Row 38d
					} else {
						someOrgValues = false;
					}
				} catch (CellNotFoundException ee) {
					System.out.println("stopped parsing here: "
							+ ee.getMessage());
					// cell not found
					someOrgValues = false;
				}
			}

			dimensions = new HashMap<String, Integer>();
			CellHandle[] xlsCellsDimensions = dimensionsColumn.getCells();
			for (int c2 = 0; c2 < 195; c2++) {
				dimensions.put(xlsCellsDimensions[c2].getStringVal(),
						new Integer(c2));
			}

			for (int c1 = 0; c1 < organizations.size(); c1++) {
				Organization organization = organizations.get(c1);
				System.out.println("[ORGANIZATION " + c1
						+ "] -------------------------------");
				try {
					dimensionInstancesColumn = sheet1.getCol(c1 + 2);
				} catch (ColumnNotFoundException e) {
					e.printStackTrace();
				}
				for (Questionnaire questionnaire : questionnaires) {
					insertDBTestDataInstances(questionnaire, organization);
				}
				System.out
						.println("--------------------------------------------------------");
			}
		}
	}

	private void insertDBTestDataInstances(Questionnaire questionnaire,
			Organization organization) {
		QuestionnaireInstance questionnaireInstance = new QuestionnaireInstance();
		questionnaireInstance.setQuestionnaire(questionnaire);
		questionnaireInstance.setOrganization(organization);
		questionnaireInstance.setWeight(1);
		questionnaireInstance.setDate(new java.util.Date());
		buildQuestionnaireInstance(questionnaireInstance, questionnaire);
		questionnaireInstanceRepository
				.addQuestionnaireInstance(questionnaireInstance);
	}

	public void setCompetenceInstanceRepository(
			ICompetenceInstanceRepository competenceInstanceRepository) {
		this.competenceInstanceRepository = competenceInstanceRepository;
	}

	public void setCompetenceRepository(
			ICompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public void setCviInstanceRepository(
			ICVIInstanceRepository cviInstanceRepository) {
		this.cviInstanceRepository = cviInstanceRepository;
	}

	public void setCviRepository(ICVIRepository cviRepository) {
		this.cviRepository = cviRepository;
	}

	public void setDimensionInstanceRepository(
			IDimensionInstanceRepository dimensionInstanceRepository) {
		this.dimensionInstanceRepository = dimensionInstanceRepository;
	}

	public void setDimensionRepository(IDimensionRepository dimensionRepository) {
		this.dimensionRepository = dimensionRepository;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	public void setQuestionnaireInstanceRepository(
			IQuestionnaireInstanceRepository questionnaireInstanceRepository) {
		this.questionnaireInstanceRepository = questionnaireInstanceRepository;
	}

	public void setQuestionnaireRepository(
			IQuestionnaireRepository questionnaireRepository) {
		this.questionnaireRepository = questionnaireRepository;
	}

	public void setTickInstanceRepository(
			ITickInstanceRepository tickInstanceRepository) {
		this.tickInstanceRepository = tickInstanceRepository;
	}

	public void setTickRepository(ITickRepository tickRepository) {
		this.tickRepository = tickRepository;
	}

	public void setSmService(ProductMaterialSupportingServices_Service smService) {
		this.smService = smService;
	}

	public ProductMaterialSupportingServices_Service getSmService() {
		return this.smService;
	}

	public void setEnterpriseEvalService(EnterpriseEvaluationService service) {
		this.enterpriseEvalService = service;
	}

	public EnterpriseEvaluationService getEnterpriseEvalService() {
		return this.enterpriseEvalService;
	}

}