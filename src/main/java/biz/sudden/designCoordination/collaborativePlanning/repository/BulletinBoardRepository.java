package biz.sudden.designCoordination.collaborativePlanning.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;

public interface BulletinBoardRepository extends
		GenericRepository<BulletinBoard, Long> {

	public List<BulletinBoard> retrieveAll();

	public List<BulletinBoard> retrieveAllOfType(Class type);
}
