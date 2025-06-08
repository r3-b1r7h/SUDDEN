package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public abstract class Product extends Item {

	public static String ASSOCIATIONTYPE = "part";
	public static String SUPER = "whole";
	public static String SUB = "part";

}
