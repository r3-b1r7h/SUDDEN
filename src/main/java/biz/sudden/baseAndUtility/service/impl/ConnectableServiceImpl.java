package biz.sudden.baseAndUtility.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.domain.connectable.Value;
import biz.sudden.baseAndUtility.repository.AssocRoleTypeRepository;
import biz.sudden.baseAndUtility.repository.AssocTypeRepository;
import biz.sudden.baseAndUtility.repository.AssociationRepository;
import biz.sudden.baseAndUtility.repository.AssociationRoleRepository;
import biz.sudden.baseAndUtility.repository.OccurTypeRepository;
import biz.sudden.baseAndUtility.repository.OccurrenceRepository;
import biz.sudden.baseAndUtility.repository.StringValueRepository;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ScopeService;

public class ConnectableServiceImpl implements ConnectableService {

	private Logger logger = Logger.getLogger(this.getClass());

	// repositories
	private AssociationRepository assocRep;
	private AssocTypeRepository assocTypeRep;
	private AssociationRoleRepository assocRoleRep;
	private AssocRoleTypeRepository assocRoleTypeRep;
	private OccurTypeRepository occurTypeRep;
	private OccurrenceRepository occurRep;
	private StringValueRepository stringValueRep;
	private ScopeService scopeService;

	public ConnectableServiceImpl() {
		logger.debug("ConnectableServiceImpl -> cst");
	}

	@Override
	public Association associate(String assocType,
			HashMap<String, List<Connectable>> connectables, Scope scope) {
		return associate(createOrRetrieveAssocType(assocType), connectables,
				scope);
		//		
		//		
		// if(connectables.values().size()<2 &&
		// !(connectables.keySet().size()==1 &&
		// connectables.get(connectables.keySet().iterator().next()).size()<2)){
		// logger.debug(
		// "ERROR: ConnectableServiceImpl.associate - at least 2 Connectables are needed within an association!"
		// );
		// return null;
		// }
		// Association a = new Association();
		// a.setType(getAssocType(assocType));
		// a.setScope(scope);
		// assocRep.create(a);
		//		
		// Iterator<String> kI = connectables.keySet().iterator();
		// while(kI.hasNext()){
		// String key = kI.next();
		// AssocRoleType aRT = getAssocRoleType(key);
		// Iterator<Connectable> cI = connectables.get(key).iterator();
		// while(cI.hasNext()){//Create AssociationRoles
		// assocRoleRep.create(new AssociationRole(aRT, cI.next(), a));
		// }
		// }
		// //
		// logger.debug("method associate in ConnectableService - Scope: "+scope
		// );
		// return a;
	}

	// FIXME: Performance !! this is slow
	@Override
	public Association associate(AssocType assocType,
			HashMap<String, List<Connectable>> connectables, Scope scope) {
		if (connectables.values().size() < 2
				&& !(connectables.keySet().size() == 1 && connectables.get(
						connectables.keySet().iterator().next()).size() < 2)) {
			logger
					.debug("ERROR: ConnectableServiceImpl.associate - at least 2 Connectables are needed within an association!");
			return null;
		}
		Association a = new Association();
		a.setType(assocType);
		a.setScope(scope);
		assocRep.create(a);

		for (Entry<String, List<Connectable>> entry : connectables.entrySet()) {
			String key = entry.getKey();
			List<Connectable> connectableList = entry.getValue();
			AssocRoleType aRT = getAssocRoleType(entry.getKey());
			for (Connectable connectable : connectableList) {
				// Create AssociationRoles
				if (connectable == null) {
					logger.debug("null");
				}

				logger.debug(aRT + " " + connectable + " " + a);
				assocRoleRep.create(new AssociationRole(aRT, connectable, a));
			}
		}

		// for (String key : connectables.keySet()) {
		// AssocRoleType aRT = getAssocRoleType(key);
		// for (Connectable connectable : connectables.get(key)) {
		// // Create AssociationRoles
		// if (connectable == null) {
		// logger.debug("null");
		// }
		//
		// logger.debug(aRT + " " + connectable+" "+a);
		// assocRoleRep.create(new AssociationRole(aRT, connectable, a));
		// }
		// }
		// logger.debug("method associate in ConnectableService - Scope: "+scope)
		// ;
		return a;
	}

	@Override
	public void addConnectableToAssociation(Association a, Connectable c,
			String assocRoleType) {
		// check if association exits within repository
		if (assocRep.retrieve(a.getId()) == null) {
			logger
					.debug("ERROR in ConnectableServiceImpl.addConnectableToAssociation: Association does not exist in repository!");
			return;
		}
		// create new AssociationRole with c and association
		assocRoleRep.create(new AssociationRole(
				getAssocRoleType(assocRoleType), c, a));
	}

	/**
	 * get an Association type. If none exists with the given name, a new
	 * AssocType will be created in the Repository
	 * 
	 * @param type
	 *            String which represents the type of the Association type
	 * @return AssocType for the given String
	 */
	private AssocType createOrRetrieveAssocType(String type) {
		AssocType newAT;
		List<AssocType> aTs = assocTypeRep.retrieveAssocTypeBy(type);
		if (aTs.isEmpty()) {
			newAT = new AssocType(type);
			assocTypeRep.create(newAT);
		} else {
			newAT = aTs.iterator().next();
		}
		return newAT;
	}

	/**
	 * get an AssociationRole type. If none exists with the given name, a new
	 * AssocRoleType will be created in the Repository
	 * 
	 * @param type
	 *            String which represents the AssociationRole type
	 * @return AssocRoleType for the given String
	 */
	private AssocRoleType getAssocRoleType(String type) {
		AssocRoleType newART;
		List<AssocRoleType> aRTs = assocRoleTypeRep
				.retrieveAssocRoleTypeBy(type);
		if (aRTs.isEmpty()) {
			newART = new AssocRoleType(type);
			assocRoleTypeRep.create(newART);
		} else {
			newART = aRTs.iterator().next();
		}
		return newART;
	}

	@Override
	public void delete(Association a) {
		deleteAssociationRoles(a);
		assocRep.delete(a);
	}

	/**
	 * delete AssociationRoles from given Association in the repository
	 * 
	 * @param a
	 *            given Association
	 */
	private void deleteAssociationRoles(Association a) {
		// changed 31.7
		for (AssociationRole assocRole : assocRep
				.retrieveAssociationRolesLazy(a)) {
			assocRoleRep.delete(assocRole);
		}
	}

	@Override
	public Association retrieveAssociation(Long id) {
		return assocRep.retrieve(id);
	}

	@Override
	public List<Association> retrieveAssociationsOfType(String assocType,
			Scope scope) {
		return assocRep.retrieveAssocsOfType(assocType, scope);
	}

	@Override
	public List<Association> retrieveAssociationsOfType(AssocType assocType,
			Scope scope) {
		return assocRep.retrieveAssocsOfType(assocType, scope);
	}

	@Override
	public List<AssociationRole> retrieveAssocRoles(Association a) {
		return assocRep.retrieveAssociationRoles(a);
	}

	@Override
	public Set<Association> retrieveAssociationsOf(Connectable c, Scope scope) {
		HashSet<Association> assocs = new HashSet<Association>();
		for (AssociationRole assocRole : assocRoleRep.retrieveRolesPlayedOf(c,
				scope)) {
			assocs.add(assocRole.getParent());
		}
		return assocs;
	}

	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Scope scope) {
		return assocRoleRep.retrieveAssociationRolesBy(assocType, scope);
	}

	/**
	 * FIXME not implemented currently
	 * {@link ConnectableServiceImpl#deleteAssocRoleType(AssocType) }
	 */
	@Override
	public boolean deleteAssocRoleType(AssocRoleType assocRoleType) {
		//
		// if(assocRoleRep.retrieveAssociationRolesBy(assocRoleType).isEmpty()){/
		// /muss fuer alle Assocs retrieved werden
		// this.assocRoleTypeRep.delete(assocRoleType);
		// return true;
		// }
		return false;
	}

	@Override
	public boolean deleteAssociationType(AssocType assocType, Scope scope) {
		if (assocRep.retrieveAssocsOfType(assocType, scope).isEmpty()) {
			this.assocTypeRep.delete(assocType);
			return true;
		}
		return false;

	}

	@Override
	// @Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteConnectableAssocs(Connectable connectable, Scope scope) {
		for (Association association : this.retrieveAssociationsOf(connectable,
				scope)) {
			if (isOneToOne(association)) {
				this.delete(association);
			} else {
				try {
					this.removeConnectableFromAssoc(connectable, association);
				} catch (Exception e) {
					delete(association);
				}
			}
		}

	}

	@Override
	public void deleteConnectableAssocsOccurrences(Connectable conn, Scope sc) {
		deleteConnectableAssocs(conn, sc);
		for (Occurrence o : retrieveOccurrences(conn, sc)) {
			deleteOccurrence(o);
		}
	}

	/**
	 * checks if an association is a 1:1 association
	 * 
	 * @param a
	 *            given Association
	 * @return true if it a 1:1 Association
	 */
	private boolean isOneToOne(Association a) {
		if (assocRep.retrieveAssociationRoles(a).size() == 2)
			return true;
		return false;
	}

	@Override
	public String getTypeOf(Connectable c) {
		return c.getClass().getName();
	}

	@Override
	public void removeConnectableFromAssoc(Connectable c, Association a) {
		List<AssociationRole> aRL = assocRoleRep.retrieveAssociationRolesBy(c,
				a);
		deleteAssociationRoleFromAssoc(a, aRL);
	}

	@Override
	public void removeConnectableFromAssoc(Connectable c, String assocRoleType,
			Association a) {
		List<AssociationRole> aRL = assocRoleRep.retrieveAssociationRolesBy(c,
				assocRoleType, a);
		deleteAssociationRoleFromAssoc(a, aRL);
	}

	private void deleteAssociationRoleFromAssoc(Association a,
			List<AssociationRole> aRL) {
		if (aRL.isEmpty()) {
			return;
		} else if (aRL.size() > 1) {
			return;
		} else {
			if (isOneToOne(a))
				delete(a);
			else
				assocRoleRep.delete(aRL.get(0));
		}

	}

	public void removeAssociationRoleFromAssoc(AssociationRole ar, Association a) {
		List<AssociationRole> aRL = assocRoleRep.retrieveAssociationRolesBy(a,
				ar);
		deleteAssociationRoleFromAssoc(a, aRL);
	}

	@Override
	public AssocRoleType retrieveAssocRoleType(String assocRoleType) {
		List<AssocRoleType> artL = this.assocRoleTypeRep
				.retrieveAssocRoleTypeBy(assocRoleType);
		if (artL.isEmpty())
			return null;
		return this.assocRoleTypeRep.retrieveAssocRoleTypeBy(assocRoleType)
				.iterator().next();
	}

	@Override
	public AssocRoleType createAssocRoleType(String assocRoleType) {
		AssocRoleType d = new AssocRoleType(assocRoleType);
		this.assocRoleTypeRep.create(d);
		return d;
	}

	@Override
	public List<AssocRoleType> retrieveAssocRoleTypes() {
		return this.assocRoleTypeRep.retrieveAssocRoleTypes();
	}

	@Override
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String roleType, Scope scope) {
		return this.assocRep.retrieveAssociationBy(assocType, c, roleType,
				scope);
	}

	@Override
	public List<Association> retrieveAssociationBy(Connectable c,
			String roleType, Scope scope) {
		return this.assocRep.retrieveAssociationBy(c, roleType, scope);
	}

	@Override
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String connectableRoleType, String neighborRoleType,
			Scope scope) {
		return this.assocRep.retrieveAssociationBy(assocType, c,
				connectableRoleType, neighborRoleType, scope);
	}

	@Override
	public List<AssociationRole> retrieveAssociationRolesOf(Connectable c,
			Scope scope) {
		List<AssociationRole> result = null;
		try {
			result = assocRoleRep.retrieveRolesPlayedOf(c, scope);
		} catch (org.hibernate.ObjectNotFoundException onfe) {
			this.logger
					.error("ConnectableServiceImpl.retrieveAssociationRolesOf: "
							+ c.getClass().getSimpleName());
			this.logger.error(onfe.getMessage());
		}
		return result;
	}

	@Override
	public List<AssociationRole> retrieveAssociationRole(Association a,
			Connectable c, String roleType) {
		return assocRoleRep.retrieveAssociationRolesBy(c, roleType, a);
	}

	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, Scope scope) {
		return this.assocRoleRep
				.retrieveAssociationRolesBy(assocType, c, scope);
	}

	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, String assocRoleTypePlayed,
			Scope scope) {
		return assocRoleRep.retrieveAssociationRolesBy(assocType, c,
				assocRoleTypePlayed, scope);
	}

	@Override
	public AssocType retrieveAssociationType(String assocType) {
		AssocType newAT = null;
		List<AssocType> aTs = assocTypeRep.retrieveAssocTypeBy(assocType);
		if (!aTs.isEmpty())
			newAT = aTs.iterator().next();
		return newAT;
	}

	@Override
	public List<AssocType> retrieveAssociationTypes() {
		return this.assocTypeRep.retrieveAssocTypes();
	}

	@Override
	public List<Association> retrieveAssociationsOfType(String assocType,
			Connectable c, Scope scope) {
		return assocRoleRep.retrieveAssociationsOfType(assocType, c, scope);
	}

	@Override
	public List<Connectable> retrieveConnectablesInAssocRole(Association a,
			AssocRoleType roleType) {
		return assocRoleRep.retrieveConnectablesInAssocRole(a, roleType);
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType) {
		return assocRoleRep.retrieveCounterpartConnectables(a, givenCon,
				givenAssocRoleType, searchedAssocRoleType);
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType) {
		return assocRoleRep.retrieveCounterpartConnectables(a, givenCon,
				givenAssocRoleType);
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(
			Connectable givenCon, String givenAssocRoleType, Scope scope) {
		return assocRoleRep.retrieveCounterpartConnectables(givenCon,
				givenAssocRoleType, scope);
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon) {
		return assocRoleRep.retrieveCounterpartConnectables(a, givenCon);
	}

	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType) {
		return assocRoleRep.retrieveCounterpartAssociationRoles(a, givenCon,
				givenAssocRoleType, searchedAssocRoleType);
	}

	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType) {
		return assocRoleRep.retrieveCounterpartAssociationRoles(a, givenCon,
				givenAssocRoleType);
	}

	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon) {
		return assocRoleRep.retrieveCounterpartAssociationRoles(a, givenCon);
	}

	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Connectable givenCon, String givenAssocRoleType, Scope scope) {
		return assocRoleRep.retrieveCounterpartAssociationRoles(givenCon,
				givenAssocRoleType, scope);
	}

	// add an association where roleTypeC1 occurs 1x and roleTypeC2 occurs
	// multiple times (1:N relation)
	// rename: addToDirected1toNRelation
	@Override
	public void addToDirected1toNRelation(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope) {
		if (getAssociation(c1, c2, associationType, roleTypeC1, roleTypeC2,
				scope) != null)
			return;
		// check if param c1 already has an association of given type -> Yes
		// extend
		List<Association> aL = retrieveAssociationBy(associationType, c1,
				roleTypeC1, scope);
		if (!aL.isEmpty()) {
			logger.debug("association already exists!!");
			addConnectableToAssociation(aL.get(0), c2, roleTypeC2);
			return;
		}
		// association does not exist -> associate Connectables with a new
		// association of the given associationType
		logger.debug("association does not exist new one will be created!!");
		associateTwoConnectables(c1, c2, associationType, roleTypeC1,
				roleTypeC2, scope);
	}

	// method for associations where just one assocRoleType occurs (i.e
	// alternative)
	// rename addTODirectedNtoNRelation
	@Override
	public void addToNtoNAssociation(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, Scope scope) {
		if (getAssociation(c1, c2, associationType, roleTypeC1, roleTypeC1,
				scope) != null)
			return;

		// check if param c1 already has an association of given type relation
		// -> Yes extend
		List<Association> aL = retrieveAssociationBy(associationType, c1,
				roleTypeC1, scope);
		if (aL != null && !aL.isEmpty()) {
			for (Association a : aL)
				// TODO: is this to be done really for all assocs found??
				addConnectableToAssociation(a, c2, roleTypeC1);
			return;
		}
		// check if param c2 already has an association of given type relation
		// -> Yes extend
		aL = retrieveAssociationBy(associationType, c2, roleTypeC1, scope);
		if (aL != null && !aL.isEmpty()) {
			for (Association a : aL)
				// TODO: is this to be done really for all assocs found??
				addConnectableToAssociation(a, c1, roleTypeC1);
			return;
		}

		// association does not exist -> associate Items within given
		// associationType
		associateTwoConnectables(c1, c2, associationType, roleTypeC1,
				roleTypeC1, scope);
	}

	@Override
	public Association getAssociation(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope) {
		Association existingAssoc = null;
		Association curA;
		Iterator<Association> i = retrieveAssociationsOfType(associationType,
				c1, scope).iterator();
		while (i.hasNext()) {
			curA = i.next();
			Connectable curC;
			Iterator<Connectable> cI = retrieveCounterpartConnectables(curA,
					c1, roleTypeC1, roleTypeC2).iterator();
			while (cI.hasNext()) {
				curC = cI.next();
				if (curC.getId().equals(c2.getId())
						&& curC.getClass().getCanonicalName().startsWith(
								c2.getClass().getCanonicalName())) {
					existingAssoc = curA;
					return existingAssoc;
				}
			}
		}
		return existingAssoc;
	}

	@Override
	public List<Association> getAssociations(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope) {
		List<Association> existingAssoc = new ArrayList<Association>();
		for (Association currentAssociation : retrieveAssociationsOfType(
				associationType, c1, scope)) {
			for (Connectable currentConnectable : retrieveCounterpartConnectables(
					currentAssociation, c1, roleTypeC1, roleTypeC2)) {
				if (currentConnectable.getId().equals(c2.getId())
						&& currentConnectable.getClass().getCanonicalName()
								.startsWith(c2.getClass().getCanonicalName()))
					existingAssoc.add(currentAssociation);
			}

		}

		return existingAssoc;
	}

	@Override
	public void associateTwoConnectables(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope) {
		HashMap<String, List<Connectable>> connectables = new HashMap<String, List<Connectable>>();
		List<Connectable> c1L = new ArrayList<Connectable>();
		c1L.add(c1);
		List<Connectable> c2L = new ArrayList<Connectable>();
		c2L.add(c2);

		connectables.put(roleTypeC1, c1L);
		connectables.put(roleTypeC2, c2L);
		associate(associationType, connectables, scope);
	}

	@Override
	public Set<Connectable> directedSearch(int distance,
			String associationType, Connectable c1, String roleTypeC1,
			String roleTypeC2, Scope scope) {
		Set<Connectable> returnSet = new HashSet<Connectable>();
		if (distance == 0)
			return returnSet;

		Set<Connectable> c2RoleTypes = directedSearchNeighbor(associationType,
				c1, roleTypeC1, roleTypeC2, scope);

		for (Connectable connectable : c2RoleTypes) {
			if (distance == 0)
				break;
			else if (distance == -1)
				returnSet.addAll(directedSearch(-1, associationType,
						connectable, roleTypeC1, roleTypeC2, scope));
			else
				returnSet.addAll(directedSearch(distance - 1, associationType,
						connectable, roleTypeC1, roleTypeC2, scope));
		}

		returnSet.addAll(c2RoleTypes);
		return returnSet;
	}

	@Override
	public Set<Connectable> directedSearchAll(String associationType,
			Connectable c1, String roleTypeC1, String roleTypeC2, Scope scope) {
		Set<Connectable> returnSet = new HashSet<Connectable>();
		Set<Connectable> c2RoleTypes = directedSearchNeighbor(associationType,
				c1, roleTypeC1, roleTypeC2, scope);
		for (Connectable connectable : c2RoleTypes) {
			returnSet.addAll(directedSearchAll(associationType, connectable,
					roleTypeC1, roleTypeC2, scope));
		}
		returnSet.addAll(c2RoleTypes);
		return returnSet;
	}

	@Override
	public Set<Connectable> directedSearchNeighbor(String associationType,
			Connectable c1, String roleTypeC1, String roleTypeC2, Scope scope) {
		Set<Connectable> returnSet = new HashSet<Connectable>();

		List<Association> assocs = assocRep.retrieveAssociationBy(
				associationType, c1, roleTypeC1, scope);
		for (Association assoc : assocs) {
			returnSet.addAll(retrieveCounterpartConnectables(assoc, c1,
					roleTypeC1, roleTypeC2));
		}
		return returnSet;
	}

	@Override
	public void setNElementsOf1toNRelation(String associationType,
			Connectable c1, String c1RoleType, Set<Connectable> c2,
			String c2RoleType, Scope scope) {
		List<Association> assocs = retrieveAssociationBy(associationType, c1,
				c1RoleType, scope);

		if ((c2 == null) || (c2.isEmpty())) {
			if (assocs != null && !assocs.isEmpty())
				for (Association assoc : assocs)
					delete(assoc);// assumed that just one exists!!
			return;
		} else if (assocs != null && !assocs.isEmpty()) {
			// remove association and make new one = Bad Code TODO: write code
			// which evaluates existing association and
			// edits this association according to param input
			for (Association assoc : assocs)
				delete(assoc);// assumed that just one exists!!
		}
		HashMap<String, List<Connectable>> connectables = new HashMap<String, List<Connectable>>();
		List<Connectable> c1L = new ArrayList<Connectable>();
		c1L.add(c1);
		List<Connectable> c2L = new ArrayList<Connectable>();
		c2L.addAll(c2);
		connectables.put(c1RoleType, c1L);
		connectables.put(c2RoleType, c2L);
		associate(associationType, connectables, scope);
	}

	// TODO review set & remove and check set 4 alternatives(associations with
	// where AssociationRoles are all of the same
	// role type)

	@Override
	public boolean removeFromAssociations(String associationType,
			Connectable c1, String c1RoleType, Connectable c2,
			String c2RoleType, Scope scope) {
		// check if input relation between items already exists
		// Association existingAssoc = getAssociation(c1, c2, associationType,
		// c1RoleType, c2RoleType);
		// if(existingAssoc == null)
		// return false;
		// removeConnectableFromAssoc(c2, c2RoleType, existingAssoc);
		// return true;
		List<Association> existingAssoc = getAssociations(c1, c2,
				associationType, c1RoleType, c2RoleType, scope);

		if (existingAssoc.isEmpty())
			return false;

		for (Association association : existingAssoc) {
			removeConnectableFromAssoc(c2, c2RoleType, association);
		}

		return true;
	}

	// @Override
	// public void deleteAssociation(Association a){
	// assocRep.delete(a);
	// }

	// Occurrence
	// ----------

	@SuppressWarnings("unchecked")
	@Override
	public void addOccurrence(Connectable c, String occType, Value value,
			Scope scope) {
		OccurType oT;
		List<OccurType> oTL = occurTypeRep.retrieveOccurTypeBy(occType);
		if (oTL.isEmpty()) {
			oT = new OccurType(occType);
			occurTypeRep.create(oT);
		} else {
			oT = oTL.get(0);
		}
		Occurrence o = new Occurrence(c, oT, value);
		o.setScope(scope);
		occurRep.create(o);
	}

	@Override
	public void deleteOccurrence(Occurrence o) {
		occurRep.delete(o);
	}

	@Override
	public List<Occurrence> retrieveOccurrences(Connectable c, Scope scope) {
		if (c != null)
			return occurRep.retrieveOccurrences(c, scope);
		else
			return null;
	}

	@Override
	public List<Occurrence> retrieveOccurrences(Connectable c, String occType,
			Scope scope) {
		return occurRep.retrieveOccurrences(c, occType, scope);
	}

	// TODO: setOccurrenceScope! also 4 Association
	@Override
	public void updateOccurrence(Occurrence o) {
		occurRep.update(o);
	}

	// public void setOccurrenceScope(Occurrence o, Scope s){
	// o.setScope(s);
	// occurRep.update(o);
	// }

	// OccurType
	@Override
	public void deleteOccurType(OccurType occurType) {
		occurTypeRep.delete(occurType);
	}

	@Override
	public void deleteOccurTypeAndOccurrences(OccurType occurType) {
		for (Occurrence deleteMe : occurRep.retrieveOccurrencesBy(occurType)) {
			occurRep.delete(deleteMe);
		}
	}

	@Override
	public List<OccurType> retrieveOccurTypes() {
		return occurTypeRep.retrieveOccurTypes();
	}

	@Override
	public OccurType retrieveOccurTypeBy(String type) {
		return occurTypeRep.retrieveOccurTypeBy(type).get(0);
	}

	// Scope
	@Override
	public void setScopeService(ScopeService session) {
		logger.debug("ConnectableServiceImpl.setScopeService");
		this.scopeService = session;
	}

	@Override
	public Scope createOrRetrieveScope(String scopeName) {
		return scopeService.createOrRetrieveScope(scopeName);
	}

	@Override
	public Scope getRetrieveAllScope() {
		return scopeService.getRetrieveAllScope();
	}

	@Override
	public Scope getUnspecifiedScope() {
		return scopeService.getUnspecifiedScope();
	}

	@Override
	public Scope retrieveScopeBy(String name) {
		return scopeService.retrieveScopeBy(name);
	}

	@Override
	public Scope retrieveScopeBy(Set<Connectable> context) {
		return scopeService.retrieveScopeBy(context);
	}

	@Override
	public void deleteScope(Scope deleteme) {
		scopeService.deleteScope(deleteme);
	}

	@Override
	public List<Association> retrieveAssociationsFrom(Scope scope) {
		return scopeService.retrieveAssociationsFrom(scope);
	}

	@Override
	public List<Occurrence> retrieveOccurrencesFrom(Scope scope) {
		return scopeService.retrieveOccurrencesFrom(scope);
	}

	// @Override
	// public List<Statement> retrieveStatementsFrom(Scope scope){
	// return scopeRep.retrieveStatementsFrom(scope);
	// }

	// addtoContext, removefromContext, setName

	@Override
	public StringValue createOrRetrieveStringValue(String value) {
		List<StringValue> svL = stringValueRep.retrieveStringBy(value);
		if (svL.isEmpty()) {
			StringValue result = new StringValue(value);
			stringValueRep.create(result);
			return result;
		} else {
			// logger.debug("StringValue " + value + " already existing! size: "
			// + svL.size());
			return svL.get(0);
		}
	}

	// @Override
	// public StringValue createStringValue(String value) {
	// StringValue result = new StringValue(value);
	// stringValueRep.create(result);
	// return result;
	// }

	@Override
	public StringValue retrieveStringValueBy(String value) {
		List<StringValue> svL = stringValueRep.retrieveStringBy(value);
		if (svL.isEmpty())
			return null;
		// logger.debug("Found multiple StringValues for " + value + " !: " +
		// svL.size());
		return svL.get(0);
	}

	// does not check references to Occurrences!!
	@Override
	public void deleteStringValue(String value) {
		StringValue sV = this.retrieveStringValueBy(value);
		// TODO: case in which value references an Occurrence??
		if (sV != null)
			stringValueRep.delete(sV);
	}

	// setter methods 4 class variables

	public void setAssocRep(AssociationRepository assocRep) {
		this.assocRep = assocRep;
	}

	public void setAssocTypeRep(AssocTypeRepository assocTypeRep) {
		this.assocTypeRep = assocTypeRep;
	}

	public void setAssocRoleRep(AssociationRoleRepository assocRoleRep) {
		this.assocRoleRep = assocRoleRep;
	}

	public void setAssocRoleTypeRep(AssocRoleTypeRepository assocRoleTypeRep) {
		this.assocRoleTypeRep = assocRoleTypeRep;
	}

	public void setOccurTypeRep(OccurTypeRepository occurTypeRep) {
		this.occurTypeRep = occurTypeRep;
	}

	public void setOccurRep(OccurrenceRepository occurRep) {
		this.occurRep = occurRep;
	}

	public void setStringValueRep(StringValueRepository stringValueRep) {
		this.stringValueRep = stringValueRep;
	}

	@Override
	public Long createAssociationType(AssocType assocType) {
		// TODO Auto-generated method stub
		return this.assocTypeRep.create(assocType);
	}

	@Override
	public void update(Occurrence o) {
		this.occurRep.update(o);
	}

	@Override
	public void update(Association a) {
		this.assocRep.update(a);
	}

	@Override
	public void update(AssociationRole a) {
		this.assocRoleRep.update(a);
	}

	@Override
	public void update(AssocType a) {
		this.assocTypeRep.update(a);
	}

}
