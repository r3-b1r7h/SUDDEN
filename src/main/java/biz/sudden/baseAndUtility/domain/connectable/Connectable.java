package biz.sudden.baseAndUtility.domain.connectable;

/**
 * Connectable represents a marker interface for classes which can be associated
 * via AssociationRole and Association respectively
 * 
 * Note: if you write a class which implements Connectable, extend
 * package-info.java (in biz.sudden directory) -> the extension is needed to
 * ensure accurate functionality for associating Connectables. Moreover annotate
 * getId() with "@Id @GeneratedValue(strategy = GenerationType.AUTO)"
 * 
 * @author Matthias Neubauer
 */
// @MappedSuperclass
public interface Connectable {

	public Long getId();

	public void setId(Long id);

}
