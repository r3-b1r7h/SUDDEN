package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.MappedSuperclass;

/*
 * discarded
 */
@MappedSuperclass
public class BaseConnectable implements Connectable {
	private Long id;

	// private Set<Occurrence> occurrences;

	// @Override
	// public void addOccurrence(Occurrence o) {
	// if (occurrences == null)
	// occurrences = new HashSet<Occurrence>();
	// occurrences.add(o);
	// o.setParent(this);
	// }
	//
	@Override
	public Long getId() {
		return id;
	}

	//
	// @Override
	// public Set<Occurrence> getOccurrences() {
	// return occurrences;
	// }
	//
	// @Override
	// public void removeOccurrence(Occurrence o) {
	// occurrences.remove(o);
	// }
	//
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	//
	// @Override
	// public void setOccurrences(Set<Occurrence> occurs) {
	// this.occurrences=occurs;
	// }

}
