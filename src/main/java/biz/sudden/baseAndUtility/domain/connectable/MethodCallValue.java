package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

/**
 * The Value is actually a reference to some method call result using java
 * -reflection.
 */

@javax.persistence.Entity
public class MethodCallValue implements Value<Object> {

	Logger logger = Logger.getLogger(this.getClass());

	private Long id;

	private String methodToCall;
	private Connectable objToUse;

	private Object[] callArguments;

	/**
	 * default constructor
	 */
	public MethodCallValue() {
	}

	public MethodCallValue(String methodToCall, Connectable objToUse,
			Object[] callArguments) {
		this.methodToCall = methodToCall;
		this.objToUse = objToUse;
		this.callArguments = callArguments;
	}

	/**
	 * get result of Call
	 * 
	 * @return String value
	 */
	@Override
	@Transient
	public Object getValue() {
		try {
			Class<?>[] methArgs = null;
			if (callArguments != null) {
				methArgs = new Class<?>[callArguments.length];
				for (int i = 0; i < callArguments.length; i++) {
					methArgs[i] = callArguments[i].getClass();
				}
			}
			return objToUse.getClass().getMethod(methodToCall, methArgs)
					.invoke(objToUse, callArguments);
		} catch (Exception e) {
			logger.error("getValue call did not work for: " + objToUse
					+ " for this method: " + methodToCall
					+ " with these args: " + callArguments
					+ e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * ignorred!!
	 * 
	 * @param value
	 *            String value
	 */
	@Override
	public void setValue(Object value) {
	}

	/**
	 * @return the Name of the methodToCall
	 * 
	 */
	public String getMethodToCall() {
		return methodToCall;
	}

	/**
	 * @param methodToCall
	 *            the Name of the methodToCall
	 */
	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

	/**
	 * @return the objToUse
	 */
	public Connectable getObjToUse() {
		return objToUse;
	}

	/**
	 * @param objToUse
	 *            the objToUse to set
	 */
	public void setObjToUse(Connectable objToUse) {
		this.objToUse = objToUse;
	}

	/**
	 * @return the callArguments
	 */
	public Object[] getCallArguments() {
		return callArguments;
	}

	/**
	 * @param callArguments
	 *            the callArguments to set
	 */
	public void setCallArguments(Object[] callArguments) {
		this.callArguments = callArguments;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
