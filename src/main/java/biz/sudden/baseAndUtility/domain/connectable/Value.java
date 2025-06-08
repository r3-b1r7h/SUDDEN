package biz.sudden.baseAndUtility.domain.connectable;

/**
 * Value is a generic Interface for concrete Occurrence values
 * 
 * @author MN
 * 
 * @param <T>
 *            type of concrete value
 */
public interface Value<T> {

	public Long getId();

	public void setId(Long id);

	public T getValue();

	public void setValue(T value);
}
