package biz.sudden.baseAndUtility.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

/**
 * ConnectableService defines methods to manage associations between concrete
 * Connectables
 */
public interface ConnectableService {

	// Elementary operations
	// ---------------------

	// operations 4 AssociationTypes
	// -----------------------------

	/**
	 * retrieve all (in the DB) defined Association types. Association types
	 * define the "nature" of a concrete Association between concrete
	 * Connectables
	 * 
	 * @return returns defined Association types, an empty set if none exist
	 */
	public List<AssocType> retrieveAssociationTypes();

	/**
	 * retrieve an Association type by String
	 * 
	 * @param assocType
	 *            String which represents the type of AssocType
	 * @return returns an AssocType Object, null if none exits
	 */
	public AssocType retrieveAssociationType(String assocType);

	/**
	 * delete an Association type. If associations exists, which reference the
	 * given Association type, it will not be deleted
	 * 
	 * @param assocType
	 *            Association type
	 * @param scope
	 *            TODO
	 * @return returns true if deleting has been successful
	 */
	public boolean deleteAssociationType(AssocType assocType, Scope scope);// TODO

	// :
	// update
	// doc

	// operations 4 AssociationRoleTypes
	// ---------------------------------

	/**
	 * retrieve all (in the DB) defined AssociationRole types. AssociationRole
	 * types define the "nature" of an association role.
	 * 
	 * @return returns defined AssociationRole types, en empty set if none exist
	 */
	public List<AssocRoleType> retrieveAssocRoleTypes();

	/**
	 * retrieve an AssociationRole type by String
	 * 
	 * @param assocRoleType
	 *            String which represents the type of AssocRoleType
	 * @return returns an AssocRoleType object, null if none exists
	 */
	public AssocRoleType retrieveAssocRoleType(String assocRoleType);

	/**
	 * delete an AssociationRole type. If AssociationRoles exists, which
	 * reference the given AssociationRole type, it will not be deleted
	 * 
	 * @param assocRoleType
	 *            AssociationRole type
	 * @return returns true if deleting has been successful
	 */
	public boolean deleteAssocRoleType(AssocRoleType assocRoleType);

	// operations 4 associating
	// ------------------------

	/**
	 * associate a set of Connectables with an Association of the given Type and
	 * in a certain scop, where the Connectables each take the role of the given
	 * AssociationRoleType. If the given AssociationType or an
	 * AssociationRoleType does not exist, it will be created. Note: at least 2
	 * Connectables have to be associated
	 * 
	 * @param assocType
	 *            defines the Association type
	 * @param connectables
	 *            HashMap where the key represents the AssociationRole type and
	 *            the Entry defines a List of Connectables which take the
	 *            respective AssociationRole type within the association
	 * @param scope
	 *            TODO
	 * @return returns the newly created Association, null if less than 2
	 *         Connectables are passed within parameter "connectables"
	 */
	public Association associate(String assocType,
			HashMap<String, List<Connectable>> connectables, Scope scope);

	public Association associate(AssocType assocType,
			HashMap<String, List<Connectable>> connectables, Scope scope);

	// TODO:doc
	public void associateTwoConnectables(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope);

	/**
	 * add a Connectable to an existing Association in the repository
	 * 
	 * @param a
	 *            Association which will be enhanced
	 * @param c
	 *            Connectable which will be linked via an AssociationRole to the
	 *            given Association
	 * @param assocRoleType
	 *            AssociationRole type of the given Connectable within the
	 *            Association
	 */
	public void addConnectableToAssociation(Association a, Connectable c,
			String assocRoleType);

	/**
	 * delete an Association between concrete Connectables.
	 * 
	 * @param a
	 *            the Association to be removed
	 */
	public void delete(Association a);

	// TODO: define methods which include SCOPE parameter

	// //TODO docu
	// public void deleteAssociation(Association a);

	// operations for finding associations and navigating along associations
	// ---------------------------------------------------------------------

	/**
	 * get an association by the given parameter
	 * 
	 * @param c1
	 * @param c2
	 * @param associationType
	 * @param roleTypeC1
	 * @param roleTypeC2
	 * @param scope
	 *            TODO
	 * @return Association for the given parameter, null if none exist, (if
	 *         multiple exist it returns the first association found)
	 */
	public Association getAssociation(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope);

	/**
	 * get associations by the given parameter
	 * 
	 * @param c1
	 * @param c2
	 * @param associationType
	 * @param roleTypeC1
	 * @param roleTypeC2
	 * @param scope
	 *            TODO
	 * @return Associations for the given parameter, an empty set if none exist
	 */
	public List<Association> getAssociations(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope);

	/**
	 * retrieve Associations of a given type from the repository
	 * 
	 * @param assocType
	 *            String which represents the Association type
	 * @param scope
	 *            TODO
	 * @return returns a List of Associations of the given Association type,
	 *         otherwise an empty list
	 */
	// public List<Association> retrieveAssociationsOfType(String assocType);
	public List<Association> retrieveAssociationsOfType(String assocType,
			Scope scope);

	/**
	 * retrieve Associations of a given type from the repository
	 * 
	 * @param assocType
	 *            AssocType
	 * @param scope
	 *            TODO
	 * @return returns a List of Associations of the given Association type,
	 *         otherwise an empty list
	 */
	// public List<Association> retrieveAssociationsOfType(AssocType assocType);
	public List<Association> retrieveAssociationsOfType(AssocType assocType,
			Scope scope);

	/**
	 * retrieve AssociationRoles of a concrete Connectable. AssociationRoles
	 * provide the link between an association and the given Connectable.
	 * 
	 * @param c
	 *            Connectable
	 * @param scope
	 *            TODO
	 * @return return a List of AssociationRoles where the given Connectable is
	 *         the "player", an empty list if none exists for the given
	 *         Connectable
	 */
	public List<AssociationRole> retrieveAssociationRolesOf(Connectable c,
			Scope scope);

	public List<AssociationRole> retrieveAssociationRole(Association a,
			Connectable c, String roleType);

	/**
	 * retrieve AssociationRoles by a given AssociationType
	 * 
	 * @param assocType
	 *            given AssociationType
	 * @param scope
	 *            TODO
	 * @return returns a List of AssociationRoles which belong to Associations
	 *         of the given AssociationType
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Scope scope);

	/**
	 * retrieve AssociationRoles by a given AssociationType and a given
	 * Connectable
	 * 
	 * @param assocType
	 *            given AssociationType
	 * @param c
	 *            given Connectable
	 * @param scope
	 *            TODO
	 * @return returns all AssociationRoles of existing associations of the
	 *         given type, whereas Connectable is a player within the
	 *         association
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, Scope scope);

	/**
	 * retrieve AssociationRoles by a given AssociationType, a given Connectable
	 * and the AssociationRole type which is taken by the Connectable within the
	 * association
	 * 
	 * @param assocType
	 *            given AssociationType
	 * @param c
	 *            given Connectable
	 * @param assocRoleTypePlayed
	 *            given AssociationRole type
	 * @param scope
	 *            TODO
	 * @return returns all AssociationRoles of existing associations of the
	 *         given type, whereas Connectable is a player within the
	 *         association and the AssociationRole is of the given type
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, String assocRoleTypePlayed,
			Scope scope);

	/**
	 * retrieve AssociatioRoles of a given association
	 * 
	 * @param a
	 *            given Association
	 * @return returns a List of AssociationRole
	 */
	public List<AssociationRole> retrieveAssocRoles(Association a);

	// operations 4 connectables
	// -------------------------

	/**
	 * get the type of a given Connectable
	 * 
	 * @param c
	 *            given Connectable
	 * @return a String which represents the type (ClassName) of a Connectable
	 */
	public String getTypeOf(Connectable c);

	/**
	 * retrieve all Associations in which the given Connectable is involved
	 * 
	 * @param c
	 *            given Connectable
	 * @param scope
	 *            TODO
	 * @return returns a Set of Associations where the given Connectable "plays
	 *         a role"
	 */
	public Set<Association> retrieveAssociationsOf(Connectable c, Scope scope);// TODO

	// :
	// update
	// doc

	/**
	 * retrieve associations for a given Connectable, which are of a certain
	 * Association type
	 * 
	 * @param assocType
	 *            AssociationType
	 * @param c
	 *            Connectable for which associations of the given type will be
	 *            searched
	 * @param scope
	 *            TODO
	 * @return a List of Associations
	 */
	public List<Association> retrieveAssociationsOfType(String assocType,
			Connectable c, Scope scope);

	/**
	 * retrieve an association by a given association type, a given connectable
	 * and the associationRole type which teh connectable plays within the
	 * association(s)
	 * 
	 * @param assocType
	 *            given association type
	 * @param c
	 *            given Connectable
	 * @param scope
	 *            TODO
	 * @param given
	 *            associationRole type for c
	 * @return a List of Associations according to the given parameter, an empty
	 *         list if none exist
	 */
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String roleType, Scope scope);// TODO

	// :
	// update
	// doc

	public List<Association> retrieveAssociationBy(Connectable c,
			String roleType, Scope scope);

	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String connectableRoleType,
			String counterpartRoleType, Scope scope);

	/**
	 * retrieve Connectables which take a certain AssociationRole type within a
	 * given Association
	 * 
	 * @param a
	 *            given Association
	 * @param roleType
	 *            given AssociationRole type
	 * @return return a Set of Connectables which take the given AssociationRole
	 *         type within the given Association, an empty set if none exist
	 */
	public List<Connectable> retrieveConnectablesInAssocRole(Association a,
			AssocRoleType roleType);

	/**
	 * retrieve counterpart Connectables of a given Connectable within a given
	 * Association.
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            AssociationRole type of the given Connectable within the
	 *            association
	 * @param searchedAssocRoleType
	 *            AssociationRole type of the counterpart Connectables
	 * @return returns a List of counterpart connectables, an empty set if none
	 *         exist for the given parameter
	 */
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType);

	/**
	 * retrieve counterpart Connectables of a given Connectable within a given
	 * Association.
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            AssociationRole type of the given Connectable within the
	 *            association
	 * @return returns a List of counterpart connectables, an empty set if none
	 *         exist for the given parameter
	 */
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType);

	/**
	 * retrieve counterpart Connectables of a given Connectable which is in a
	 * given role
	 * 
	 * @param givenCon
	 * @param givenAssocRoleType
	 *            name of the role the connectable uses
	 * @param scope
	 *            TODO
	 * @return
	 */
	public List<Connectable> retrieveCounterpartConnectables(
			Connectable givenCon, String givenAssocRoleType, Scope scope);

	/**
	 * retrieve counterpart Connectables of a given Connectable within a given
	 * Association. Note: if an Association contains the same Connectable
	 * multiple times, the Connectable will not be returned
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @return returns a Set of counterpart connectables, an empty set if none
	 *         exist for the given parameter
	 */
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon);

	/**
	 * retrieve counterpart AssociationRoles of a given Connectable within a
	 * given Association.
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            AssociationRole type of the given Connectable within the
	 *            association
	 * @param searchedAssocRoleType
	 *            AssociationRole type of the counterpart AssociationRoles
	 * @return returns a List of counterpart AssociationRoles, an empty set if
	 *         none exist for the given parameter
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType);

	/**
	 * retrieve counterpart AssociationRoles of a given Connectable within a
	 * given Association.
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            AssociationRole type of the given Connectable within the
	 *            association
	 * @return returns a List of counterpart AssociationRoles, an empty set if
	 *         none exist for the given parameter
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType);

	/**
	 * retrieve counterpart AssociationRoles of a given Connectable within a
	 * given Association. Note: if an Association contains the same Connectable
	 * multiple times, the AssociationRoles of the Connectable will not be
	 * returned
	 * 
	 * @param a
	 *            given Association
	 * @param givenCon
	 *            given Connectable
	 * @return returns a Set of counterpart AssociationRoles, an empty set if
	 *         none exist for the given parameter
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon);

	/**
	 * retrieve counterpart AssociationRoles of a given Connectable which is in
	 * a given role
	 * 
	 * @param givenCon
	 * @param givenAssocRoleType
	 *            name of the role the connectable uses
	 * @param scope
	 *            TODO
	 * @return
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Connectable givenCon, String givenAssocRoleType, Scope scope);

	/**
	 * remove a given Connectable from an Association. If only two Connectables
	 * are associated via the given Association it will be deleted
	 * 
	 * @param c
	 *            given Connectable
	 * @param a
	 *            given Association
	 * @return true if removing has been succesful
	 */
	public void removeConnectableFromAssoc(Connectable c, Association a);

	/**
	 * remove a given Connectable from an Association. If only two Connectables
	 * are associated via the given Association it will be deleted
	 * 
	 * @param c
	 *            given Connectable
	 * @param assocRoleType
	 *            AssociationRole type of the AssociationRole(s) which link c
	 *            and a
	 * @param a
	 *            given Association
	 * @return true if removing has been succesful
	 */
	public void removeConnectableFromAssoc(Connectable c, String assocRoleType,
			Association a);

	/**
	 * delete all Association of a Connectable. In cases where the given
	 * Connectable is just associated with exactly one other Connectable, the
	 * association will be deleted, otherwise it will just be removed from the
	 * association
	 * 
	 * @param c
	 *            given Connectable
	 * @param scope
	 *            TODO
	 */
	public void deleteConnectableAssocs(Connectable c, Scope scope);

	/**
	 * search all Connectables which are (directly and indirectly) associated
	 * with c1, whereas distance specifies the search distance within the graph
	 * 
	 * @param distance
	 *            search distance
	 * @param associationType
	 *            type of association
	 * @param c1
	 *            given Connectable for which associated Connectables will be
	 *            searches
	 * @param roleTypeC1
	 *            associationRole type of c1 within associations of the given
	 *            associationType
	 * @param roleTypeC2
	 *            associationRole type of c2 within associations of the given
	 *            associationType
	 * @param scope
	 *            TODO
	 * @return all Connectables which are associated with c1 according to the
	 *         given parameter, an empty set if none exist
	 */
	public Set<Connectable> directedSearch(int distance,
			String associationType, Connectable c1, String roleTypeC1,
			String roleTypeC2, Scope scope);

	/**
	 * search all Connectables which are (directly and indirectly) associated
	 * with c1
	 * 
	 * @param associationType
	 *            type of association
	 * @param c1
	 *            given Connectable for which associated Connectables will be
	 *            searches
	 * @param roleTypeC1
	 *            associationRole type of c1 within associations of the given
	 *            associationType
	 * @param roleTypeC2
	 *            associationRole type of c2 within associations of the given
	 *            associationType
	 * @param scope
	 *            TODO
	 * @return all Connectables which are associated with c1 according to the
	 *         given parameter, an empty set if none exist
	 */
	public Set<Connectable> directedSearchAll(String associationType,
			Connectable c1, String roleTypeC1, String roleTypeC2, Scope scope);

	/**
	 * search all Connectables which are directly associated with c1
	 * 
	 * @param associationType
	 *            type of association
	 * @param c1
	 *            given Connectable for which associated Connectables will be
	 *            searches
	 * @param roleTypeC1
	 *            associationRole type of c1 within associations of the given
	 *            associationType
	 * @param roleTypeC2
	 *            associationRole type of c2 within associations of the given
	 *            associationType
	 * @param scope
	 *            TODO
	 * @return all Connectables which are directly associated with c1, an empty
	 *         set if none exist
	 */
	public Set<Connectable> directedSearchNeighbor(String associationType,
			Connectable c1, String roleTypeC1, String roleTypeC2, Scope scope);

	// TODO: undirectedSearch - all, neighbor, distance

	/**
	 * add a Connectable c2 to the directed 1:n relation of c1.
	 * 
	 * @param c1
	 * @param c2
	 * @param associationType
	 *            associationType
	 * @param roleTypeC1
	 *            associationRole type of c1
	 * @param roleTypeC2
	 *            associationRole type of c2
	 * @param scope
	 *            TODO
	 */
	public void addToDirected1toNRelation(Connectable c1, Connectable c2,
			String associationType, String roleTypeC1, String roleTypeC2,
			Scope scope);

	/**
	 * add a Connectable c2 to the directed n:n relation where all
	 * associationRoles are of the same type.
	 * 
	 * @param c1
	 * @param c2
	 * @param associationType
	 *            associationType
	 * @param roleType
	 *            associationRole type of Connectables within an association
	 * @param scope
	 *            TODO
	 */
	public void addToNtoNAssociation(Connectable c1, Connectable c2,
			String associationType, String roleType, Scope scope);

	/**
	 * set n Elements of a 1:n relation
	 * 
	 * @param associationType
	 * @param c1
	 *            Connectable which is the root
	 * @param c1RoleType
	 *            associationRole type of c1
	 * @param c2
	 *            Connectables which represente the child elements in 1:n
	 *            relation
	 * @param c2RoleType
	 *            associationRole type of c2
	 * @param scope
	 *            TODO
	 */
	public void setNElementsOf1toNRelation(String associationType,
			Connectable c1, String c1RoleType, Set<Connectable> c2,
			String c2RoleType, Scope scope);

	/**
	 * remove a Connectable(c2) from associations of the given type, where the
	 * Connectable(c2) plays an associationRole of the given type(c2RoleType)
	 * and is associated with another Connectable(c1)
	 * 
	 * @param associationType
	 * @param c1
	 * @param c1RoleType
	 * @param c2
	 *            Connectable which will be removed
	 * @param c2RoleType
	 *            associationRole type of c2
	 * @param scope
	 *            TODO
	 * @return
	 */
	public boolean removeFromAssociations(String associationType,
			Connectable c1, String c1RoleType, Connectable c2,
			String c2RoleType, Scope scope);

	// Occurrence
	@SuppressWarnings("unchecked")
	public void addOccurrence(Connectable c, String occType, Value value,
			Scope scope);

	public void deleteOccurrence(Occurrence o);

	// public void deleteOccurrence(Occurrence o, Scope s);

	public List<Occurrence> retrieveOccurrences(Connectable c, Scope scope);

	public List<Occurrence> retrieveOccurrences(Connectable c, String occType,
			Scope scope);

	// e.g. use to set scope
	public void updateOccurrence(Occurrence o);

	// OccurType
	public void deleteOccurType(OccurType occurType);

	public List<OccurType> retrieveOccurTypes();

	public OccurType retrieveOccurTypeBy(String type);

	// Scope
	/**
	 * a service which holds the current user session and her currently selected
	 * scope this service should be set **automatically** by spring......!!
	 * don't call this method
	 */
	public void setScopeService(ScopeService userscope);

	/** the scope that sees it all (do not use for writing!)) */
	public Scope getRetrieveAllScope();

	/** the scope that has no particular meaning (try to limit its use) */
	public Scope getUnspecifiedScope();

	public Scope retrieveScopeBy(String name);

	public Scope retrieveScopeBy(Set<Connectable> context);

	public Scope createOrRetrieveScope(String scopeName);

	public List<Association> retrieveAssociationsFrom(Scope scope);

	public List<Occurrence> retrieveOccurrencesFrom(Scope scope);

	public void deleteScope(Scope scope);

	// public List<Statement> retrieveStatementsFrom(Scope scope);

	// Value
	public StringValue createOrRetrieveStringValue(String value);

	public StringValue retrieveStringValueBy(String value);

	public void deleteStringValue(String value);

	public Long createAssociationType(AssocType assocType);

	public AssocRoleType createAssocRoleType(String assocRoleType);

	public void deleteOccurTypeAndOccurrences(OccurType occurType);

	public void deleteConnectableAssocsOccurrences(Connectable conn, Scope sc);

	public void update(Occurrence o);

	public void update(Association a);

	public void update(AssociationRole a);

	public void update(AssocType a);

	public Association retrieveAssociation(Long id);
}
