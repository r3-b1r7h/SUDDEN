package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;

public interface ITickRepository extends GenericRepository<Tick, Long> {

	public long addTick(Tick tick);

	public List<Tick> getAllTicks();

	public Tick getTickById(long id);

	public void removeAllTicks();

}
