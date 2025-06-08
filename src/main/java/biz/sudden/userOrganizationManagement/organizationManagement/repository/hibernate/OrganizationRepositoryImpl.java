package biz.sudden.userOrganizationManagement.organizationManagement.repository.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;

public class OrganizationRepositoryImpl extends
		GenericRepositoryImpl<Organization, Long> implements
		IOrganizationRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public OrganizationRepositoryImpl(Class<Organization> type) {
		super(type);
	}

	public OrganizationRepositoryImpl() {
		super(Organization.class);
	}

	@Override
	public void addNewOrganizationUser(String idOrganization, User2 user) {
		DetachedCriteria query = DetachedCriteria.forClass(Organization.class)
				.add(Property.forName("id").eq(idOrganization));
		List result = getHibernateTemplate().findByCriteria(query);
		if (result != null && result.size() > 0) {
			user
					.setCompanyId(((Organization) result.get(0)).getId()
							.toString());
			user.setCompany(((Organization) result.get(0)).getName());
			user.setAux_companyName(((Organization) result.get(0)).getName());
		}
	}

	@Override
	public Organization addOrganization(String aux_companyManager,
			String aux_countryCompany, String aux_numberEmployeeByCompany,
			String aux_userNameCompanyManager, int companyId, String aux_name) {
		// TODO Auto-generated method stub

		Organization organization = new Organization();
		organization.setAux_companyManager(aux_companyManager);
		organization.setAux_countryCompany(aux_countryCompany);
		organization
				.setAux_numberEmployeeByCompany(aux_numberEmployeeByCompany);
		organization.setAux_userNameCompanyManager(aux_userNameCompanyManager);
		organization.setName(aux_name);

		long id = create(organization);

		logger.info("ORGANIZATION");
		logger.info("id de organization: " + id);
		logger.info("name der organisation: " + organization.getName());
		logger.info("Id Company Manager: "
				+ organization.getAux_companyManager());
		logger.info("Id Country Company: "
				+ organization.getAux_countryCompany());
		logger.info("Number employee: "
				+ organization.getAux_numberEmployeeByCompany());
		logger.info("User Name company Manager: "
				+ organization.getAux_userNameCompanyManager());
		logger.info("**************");

		return organization;

	}

	@Override
	public List<User2> allOrganizationUsers(String idOrganization) {
		DetachedCriteria query = DetachedCriteria.forClass(User2.class).add(
				Property.forName("company").eq(idOrganization));
		return getHibernateTemplate().findByCriteria(query);
	}

	@Override
	public User2 getOrganizationManager(String idOrganization) {
		String manager = null;
		try {
			manager = retrieve(Long.parseLong(idOrganization))
					.getAux_companyManager();
		} catch (Exception ee) {
		}
		if (manager != null) {
			DetachedCriteria query = DetachedCriteria.forClass(User2.class)
					.add(Property.forName("id").eq(manager));
			List result = getHibernateTemplate().findByCriteria(query);
			if (result != null && result.size() > 0)
				return (User2) result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> retrieveOrganisationByName(String name) {
		DetachedCriteria query = DetachedCriteria.forClass(Organization.class)
				.add(Property.forName("name").eq(name));
		return getHibernateTemplate().findByCriteria(query);
	}

}
