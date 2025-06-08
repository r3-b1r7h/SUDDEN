package biz.sudden.knowledgeData.competencesManagement.service.impl;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;

public class CMCompetencesManagement_ServiceImpl extends
		CMServiceCategoryBaseClassImpl implements
		ICMCompetencesManagement_Service {

	private ICategoryRepository categoryRepository;
	private ICompetenceRepository competenceRepository;
	private ICVIRepository cviRepository;
	private IDimensionRepository dimensionRepository;
	private ITickRepository tickRepository;

	@Override
	public long addCategory(Category category) {
		return categoryRepository.addCategory(category);
	}

	@Override
	public long addCompetence(Competence competence) {
		return competenceRepository.addCompetence(competence);
	}

	@Override
	public long addCVI(CVI cvi) {
		return cviRepository.addCVI(cvi);
	}

	@Override
	public long addDimension(Dimension dimension) {
		return dimensionRepository.addDimension(dimension);
	}

	@Override
	public long addTick(Tick tick) {
		return tickRepository.addTick(tick);
	}

	@Override
	public List<Category> retrieveAllCategories() {
		return categoryRepository.getAllCategories();
	}

	@Override
	public List<Competence> retrieveAllCompetences() {
		return competenceRepository.getAllCompetences();
	}

	@Override
	public List<CVI> retrieveAllCVIs() {
		return cviRepository.getAllCVIs();
	}

	@Override
	public List<Dimension> retrieveAllDimensions() {
		return dimensionRepository.getAllDimensions();
	}

	@Override
	public List<Tick> retrieveAllTicks() {
		return tickRepository.getAllTicks();
	}

	@Override
	public List<Category> retrieveCategoriesByParentId(long parentCategoryId,
			int type) {
		return categoryRepository.getCategoriesByParentId(parentCategoryId,
				type);
	}

	@Override
	public List<Category> retrieveCategoriesByType(int type) {
		return categoryRepository.getCategoriesByType(type);
	}

	@Override
	public ICategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	@Override
	public Competence retrieveCompetenceById(long id) {
		return this.competenceRepository.getCompetenceById(id);
	}

	@Override
	public List<Competence> retrieveCompetenceByName(String name) {
		return this.competenceRepository.getCompetenceByName(name);
	}

	@Override
	public ICompetenceRepository getCompetenceRepository() {
		return competenceRepository;
	}

	@Override
	public List<Competence> retrieveCompetencesByCategoryId(long categoryId) {
		return competenceRepository.getCompetencesByCategoryId(categoryId);
	}

	@Override
	public CVI retrieveCVIByDimensionId(long dimensionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CVI retrieveCVIById(long id) {
		return this.cviRepository.getCVIById(id);
	}

	@Override
	public ICVIRepository getCviRepository() {
		return cviRepository;
	}

	@Override
	public List<CVI> retrieveCVIsByCategoryId(long categoryId) {
		return cviRepository.getCVIsByCategoryId(categoryId);
	}

	@Override
	public Dimension retrieveDimensionById(long id) {
		return dimensionRepository.getDimensionById(id);
	}

	@Override
	public List<Dimension> retrieveDimensionByName(String name) {
		return dimensionRepository.getDimensionByName(name);
	}

	@Override
	public IDimensionRepository getDimensionRepository() {
		return dimensionRepository;
	}

	@Override
	public List<Dimension> retrieveDimensionsByCategoryId(long categoryId) {
		return dimensionRepository.getDimensionsByCategoryId(categoryId);
	}

	@Override
	public List<Dimension> retrieveDimensionsByCompetenceId(long competenceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tick getTickById(long id) {
		return this.tickRepository.getTickById(id);
	}

	public ITickRepository getTickRepository() {
		return tickRepository;
	}

	@Override
	public void removeAllCategories() {
		categoryRepository.removeAllCategories();
	}

	@Override
	public void removeAllCompetences() {
		competenceRepository.removeAllCompetences();
	}

	@Override
	public void removeAllCVIs() {
		cviRepository.removeAllCVIs();
	}

	@Override
	public void removeAllDimensions() {
		dimensionRepository.removeAllDimensions();
	}

	@Override
	public void removeAllTicks() {
		tickRepository.removeAllTicks();

	}

	public void setCategoryRepository(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void setCompetenceRepository(
			ICompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public void setCviRepository(ICVIRepository cviRepository) {
		this.cviRepository = cviRepository;
	}

	public void setDimensionRepository(IDimensionRepository dimensionRepository) {
		this.dimensionRepository = dimensionRepository;
	}

	public void setTickRepository(ITickRepository tickRepository) {
		this.tickRepository = tickRepository;
	}

}
