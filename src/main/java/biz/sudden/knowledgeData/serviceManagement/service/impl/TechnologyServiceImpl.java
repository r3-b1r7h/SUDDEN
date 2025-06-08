package biz.sudden.knowledgeData.serviceManagement.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.knowledgeData.serviceManagement.Util.Util;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.repository.TechnologyRepository;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;

public class TechnologyServiceImpl implements TechnologyService {

	private TechnologyRepository techRep;
	private ConnectableService connectableService;

	// variables for super sub relation between technologies
	public static final String superSubAssocType = Technology.ASSOCIATIONTYPE;
	public static final String superRoleType = Technology.SUPER;
	public static final String subRoleType = Technology.SUB;

	// variables for material technology association
	public static final String techAssocType = "technologyRealizesMaterial";
	public static final String materialRoleType = "material";
	public static final String techRoleType = "technology";

	/**
	 * set TechnologyRepository
	 * 
	 * @param techRep
	 */
	public void setTechRep(TechnologyRepository techRep) {
		this.techRep = techRep;
	}

	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public void createTechnology(Technology technology) {
		techRep.create(technology);
	}

	@Override
	public void deleteTechnology(Technology technology) {
		connectableService.deleteConnectableAssocs(technology,
				connectableService.getRetrieveAllScope());
		techRep.delete(technology);
	}

	@Override
	public List<Technology> retrieveAllTechnologies() {
		return techRep.retrieveAll();
	}

	@Override
	public Technology retrieveTechnology(Long id) {
		return techRep.retrieve(id);
	}

	@Override
	public Technology retrieveTechnology(String name) {
		return techRep.retrieveTechnologyBy(name);
	}

	@Override
	public void updateTechnology(Technology technology) {
		techRep.update(technology);
	}

	// Further methods to create hierarchy
	@Override
	public void addSub(Technology technology, Set<Technology> subTechnologies,
			Scope scope) {
		Iterator<Technology> i = subTechnologies.iterator();
		while (i.hasNext()) {
			connectableService.addToDirected1toNRelation(technology, i.next(),
					superSubAssocType, superRoleType, subRoleType, scope);
		}
	}

	@Override
	public void addSub(Technology technology, Technology subTechnology,
			Scope scope) {
		connectableService.addToDirected1toNRelation(technology, subTechnology,
				superSubAssocType, superRoleType, subRoleType, scope);
	}

	@Override
	public Set<Technology> retrieveSub(Technology technology, Scope scope) {
		return Util.getTechnologies(connectableService.directedSearchNeighbor(
				superSubAssocType, technology, superRoleType, subRoleType,
				scope));
	}

	@Override
	public Set<Technology> retrieveAllSub(Technology technology, Scope scope) {
		return Util.getTechnologies(connectableService.directedSearchAll(
				superSubAssocType, technology, superRoleType, subRoleType,
				scope));
	}

	@Override
	public Technology retrieveSuper(Technology technology, Scope scope) {
		Set<Technology> ps = Util.getTechnologies(connectableService
				.directedSearchNeighbor(superSubAssocType, technology,
						subRoleType, superRoleType, scope));
		if (ps.size() > 1 || ps.isEmpty())
			return null;
		return ps.iterator().next();
	}

	@Override
	public boolean removeSub(Technology technology, Technology subTechnology,
			Scope scope) {
		return connectableService.removeFromAssociations(superSubAssocType,
				technology, superRoleType, subTechnology, subRoleType, scope);
	}

	@Override
	public void setSub(Process technology, Set<Technology> subTechnologies,
			Scope scope) {
		Set<Connectable> cSubs = new HashSet<Connectable>();
		cSubs.addAll(subTechnologies);
		connectableService.setNElementsOf1toNRelation(superSubAssocType,
				technology, superRoleType, cSubs, subRoleType, scope);
	}

	@Override
	public void addTechnology(Material material, Technology technology,
			Scope scope) {
		connectableService.addToDirected1toNRelation(material, technology,
				techAssocType, materialRoleType, techRoleType, scope);
	}

	@Override
	public Set<Technology> retrieveTechnologies(Material material, Scope scope) {
		return Util
				.getTechnologies(connectableService.directedSearchNeighbor(
						techAssocType, material, materialRoleType,
						techRoleType, scope));
	}

	@Override
	public Set<Material> retrieveMaterials(Technology tech, Scope scope) {
		return Util.getMaterials(connectableService.directedSearchNeighbor(
				techAssocType, tech, techRoleType, materialRoleType, scope));
	}

}
