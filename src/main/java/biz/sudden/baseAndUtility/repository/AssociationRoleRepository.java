package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * AssociationRoleReposotory defines the repository interface for
 * AssociationRoles
 * 
 * @author Matthias Neubauer
 * 
 */
public interface AssociationRoleRepository extends
		GenericRepository<AssociationRole, Long> {

	// finder methods

	/**
	 * get AssociationRoles of a given Connectable (that means where Connectable
	 * is a player within the AssociationRole)
	 * 
	 * @param c
	 *            given Connectable
	 * @return a List of AssociationRoles where Connectable c is a player, an
	 *         empty List if none exist
	 */
	// public List<AssociationRole> retrieveRolesPlayedOf(Connectable c);
	public List<AssociationRole> retrieveRolesPlayedOf(Connectable c,
			Scope scope);

	/**
	 * retrieve AssociationRoles from the repository by a given AssociationType
	 * 
	 * @param assocType
	 *            given AssociationType
	 * @return returns all AssociationRoles of existing associations of the
	 *         given type
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(AssocType assocType);

	/**
	 * retrieve AssociationRoles from the repository by a given AssociationType
	 * and a given Connectable
	 * 
	 * @param assocType
	 *            given AssociationType
	 * @param c
	 *            given Connectable
	 * @return returns all AssociationRoles of existing associations of the
	 *         given type, whereas Connectable is a player within the
	 *         association
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, Scope scope);// TODO update doc

	/**
	 * retrieve Connectables which play a given role (type) within a given
	 * association
	 * 
	 * @param a
	 *            given Association
	 * @param roleType
	 *            roleType of searched Connectables
	 * @return a Set of Connectables which take the given role within the given
	 *         Association, an empty set if none exist
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
	 * @return returns a Set of counterpart connectables, an empty set if none
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
	 * @return returns a Set of counterpart connectables, an empty set if none
	 *         exist for the given parameter
	 */
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType);

	/**
	 * retrieve counterpart Connectables of a given Connectable within a given
	 * Association.
	 * 
	 * Note: if an Association contains the same Connectable multiple times, the
	 * Connectable will not be returned
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
	 * retrieve counterpart Connectables of a given Connectable within a given
	 * scope where only the role type name of the connectable is known
	 * 
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            given Connectable's role type name
	 * @param scope
	 *            the current scope which determines if a certain association is
	 *            visible or not.
	 * @return returns a Set of counterpart connectables, an empty set if none
	 *         exist for the given parameter
	 */
	public List<Connectable> retrieveCounterpartConnectables(
			Connectable givenCon, String givenAssocRoleType, Scope scope);

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
	 * @return returns a Set of counterpart AssociationRoles, an empty set if
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
	 * @return returns a Set of counterpart AssociationRoles, an empty set if
	 *         none exist for the given parameter
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType);

	/**
	 * retrieve counterpart AssociationRoles of a given Connectable within a
	 * given Association.
	 * 
	 * Note: if an Association contains the same Connectable multiple times, the
	 * Connectable (in its roles) will not be returned
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
	 * retrieve counterpart roles of a given Connectable within a given scope
	 * where only the role type name of the connectable is known
	 * 
	 * @param givenCon
	 *            given Connectable
	 * @param givenAssocRoleType
	 *            given Connectable's role type name
	 * @param scope
	 *            the current scope which determines if a certain association is
	 *            visible or not (and hence the roles)
	 * @return returns a Set of counterpart AssociationRoles, an empty set if
	 *         none exist for the given parameter
	 */
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Connectable givenCon, String givenAssocRoleType, Scope scope);

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
	 *            the current scope which determes if a certain association is
	 *            visible (and hence the visibility of the roles)
	 * @return a List of Associations
	 */
	public List<Association> retrieveAssociationsOfType(String assocType,
			Connectable c, Scope scope);

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
	 *            the current scope which determes if a certain association is
	 *            visible (and hence the visibility of the roles)
	 * @return returns all AssociationRoles of existing associations of the
	 *         given type, whereas Connectable is a player within the
	 *         association and the AssociationRole is of the given type
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, String assocRoleTypePlayed,
			Scope scope);

	/**
	 * retrieve AssociatioRoles by a given AssociationRole type
	 * 
	 * @param assocRoleType
	 *            given AssociationRole type
	 * @param scope
	 *            the current scope which determes if a certain association is
	 *            visible (and hence the visibility of the roles)
	 * @return a List of AssociationRoles which reference the given
	 *         AssociationRole type
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Scope scope);

	/**
	 * retrieve AssociatioRoles by a given AssociationRole type
	 * 
	 * @param assocRoleTypeName
	 *            given AssociationRole type
	 * @param scope
	 *            the current scope which determes if a certain association is
	 *            visible (and hence the visibility of the roles)
	 * @return a List of AssociationRoles which reference the given
	 *         AssociationRole type
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(
			String assocTypeName, Scope scope);

	/**
	 * retrieve AssociationRoles by a given Connectable and a given Association.
	 * 
	 * @param c
	 *            given Connectable
	 * @param a
	 *            given Association
	 * @return a List of AssociationRoles which belong to the given Association
	 *         and have a reference to the given player, an empty list if none
	 *         exist
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(Connectable c,
			Association a);

	/**
	 * retrieve AssociationRoles by a given Connectable, its role type and a
	 * given Association.
	 * 
	 * @param c
	 *            given Connectable
	 * @param arType
	 *            AssociationRole type of the AssociationRole which links c and
	 *            a
	 * @param a
	 *            given Association
	 * @return a List of AssociationRoles which belong to the given Association
	 *         and have a reference to the given player, an empty list if none
	 *         exist
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(Connectable c,
			String arType, Association a);

	/**
	 * retrieve AssociationRoles by a given Association and a given Role
	 * 
	 * @param a
	 *            given Association
	 * @param ar
	 *            given AssociationRole
	 * @return returns in fact the given AssociationRole if the given
	 *         Association contains it, otherwise an empty set
	 */
	public List<AssociationRole> retrieveAssociationRolesBy(Association a,
			AssociationRole ar);
}
