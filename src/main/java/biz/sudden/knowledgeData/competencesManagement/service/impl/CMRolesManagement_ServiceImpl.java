package biz.sudden.knowledgeData.competencesManagement.service.impl;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleCompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleRepository;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMRolesManagement_Service;

public class CMRolesManagement_ServiceImpl extends
		CMServiceCategoryBaseClassImpl implements ICMRolesManagement_Service {

	private ICMCompetencesManagement_Service cmCompetencesManagement_Service;
	private IRoleCompetenceRepository roleCompetenceRepository;
	private IRoleDimensionRepository roleDimensionRepository;
	private IRoleQuestionnaireRepository roleQuestionnaireRepository;
	private IRoleRepository roleRepository;

	@Override
	public long addCategory(Category category) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.addCategory(category);
	}

	@Override
	public long addRole(Role role) {
		for (RoleCompetence roleCompetence : role.getRoleQuestionnaire()
				.getRoleCompetences()) {
			for (RoleDimension roleDimension : roleCompetence
					.getRoleDimensions()) {
				roleDimensionRepository.addRoleDimension(roleDimension);
			}
			roleCompetenceRepository.addRoleCompetence(roleCompetence);
		}
		roleQuestionnaireRepository.addRoleQuestionnaire(role
				.getRoleQuestionnaire());
		return roleRepository.addRole(role);
	}

	@Override
	public void applyNonWeightedRole(QuestionnaireInstance questionnaireInstance) {
		if (questionnaireInstance != null) {
			questionnaireInstance.setWeight(1);
			List<CompetenceInstance> competenceInstances = questionnaireInstance
					.getCompetenceInstances();
			for (int c0 = 0; c0 < competenceInstances.size(); c0++) {
				CompetenceInstance competenceInstance = competenceInstances
						.get(c0);
				List<DimensionInstance> dimensionInstances = competenceInstance
						.getDimensionInstances();
				for (int c1 = 0; c1 < dimensionInstances.size(); c1++) {
					DimensionInstance dimensionInstance = dimensionInstances
							.get(c1);
					dimensionInstance.setWeight(1);
					dimensionInstance.calculateValue();
				}
				competenceInstance.setWeight(1);
				competenceInstance.calculateValue();
			}
			questionnaireInstance.calculateValue();
		}
	}

	@Override
	public void applyRole(Role role, QuestionnaireInstance questionnaireInstance) {
		if (questionnaireInstance != null && role != null) {
			questionnaireInstance.setWeight(role.getRoleQuestionnaire()
					.getWeight());
			List<RoleCompetence> roleCompetences = role.getRoleQuestionnaire()
					.getRoleCompetences();
			List<CompetenceInstance> competenceInstances = questionnaireInstance
					.getCompetenceInstances();
			for (int c0 = 0; c0 < roleCompetences.size(); c0++) {
				CompetenceInstance competenceInstance = competenceInstances
						.get(c0);
				RoleCompetence roleCompetence = roleCompetences.get(c0);
				List<RoleDimension> roleDimensions = roleCompetence
						.getRoleDimensions();
				List<DimensionInstance> dimensionInstances = competenceInstance
						.getDimensionInstances();
				for (int c1 = 0; c1 < roleDimensions.size(); c1++) {
					DimensionInstance dimensionInstance = dimensionInstances
							.get(c1);
					RoleDimension roleDimension = roleDimensions.get(c1);
					dimensionInstance.setWeight(roleDimension.getWeight());
					dimensionInstance.calculateValue();
				}
				competenceInstance.setWeight(roleCompetence.getWeight());
				competenceInstance.calculateValue();
			}
			questionnaireInstance.calculateValue();
		}
	}

	@Override
	public List<Category> retrieveAllCategories() {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getAllCategories();
	}

	@Override
	public List<Role> retrieveAllRoles() {
		return roleRepository.getAllRoles();
	}

	@Override
	public List<Category> retrieveCategoriesByParentId(long parentCategoryId,
			int type) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getCategoriesByParentId(parentCategoryId, type);
	}

	@Override
	public List<Category> retrieveCategoriesByType(int type) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getCategoriesByType(type);
	}

	@Override
	public ICategoryRepository getCategoryRepository() {
		return cmCompetencesManagement_Service.getCategoryRepository();
	}

	public ICMCompetencesManagement_Service getCmCompetencesManagement_Service() {
		return cmCompetencesManagement_Service;
	}

	@Override
	public Role retrieveRoleById(long id) {
		return roleRepository.getRolesById(id).get(0);
	}

	@Override
	public IRoleCompetenceRepository getRoleCompetenceRepository() {
		return roleCompetenceRepository;
	}

	@Override
	public IRoleDimensionRepository getRoleDimensionRepository() {
		return roleDimensionRepository;
	}

	@Override
	public IRoleQuestionnaireRepository getRoleQuestionnaireRepository() {
		return roleQuestionnaireRepository;
	}

	@Override
	public IRoleRepository getRoleRepository() {
		return roleRepository;
	}

	@Override
	public List<Role> retrieveRolesByCategoryId(long categoryId) {
		return roleRepository.getRolesByCategoryId(categoryId);
	}

	@Override
	public List<Role> retrieveRolesByQuestionnaireId(long questionnaireId) {
		return roleRepository.getRolesByQuestionnaireId(questionnaireId);
	}

	@Override
	public void removeAllCategories() {
		cmCompetencesManagement_Service.getCategoryRepository()
				.removeAllCategories();
	}

	@Override
	public void removeAllRoles() {
		roleDimensionRepository.removeAllRoleDimensions();
		roleCompetenceRepository.removeAllRoleCompetences();
		roleQuestionnaireRepository.removeAllRoleQuestionnaires();
		roleRepository.removeAllRoles();
	}

	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service) {
		this.cmCompetencesManagement_Service = cmCompetencesManagement_Service;
	}

	public void setRoleCompetenceRepository(
			IRoleCompetenceRepository roleCompetenceRepository) {
		this.roleCompetenceRepository = roleCompetenceRepository;
	}

	public void setRoleDimensionRepository(
			IRoleDimensionRepository roleDimensionRepository) {
		this.roleDimensionRepository = roleDimensionRepository;
	}

	public void setRoleQuestionnaireRepository(
			IRoleQuestionnaireRepository roleQuestionnaireRepository) {
		this.roleQuestionnaireRepository = roleQuestionnaireRepository;
	}

	public void setRoleRepository(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

}
