package biz.sudden.knowledgeData.competencesManagement.service;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;

/**
 * 
 * CMCompetencesManagement_Service - This interface defines Methods for
 * Competences and its entities
 * 
 * @author Victor Blazquez
 * 
 */
public interface ICMCompetencesManagement_Service extends
		ICMServiceCategoryBaseClass {

	/*
	 * Tick Entities - Access to the entities should be done via the service,
	 * not the repository
	 */

	public ITickRepository getTickRepository();

	public List<Tick> retrieveAllTicks();

	public Tick getTickById(long id);

	public long addTick(Tick tick);

	public void removeAllTicks();

	/*
	 * CVI Entities - Access to the entities should be done via the service, not
	 * the repository
	 */

	public ICVIRepository getCviRepository();

	public List<CVI> retrieveAllCVIs();

	public List<CVI> retrieveCVIsByCategoryId(long categoryId);

	public CVI retrieveCVIByDimensionId(long dimensionId);

	public CVI retrieveCVIById(long id);

	public long addCVI(CVI cvi);

	public void removeAllCVIs();

	/*
	 * Dimension Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public IDimensionRepository getDimensionRepository();

	public List<Dimension> retrieveAllDimensions();

	public List<Dimension> retrieveDimensionsByCategoryId(long categoryId);

	public List<Dimension> retrieveDimensionsByCompetenceId(long competenceId);

	public Dimension retrieveDimensionById(long id);

	public List<Dimension> retrieveDimensionByName(String name);

	public long addDimension(Dimension dimension);

	public void removeAllDimensions();

	/*
	 * Competences Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public ICompetenceRepository getCompetenceRepository();

	public List<Competence> retrieveAllCompetences();

	public List<Competence> retrieveCompetencesByCategoryId(long categoryId);

	public Competence retrieveCompetenceById(long id);

	public List<Competence> retrieveCompetenceByName(String name);

	public long addCompetence(Competence competence);

	public void removeAllCompetences();

}
