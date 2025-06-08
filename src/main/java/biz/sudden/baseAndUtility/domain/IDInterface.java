package biz.sudden.baseAndUtility.domain;

/**
 * 
 * @author Matthias Neubauer & Thomas Feiner
 * 
 *         This interface should be implemented from all domain objects to
 *         ensure, that each domain object has a getter/setter for hibernate ID
 * 
 */

public interface IDInterface {

	public Long getId();

	public void setId(Long id);
}
