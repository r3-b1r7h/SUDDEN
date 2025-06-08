package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;

public class CompetenceRepositoryImpl extends
		GenericRepositoryImpl<Competence, Long> implements
		ICompetenceRepository {

	public CompetenceRepositoryImpl() {
		super(Competence.class);
	}

	public CompetenceRepositoryImpl(Class<Competence> type) {
		super(Competence.class);
	}

	@Override
	public long addCompetence(Competence competence) {
		long id = create(competence);
		competence.setId(id);
		System.out.println("Adding Competence to repository...");
		System.out.println(competence.toString());

		return id;
	}

	@Override
	public long addCompetence(long categoryId, String categoryName,
			String name, String description, String eText, String qText,
			ArrayList<Dimension> dimensions) {
		Competence competence = new Competence();
		competence.setCategoryId(categoryId);
		competence.setCategoryName(categoryName);
		competence.setName(name);
		competence.setDescription(description);
		competence.setEText(eText);

		long id = create(competence);
		competence.setId(id);
		System.out.println("Adding Competence to repository...");
		System.out.println(competence.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Competence> getAllCompetences() {
		return getHibernateTemplate().loadAll(Competence.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Competence getCompetenceById(long id) {
		List<Competence> listOfCompetences = getHibernateTemplate().find(
				"from Competence as com where com.id = ?", id);
		if (listOfCompetences.size() > 0) {
			return listOfCompetences.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Competence> getCompetenceByName(String name) {
		return this.retrieveAllByFieldNameContains("name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Competence> getCompetencesByCategoryId(long categoryId) {
		return getHibernateTemplate().find(
				"from Competence as com where com.categoryId = ?", categoryId);
	}

	@Override
	public void removeAllCompetences() {
		List<Competence> competences = getAllCompetences();
		getHibernateTemplate().deleteAll(competences);
	}

}
