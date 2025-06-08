package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public class Material extends Item {
	public static String ASSOCIATIONTYPE = "submaterialCategories";
	public static String SUPER = "parent";
	public static String SUB = "child";

	public static final String materialAssocType = "requiresMaterial";
	public static final String productRoleType = "product";// 1
	public static final String materialRoleType = "material";// n

}
