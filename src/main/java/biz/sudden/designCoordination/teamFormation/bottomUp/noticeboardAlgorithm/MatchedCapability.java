package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * 
 * This class is used to hold the data generated when a declared capability is
 * matched to a potential extension.
 * 
 * Hence it contains several bits of data: the potential extension, the declared
 * capability, the set of resources from the potential extension which the
 * declared capability has matched to - both forwards and backwards.
 * 
 * Note that this match is generated by the *process agents* rather than by the
 * noticeboard itself - this is to allow for non exact resource type matching.
 * 
 * @author mcassmc
 * 
 */

public class MatchedCapability {

	private PotentialExtension potentialExtension;
	private DeclaredCapability declaredCapabilityMatched;

	private LinkedList<ResourceSet> matchedResourcesForward;
	private LinkedList<ResourceSet> unmatchedResourcesForward;
	private LinkedList<ResourceSet> remainderForward;

	private LinkedList<ResourceSet> matchedResourcesBackwards;
	private LinkedList<ResourceSet> unmatchedResourcesBackwards;
	private LinkedList<ResourceSet> remainderBackwards;

	private Hashtable<ResourceSet, ResourceSet> resourcesToReplace;

	public MatchedCapability() {
		this.matchedResourcesBackwards = new LinkedList<ResourceSet>();
		this.matchedResourcesForward = new LinkedList<ResourceSet>();
		this.remainderBackwards = new LinkedList<ResourceSet>();
		this.remainderForward = new LinkedList<ResourceSet>();
		this.unmatchedResourcesBackwards = new LinkedList<ResourceSet>();
		this.unmatchedResourcesForward = new LinkedList<ResourceSet>();
		this.resourcesToReplace = new Hashtable<ResourceSet, ResourceSet>();
	}

	public void SetDeclaredCapabilityMatched(DeclaredCapability dc) {
		this.declaredCapabilityMatched = dc;
	}

	public DeclaredCapability getDeclaredCapabilityMatched() {
		return this.declaredCapabilityMatched;
	}

	public void setMatchedResourcesForward(LinkedList<ResourceSet> hm) {
		this.matchedResourcesForward = hm;
	}

	public Collection<ResourceSet> getMatchedResourcesForward() {
		return this.matchedResourcesForward;
	}

	public void setMatchedResourcesBackwards(LinkedList<ResourceSet> hm) {
		this.matchedResourcesBackwards = hm;
	}

	public Collection<ResourceSet> getMatchedResourcesBackwards() {
		return this.matchedResourcesBackwards;
	}

	public void setUnmatchedResourcesForward(LinkedList<ResourceSet> ll) {
		this.unmatchedResourcesForward = ll;
	}

	public Collection<ResourceSet> getUnmatchedResourcesForward() {
		return this.unmatchedResourcesForward;
	}

	public void setUnmatchedResourcesBackwards(LinkedList<ResourceSet> ll) {
		this.unmatchedResourcesBackwards = ll;
	}

	public Collection<ResourceSet> getUnmatchedResourcesBackwards() {
		return this.unmatchedResourcesBackwards;
	}

	public void setRemainderForward(Collection<ResourceSet> ll) {
		this.remainderForward = new LinkedList<ResourceSet>(ll);
	}

	public LinkedList getRemainderForward() {
		return this.remainderForward;
	}

	public void setRemainderBackwards(Collection<ResourceSet> ll) {
		this.remainderBackwards = new LinkedList<ResourceSet>(ll);
	}

	public LinkedList getRemainderBackwards() {
		return this.remainderBackwards;
	}

	public void setPotentialExtension(PotentialExtension pe) {
		this.potentialExtension = pe;
	}

	public PotentialExtension getPotentialExtension() {
		return this.potentialExtension;
	}

	/**
	 * Returns either the replacement resource set for a resource set, or null
	 * if the replacement is the same as the resource set itself - ie no changes
	 * are required.
	 * 
	 * @param r
	 * @return
	 */
	public ResourceSet getReplacementResource(ResourceSet r) {
		int resourceSetID = r.getID();
		ResourceSet toReturn = null;

		for (ResourceSet rr : this.resourcesToReplace.keySet()) {
			if (rr.getID() == resourceSetID) {
				toReturn = this.resourcesToReplace.get(rr);
			}
		}

		System.out.println(" Returning replacement resource for " + r + " as "
				+ toReturn);

		if ((toReturn == null) || toReturn.equals(r)) {
			return null;
		} else {
			return toReturn;
		}

	}

	public boolean hasReplacementResources() {
		return (this.resourcesToReplace.size() != 0);
	}

	public void addReplacementResource(ResourceSet toReplace,
			ResourceSet replacement) {
		this.resourcesToReplace.put(toReplace, replacement);
	}

	@Override
	public String toString() {
		String result = "";

		result += " Result of matching declared capability "
				+ declaredCapabilityMatched;
		result += " to potential extension " + potentialExtension;
		result += " Going Forwards";
		result += " types matched " + matchedResourcesForward;
		result += " forwards remainder " + remainderForward;
		result += " things not needed from the potential extension "
				+ unmatchedResourcesForward;
		result += " Going Backwards";
		result += " types matched " + matchedResourcesBackwards;
		result += " backwards remainder " + remainderBackwards;
		result += " things not needed from the potential extension "
				+ unmatchedResourcesBackwards;

		return result;
	}
}
