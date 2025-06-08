package biz.sudden.baseAndUtility.web.controller.tree;

/**
 * helper class to search associations
 * 
 * @author MN
 * 
 */
public class SearchedAssoc {

	private String assocType;
	private String parentRoleType;
	private String childRoleType;

	public SearchedAssoc(String assocType, String parentRoleType,
			String childRoleType) {
		this.assocType = assocType;
		this.parentRoleType = parentRoleType;
		this.childRoleType = childRoleType;
	}

	public String getAssocType() {
		return assocType;
	}

	public void setAssocType(String assocType) {
		this.assocType = assocType;
	}

	public String getParentRoleType() {
		return parentRoleType;
	}

	public void setParentRoleType(String parentRoleType) {
		this.parentRoleType = parentRoleType;
	}

	public String getChildRoleType() {
		return childRoleType;
	}

	public void setChildRoleType(String childRoleType) {
		this.childRoleType = childRoleType;
	}

}
