package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface LinkRepository extends GenericRepository<Link, Long> {

	public List<Link> getLinkFor(Object object, Long id);

}
