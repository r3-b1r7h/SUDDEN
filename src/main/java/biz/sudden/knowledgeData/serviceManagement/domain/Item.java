package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public abstract class Item extends Thing {

	public static final String isAAssocType = "isA";
	public static final String isASuperType = "super";// 1
	public static final String isASubType = "sub";// n

}
