package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;

public class CVIRepositoryImpl extends GenericRepositoryImpl<CVI, Long>
		implements ICVIRepository {

	public CVIRepositoryImpl() {
		super(CVI.class);
	}

	public CVIRepositoryImpl(Class<CVI> type) {
		super(CVI.class);
	}

	@Override
	public long addCVI(CVI cvi) {
		long id = create(cvi);
		cvi.setId(id);
		System.out.println("Adding CVI to repository...");
		System.out.println(cvi.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long addCVI(Long categoryId, String categoryName, String name,
			String description, float maxRange, float minRange,
			ArrayList<Tick> ticks) {
		CVI cvi = new CVI();
		cvi.setCategoryId(categoryId);
		cvi.setCategoryName(categoryName);
		cvi.setDescription(description);
		cvi.setMaxRange(maxRange);
		cvi.setMinRange(minRange);
		cvi.setName(name);
		cvi.setTicks(ticks);

		System.out.println("Adding CVI to repository...");
		System.out.println(cvi.toString());

		return create(cvi);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CVI> getAllCVIs() {
		return getHibernateTemplate().loadAll(CVI.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CVI getCVIById(long id) {
		List<CVI> listOfCVIs = getHibernateTemplate().find(
				"from CVI as c where c.id = ?", id);
		if (listOfCVIs.size() > 0) {
			return listOfCVIs.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CVI> getCVIsByCategoryId(Long categoryId) {
		return getHibernateTemplate().find(
				"from CVI as cvi where cvi.categoryId = ?", categoryId);
	}

	@Override
	public void removeAllCVIs() {
		List<CVI> cvi = getAllCVIs();
		getHibernateTemplate().deleteAll(cvi);
	}

}
