package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;

public class DimensionRepositoryImpl extends
		GenericRepositoryImpl<Dimension, Long> implements IDimensionRepository {

	public DimensionRepositoryImpl() {
		super(Dimension.class);
	}

	public DimensionRepositoryImpl(Class<Dimension> type) {
		super(Dimension.class);
	}

	@Override
	public long addDimension(Dimension dimension) {
		long id = create(dimension);
		dimension.setId(id);
		System.out.println("Adding Dimension to repository...");
		System.out.println(dimension.toString());

		return id;
	}

	@Override
	public long addDimension(long categoryId, String categoryName, String name,
			String description, String eText, String qText, CVI cvi) {
		Dimension dimension = new Dimension();
		dimension.setCategoryId(categoryId);
		dimension.setCategoryName(categoryName);
		dimension.setCvi(cvi);
		dimension.setName(name);
		dimension.setDescription(description);
		dimension.setEText(eText);

		long id = create(dimension);
		dimension.setId(id);
		System.out.println("Adding Dimension to repository...");
		System.out.println(dimension.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dimension> getAllDimensions() {
		return getHibernateTemplate().loadAll(Dimension.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dimension getDimensionById(long id) {
		List<Dimension> listOfDimensions = getHibernateTemplate().find(
				"from Dimension as dim where dim.id = ?", id);
		if (listOfDimensions.size() > 0) {
			return listOfDimensions.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Dimension> getDimensionByName(String name) {
		return this.retrieveAllByFieldNameContains("name", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dimension> getDimensionsByCategoryId(long categoryId) {
		return getHibernateTemplate().find(
				"from Dimension as dim where dim.categoryId = ?", categoryId);
	}

	@Override
	public void removeAllDimensions() {
		List<Dimension> dimensions = getAllDimensions();
		getHibernateTemplate().deleteAll(dimensions);
	}

}
