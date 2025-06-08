package biz.sudden.userOrganizationManagement.organizationManagement.service;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationProfileRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.service.IUser;

public interface IOrganization {

	// Organization now extends the actor class and many people use the name of
	// the actor
	public void setAux_name(String name);

	// Organization now extends the actor class and many people use the name of
	// the actor
	public String getAux_name();

	IUser getOrganizationManager(String idOrganization);

	void addNewOrganizationUser(User2 user);

	ArrayList<IUser> allOrganizationUsers();

	public String getAux_companyManager();

	public void setAux_companyManager(String aux_companyManager);

	public String getAux_countryCompany();

	public void setAux_countryCompany(String aux_countryCompany);

	public String getAux_numberEmployeeByCompany();

	public void setAux_numberEmployeeByCompany(
			String aux_numberEmployeeByCompany);

	public String getAux_userNameCompanyManager();

	public void setAux_userNameCompanyManager(String aux_userNameCompanyManager);

	public int getCompanyId();

	public void setCompanyId(int companyId);

	// ------

	public Organization addOrganization(String aux_companyManager,
			String aux_countryCompany, String aux_numberEmployeeByCompany,
			String aux_userNameCompanyManager, int companyId, String aux_name);

	public List<Organization> retrieveAll();

	public Organization retrieveOrganization(Long id);

	public List<OrganizationProfile> retrieveAllOrganizationProfiles();

	public OrganizationProfile retrieveOrganizationProfile(Long id);

	void setIOrganizationRepository(
			IOrganizationRepository organizationsRepository);

	void setIOrganizationProfileRepository(
			IOrganizationProfileRepository organizationsProfileRepository);

	public Supplier retrieveSupplier(Long id);

	public void setGenericRepository(SuddenGenericRepository rep);

	public SuddenGenericRepository getGenericRepository();

	public void deleteOrganization(Organization s);

	public void deleteSupplier(Supplier s);

	public void updateCreateOrganization(Organization s);

	public void updateCreateSupplier(Supplier s);
}
