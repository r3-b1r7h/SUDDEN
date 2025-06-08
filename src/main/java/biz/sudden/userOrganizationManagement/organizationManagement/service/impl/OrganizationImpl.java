package biz.sudden.userOrganizationManagement.organizationManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationProfileRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.service.IUser;

public class OrganizationImpl implements IOrganization {

	private String aux_Name;
	private String aux_companyManager;
	private String aux_countryCompany;
	private String aux_numberEmployeeByCompany;
	private String aux_userNameCompanyManager;
	private int companyId;

	private IOrganizationRepository iOrganizationRepository;
	private IOrganizationProfileRepository iOrganizationProfileRepository;
	private SuddenGenericRepository genericRepository;

	@Override
	public void addNewOrganizationUser(User2 user) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<IUser> allOrganizationUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAux_companyManager() {
		// TODO Auto-generated method stub
		return aux_companyManager;
	}

	@Override
	public String getAux_countryCompany() {
		// TODO Auto-generated method stub
		return aux_countryCompany;
	}

	@Override
	public String getAux_numberEmployeeByCompany() {
		// TODO Auto-generated method stub
		return aux_numberEmployeeByCompany;
	}

	@Override
	public String getAux_userNameCompanyManager() {
		// TODO Auto-generated method stub
		return aux_userNameCompanyManager;
	}

	@Override
	public int getCompanyId() {
		// TODO Auto-generated method stub
		return companyId;
	}

	@Override
	public IUser getOrganizationManager(String idOrganization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAux_name() {
		// TODO Auto-generated method stub
		return aux_Name;
	}

	@Override
	public void setAux_companyManager(String aux_companyManager) {
		// TODO Auto-generated method stub
		this.aux_companyManager = aux_companyManager;
	}

	@Override
	public void setAux_countryCompany(String aux_countryCompany) {
		// TODO Auto-generated method stub
		this.aux_countryCompany = aux_countryCompany;
	}

	@Override
	public void setAux_numberEmployeeByCompany(
			String aux_numberEmployeeByCompany) {
		// TODO Auto-generated method stub
		this.aux_numberEmployeeByCompany = aux_numberEmployeeByCompany;
	}

	@Override
	public void setAux_userNameCompanyManager(String aux_userNameCompanyManager) {
		// TODO Auto-generated method stub
		this.aux_userNameCompanyManager = aux_userNameCompanyManager;
	}

	@Override
	public void setCompanyId(int companyId) {
		// TODO Auto-generated method stub
		this.companyId = companyId;
	}

	@Override
	public void setAux_name(String name) {
		// TODO Auto-generated method stub
		aux_Name = name;
	}

	@Override
	public Organization addOrganization(String aux_companyManager,
			String aux_countryCompany, String aux_numberEmployeeByCompany,
			String aux_userNameCompanyManager, int companyId, String aux_name) {
		// TODO Auto-generated method stub
		return iOrganizationRepository.addOrganization(aux_companyManager,
				aux_countryCompany, aux_numberEmployeeByCompany,
				aux_userNameCompanyManager, companyId, aux_name);
	}

	@Override
	public void setIOrganizationRepository(
			IOrganizationRepository organizationsRepository) {
		iOrganizationRepository = organizationsRepository;
	}

	@Override
	public void setIOrganizationProfileRepository(
			IOrganizationProfileRepository organizationsProfileRepository) {
		iOrganizationProfileRepository = organizationsProfileRepository;
	}

	/**
	 * retrieve all existing Organisations from the repository
	 */
	@Override
	public List<Organization> retrieveAll() {
		return iOrganizationRepository.retrieveAll();
	}

	@Override
	public Organization retrieveOrganization(Long id) {
		return iOrganizationRepository.retrieve(id);
	}

	@Override
	public List<OrganizationProfile> retrieveAllOrganizationProfiles() {
		return iOrganizationProfileRepository.retrieveAll();
	}

	@Override
	public OrganizationProfile retrieveOrganizationProfile(Long id) {
		return iOrganizationProfileRepository.retrieve(id);
	}

	@Override
	public Supplier retrieveSupplier(Long id) {
		return genericRepository.retrieveByTypeAndId(Supplier.class, id);
	}

	@Override
	public void deleteOrganization(Organization s) {
		genericRepository.delete(s);
	}

	@Override
	public void deleteSupplier(Supplier s) {
		genericRepository.delete(s);
	}

	@Override
	public void updateCreateOrganization(Organization s) {
		if (s.getId() == null)
			genericRepository.create(s);
		else
			genericRepository.update(s);
	}

	@Override
	public void updateCreateSupplier(Supplier s) {
		if (s.getId() == null)
			genericRepository.create(s);
		else
			genericRepository.update(s);
	}

	@Override
	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

}
