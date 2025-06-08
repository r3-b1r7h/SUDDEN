package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * Note: Technology is similar to Kurts understanding of MaterialProcessing,
 * meaning the how Material is treated to produce a certain Product.
 * 
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public class Technology extends Thing {

	public static String ASSOCIATIONTYPE = "superSub";
	public static String SUPER = "super";
	public static String SUB = "sub";

	public Technology() {
	}

	public Technology(String name) {
		super.setName(name);
	}

}
