package biz.sudden.baseAndUtility.repository;

import java.util.List;
import java.util.Map;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface CaseFileRepository extends GenericRepository<CaseFile, Long> {

	public List<CaseFile> retrieveAll();

	public List<CaseFile> retrieveByKeyword(String nameKW, String sundryinfoKW);

	public List<CaseFile> retrieveAllCompleted();

	public CaseFile retrieve(BusinessOpportunity bo);

	public Map<Long, String> retrieveAllCaseFileNames();

}
