package biz.sudden.userOrganizationManagement.organizationManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;

public interface IOrganizationRepository extends
		GenericRepository<Organization, Long> {

	public User2 getOrganizationManager(String idOrganization);

	public void addNewOrganizationUser(String idOrganization, User2 user);

	public List<User2> allOrganizationUsers(String idOrganization);

	// ------

	public Organization addOrganization(String aux_companyManager,
			String aux_countryCompany, String aux_numberEmployeeByCompany,
			String aux_userNameCompanyManager, int companyId, String aux_name);

	/**
	 * retrieve a Organisation from Repository by name
	 * 
	 * @param name
	 *            name of the Organisation
	 * @return a List of Organisations which have the given name, an empty List
	 *         if none exist
	 */
	public List<Organization> retrieveOrganisationByName(String name);

}
