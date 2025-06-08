package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;

public interface IRoleDimensionRepository {

	public long addRoleDimension(RoleDimension roleDimension);

	public List<RoleDimension> getAllRoleDimensions();

	public List<RoleDimension> getRoleDimensionsByDimensionId(long dimensionId);

	public void removeAllRoleDimensions();

}
