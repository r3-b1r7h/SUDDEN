package biz.sudden.knowledgeData.serviceManagement.service;

import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;

public interface TechnologyService {

	public void createTechnology(Technology technology);

	public Technology retrieveTechnology(Long id);

	public Technology retrieveTechnology(String name);

	public List<Technology> retrieveAllTechnologies();

	public void updateTechnology(Technology technology);

	public void deleteTechnology(Technology technology);

	// further methods 4 hierarchie
	public void addSub(Technology technology, Set<Technology> subTechnologies,
			Scope scope);

	public void addSub(Technology technology, Technology subTechnology,
			Scope scope);

	public Set<Technology> retrieveSub(Technology technology, Scope scope);

	public Set<Technology> retrieveAllSub(Technology technology, Scope scope);

	public Technology retrieveSuper(Technology technology, Scope scope);

	public boolean removeSub(Technology technology, Technology subTechnology,
			Scope scope);

	public void setSub(Process technology, Set<Technology> subTechnologies,
			Scope scope);

	// methods for associating materials and Technologies

	public void addTechnology(Material material, Technology technology,
			Scope scope);

	public Set<Technology> retrieveTechnologies(Material material, Scope scope);

	public Set<Material> retrieveMaterials(Technology tech, Scope scope);

}
