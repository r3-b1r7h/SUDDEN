package biz.sudden.baseAndUtility.web.controller.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject.UserObjectActionEvent;

public class Tree extends DefaultTreeModel implements ActionListener
/* , Renderable */{

	private static final long serialVersionUID = 1L;

	public static DefaultMutableTreeNode NULL_NODE = new DefaultMutableTreeNode(
			new StringUserObject("xxx"));

	private Logger logger = Logger.getLogger(this.getClass());

	protected ConnectableService connectableService;
	protected ScopeController scopeController;
	protected DefaultMutableTreeNode rootNode;
	// it might be that as a result of this event some listener registers itself
	// as a new actionlistener...>concurrent modification
	// we need some threadsafe list here
	protected List<ActionListener> listeners = new Vector<ActionListener>();

	protected boolean alsoCreateAssociationNodes = false;

	protected boolean alsoShowOccurrence = false;

	protected boolean expandAll = false;

	protected Scope associationScope;

	protected Scope occurrenceScope;
	public int treeNodeCounter;

	// protected PersistentFacesState state = null;
	// protected OnDemandRenderer renderer;

	public Tree(ConnectableService connectableService, ScopeController scope) {
		super(new DefaultMutableTreeNode(new StringUserObject(" ")));
		this.connectableService = connectableService;
		this.scopeController = scope;
		setRoot((DefaultMutableTreeNode) this.getRoot());
	}

	public Tree(ConnectableService connectableService, ScopeController scope,
			String rootnodename) {
		super(new DefaultMutableTreeNode(new StringUserObject(rootnodename)));
		this.connectableService = connectableService;
		this.scopeController = scope;
		setRoot((DefaultMutableTreeNode) this.getRoot());
	}

	public Tree(ConnectableService connectableService, ScopeController scope,
			Connectable rootnode) {
		super(new DefaultMutableTreeNode(new ConnectableUserObject(rootnode)));
		this.connectableService = connectableService;
		this.scopeController = scope;
		setRoot((DefaultMutableTreeNode) this.getRoot());
	}

	/** create an empty tree with this name in the root node */
	public void createTree(String rootnodename) {
		if (rootNode == null) {
			setRoot(new DefaultMutableTreeNode(new StringUserObject(
					rootnodename)));
		} else {
			new StringUserObject(rootnodename,
					(DefaultMutableTreeNode) getRoot());
			((UserObject) ((DefaultMutableTreeNode) getRoot()).getUserObject())
					.addActionListener(this);
		}
	}

	/**
	 * Create a tree (model) starting from a certain connectable, exploring the
	 * connections, constraint eventually by input in the searched SearchedAssoc
	 * parameter without the associations as nodes in the tree
	 * 
	 */
	public void retrieveTree(int distance, Connectable c,
			SearchedAssoc searchedAssoc) {
		if (searchedAssoc != null) {
			ArrayList<SearchedAssoc> ass = new ArrayList<SearchedAssoc>();
			ass.add(searchedAssoc);
			retrieveTree(distance, c, ass);
		} else {
			retrieveTree(distance, c, (Collection<SearchedAssoc>) null);
		}
	}

	/**
	 * Create a tree (model) starting from a certain connectable, exploring the
	 * connections, constraint eventually by input in the searched Assocs
	 * parameter
	 * 
	 * @return a TreeModel with the Connectable C as root UserObject
	 * @param distance
	 *            -1->retrieves all
	 * @param c
	 *            Connectable to start with
	 * @param searchedAssocs
	 *            set of valid Assoc descriptions specified as [roleType
	 *            (parent), associationType, roleType(child), scope] scope may
	 *            be null, roleType(child) may be null (only roleType(parent)
	 *            and associationType considered) roleType(parent) may be null
	 *            (roleType(child) is ignored only associationType considered)
	 *            associationType may be null where only roleType(parent) or
	 *            roleType(child) is considered and searchedAssocs may be null
	 */
	public void retrieveTree(int distance, Connectable c,
			Collection<SearchedAssoc> searchedAssocs) {
		clearTree();
		if (rootNode == null)
			setRoot(new DefaultMutableTreeNode(new ConnectableUserObject(c)));
		else
			new ConnectableUserObject(c, (DefaultMutableTreeNode) getRoot());
		((UserObject) ((DefaultMutableTreeNode) getRoot()).getUserObject())
				.setExpanded(true);

		if (searchedAssocs != null && searchedAssocs.size() > 0) {
			// create AssocNode and append rootTreeNode
			for (SearchedAssoc sA : searchedAssocs) {
				retrieveTreeNodes(distance, (ConnectableUserObject) rootNode
						.getUserObject(), sA);
			}
		} else {
			retrieveTreeNodes(distance, (ConnectableUserObject) rootNode
					.getUserObject(), null);
		}
	}

	/**
	 * create next level in tree by associating the given connectables to a root
	 */
	public void createTree(int distance, Connectable root,
			Collection<Connectable> children) {
		clearTree();
		if (rootNode == null)
			setRoot(new DefaultMutableTreeNode(new ConnectableUserObject(root)));
		else
			new ConnectableUserObject(root, (DefaultMutableTreeNode) getRoot());

		ConnectableUserObject rootObject = (ConnectableUserObject) rootNode
				.getUserObject();
		rootObject.setReference(root);
		rootObject.setExpanded(true);
		retrieveNodes(distance, children, rootObject, null, null);

	}

	public void recreateTree(int distance, SearchedAssoc assoc) {
		ArrayList<SearchedAssoc> aso = new ArrayList<SearchedAssoc>();
		aso.add(assoc);
		recreateTree(distance, aso);
	}

	public void recreateTree(int distance, List<SearchedAssoc> search) {
		treeNodeCounter = 0;
		if (rootNode != null) {
			if (rootNode.getUserObject() instanceof ConnectableUserObject) {
				retrieveTree(distance, ((ConnectableUserObject) rootNode
						.getUserObject()).getReference(), search);
			} else {
				List<ConnectableUserObject> objs = new ArrayList<ConnectableUserObject>();
				for (int i = 0; i < rootNode.getChildCount(); ++i) {
					if (((DefaultMutableTreeNode) rootNode.getChildAt(i))
							.getUserObject() instanceof ConnectableUserObject)
						objs
								.add((ConnectableUserObject) ((DefaultMutableTreeNode) rootNode
										.getChildAt(i)).getUserObject());
				}
				clearTree();
				this.attachNodesAndRecursiveRetrieveNodes(distance, objs,
						(UserObject) rootNode.getUserObject(), search);
			}
		}
	}

	/** clear the whole tree BUT the root node! */
	public void clearTree() {
		treeNodeCounter = 0;
		if (rootNode != null)
			while (rootNode.getChildCount() > 0)
				clearSubTree((DefaultMutableTreeNode) rootNode.getChildAt(0));
	}

	public void clearTreeAndRootNode() {
		clearSubTree(rootNode);
		rootNode = null;
		setRoot(null);
	}

	private void clearSubTree(DefaultMutableTreeNode startNode) {
		if (startNode != null && startNode.getUserObject() != null) {
			((UserObject) startNode.getUserObject()).deleteNodeAndSubNodes();
		}
	}

	/**
	 * Create a tree (model) starting from a certain connectable, exploring the
	 * connections, constraint eventually by input in the SearchedAssoc
	 * parameter
	 * 
	 * @return a TreeModel with the Connectable C as root UserObject
	 * @param distance
	 *            -1->retrieves all
	 * @param sA
	 *            "search Constraint" specified as [roleType (parent),
	 *            associationType, roleType(child), scope] scope may be null,
	 *            roleType(child) may be null (only roleType(parent) and
	 *            associationType considered) roleType(parent) may be null
	 *            (roleType(child) is ignored only associationType considered)
	 *            associationType may be null where only roleType(parent) or
	 *            roleType(child) is considered and searchedAssocs may be null
	 * @param c
	 *            Connectable to start with
	 */
	public ConnectableUserObject retrieveTreeNodes(int distance,
			ConnectableUserObject parent, SearchedAssoc sA) {
		// logger.debug("createTreeNodesAndAssoc called");
		if (associationScope == null)
			associationScope = scopeController.getUserScope();
		if (sA != null && sA.getAssocType() != null
				&& sA.getParentRoleType() != null
				&& sA.getChildRoleType() != null) {
			if (alsoCreateAssociationNodes) {
				for (Association assoc : connectableService
						.retrieveAssociationBy(sA.getAssocType(), parent
								.getReference(), sA.getParentRoleType(), sA
								.getChildRoleType(), associationScope))
					retrieveNodes(distance, connectableService
							.directedSearchNeighbor(sA.getAssocType(), parent
									.getReference(), sA.getParentRoleType(), sA
									.getChildRoleType(), associationScope),
					// createAssocObj(parent, sA.getParentRoleType(), assoc,
							// sA.getChildRoleType()), sA);
							attachAssocNodeObj(parent, sA.getParentRoleType(),
									assoc), sA.getChildRoleType(), sA);
			} else {
				retrieveNodes(distance, connectableService
						.directedSearchNeighbor(sA.getAssocType(), parent
								.getReference(), sA.getParentRoleType(), sA
								.getChildRoleType(), associationScope), parent,
						sA.getChildRoleType(), sA);
			}
		} else if (sA != null && sA.getAssocType() != null
				&& sA.getParentRoleType() != null) {
			List<Association> assL = connectableService.retrieveAssociationBy(
					sA.getAssocType(), parent.getReference(), sA
							.getParentRoleType(), associationScope);
			for (Association ass : assL) {
				List<AssociationRole> roles = connectableService
						.retrieveCounterpartAssociationRoles(ass, parent
								.getReference(), sA.getParentRoleType());
				AssocUserObject assoc = null;
				if (alsoCreateAssociationNodes && roles != null) {
					assoc = attachAssocNodeObj(parent, sA.getParentRoleType(),
							ass);
				}

				for (AssociationRole r : roles) {
					if (alsoCreateAssociationNodes) {
						attachNodeAndRecursiveRetrieveNodes(distance, r
								.getPlayer(), assoc, r.getType().getType(), sA);
						// retrieveNode(distance, r.getPlayer(),
						// createAssocObj(parent, sA.getParentRoleType(),
						// r.getParent(),
						// r.getType().getType()), sA);

					} else {
						attachNodeAndRecursiveRetrieveNodes(distance, r
								.getPlayer(), parent, r.getType().getType(), sA);
						// retrieveNode(distance, r.getPlayer(), parent, sA);
					}
				}
			}
		} else if (sA != null && sA.getAssocType() == null
				&& sA.getParentRoleType() != null) {
			List<AssociationRole> aRolesChildren = connectableService
					.retrieveCounterpartAssociationRoles(parent.getReference(),
							sA.getParentRoleType(), associationScope);
			HashMap<Long, AssocUserObject> assocs = new HashMap<Long, AssocUserObject>();
			for (AssociationRole arChild : aRolesChildren) {
				if (alsoCreateAssociationNodes) {
					if (!assocs.containsKey(arChild.getParent().getId())) {
						assocs.put(arChild.getParent().getId(),
								attachAssocNodeObj(parent, sA
										.getParentRoleType(), arChild
										.getParent()));
					}
					attachNodeAndRecursiveRetrieveNodes(distance, arChild
							.getPlayer(), assocs.get(arChild.getParent()
							.getId()), arChild.getType().getType(), sA);
					// retrieveNode(distance, arChild.getPlayer(),
					// createAssocObj(parent, sA.getParentRoleType(),
					// arChild.getParent(),
					// arChild.getType().getType()), sA);
				} else {
					attachNodeAndRecursiveRetrieveNodes(distance, arChild
							.getPlayer(), parent, arChild.getType().getType(),
							sA);
					// retrieveNode(distance, arChild.getPlayer(), parent, sA);
				}
			}
		} else {
			// get all roles the parent node plays
			List<AssociationRole> AL = connectableService
					.retrieveAssociationRolesOf(parent.getReference(),
							associationScope);
			if (AL != null)
				for (AssociationRole arParent : AL) {
					boolean getNext = true;
					// if the AssocType is given, check if the role belongs to
					// that
					// type
					if (sA != null
							&& sA.getAssocType() != null
							&& !arParent.getParent().getType().getType()
									.equals(sA.getAssocType())) {
						getNext = false;
					}
					if (getNext) {
						List<AssociationRole> rolesChildren = connectableService
								.retrieveCounterpartAssociationRoles(arParent
										.getParent(), parent.getReference());
						AssocUserObject assoc = null;
						if (alsoCreateAssociationNodes && rolesChildren != null
								&& rolesChildren.size() > 0) {
							assoc = attachAssocNodeObj(parent, arParent
									.getType().getType(), arParent.getParent());
						}
						for (AssociationRole arChild : rolesChildren) {
							// if the child role is given, check if the
							// conterpart's role is equal to it
							if (sA != null
									&& sA.getChildRoleType() != null
									&& !arChild.getType().getType().equals(
											sA.getChildRoleType())) {
								getNext = false;
							}
							if (getNext) {
								if (alsoCreateAssociationNodes) {
									attachNodeAndRecursiveRetrieveNodes(
											distance, arChild.getPlayer(),
											assoc, arChild.getType().getType(),
											sA);
									// retrieveNode(distance,
									// arChild.getPlayer(),
									// createAssocObj(parent,
									// arParent.getType().getType(),
									// arParent.getParent(),
									// arChild.getType().getType()), sA);
								} else {
									attachNodeAndRecursiveRetrieveNodes(
											distance, arChild.getPlayer(),
											parent,
											arChild.getType().getType(), sA);
									// retrieveNode(distance,
									// arChild.getPlayer(), parent, sA);
								}
							}
							getNext = true;
						}
					}

					getNext = true;
				}
		}
		return parent;
	}

	public void retrieveNodes(int distance, Collection<Connectable> children,
			SearchedAssoc sA) {
		if (sA != null)
			retrieveNodes(distance, children, sA.getChildRoleType(), sA);
		else
			retrieveNodes(distance, children, (String) null,
					(SearchedAssoc) null);
	}

	public void retrieveNodes(int distance, Collection<Connectable> children,
			UserObject parent, SearchedAssoc sA) {
		if (sA != null)
			retrieveNodes(distance, children, parent, sA.getChildRoleType(), sA);
		else
			retrieveNodes(distance, children, parent, null, null);
	}

	/**
	 * append the selected children nodes to rootnode of this tree and explore
	 * the network further
	 * 
	 * @param distance
	 *            -1->retrieves all distance == 0 don't explore further
	 * @param sA
	 *            may be null and is not used here but only for further
	 *            exploration "search Constraint" specified as [roleType
	 *            (parent), associationType, roleType(child), scope] scope may
	 *            be null, roleType(child) may be null (only roleType(parent)
	 *            and associationType considered) roleType(parent) may be null
	 *            (roleType(child) is ignored only associationType considered)
	 *            associationType may be null where only roleType(parent) or
	 *            roleType(child) is considered and searchedAssocs may be null
	 */
	public void retrieveNodes(int distance, Collection<Connectable> children,
			String RoleForChildren, SearchedAssoc sA) {
		if (children != null)
			logger.debug("Tree.appendNodes: rootnode: '"
					+ rootNode.getUserObject() + "' #children"
					+ rootNode.getChildCount() + "  - add nodes: #"
					+ children.size());
		else
			logger.debug("Tree.appendNodes: rootnode: '"
					+ rootNode.getUserObject() + "' #children"
					+ rootNode.getChildCount());
		retrieveNodes(distance, children,
				(UserObject) rootNode.getUserObject(), RoleForChildren, sA);
		logger.debug("Tree.appendNodes: rootnode: '" + rootNode.getUserObject()
				+ "' #children" + rootNode.getChildCount());
	}

	/**
	 * append the selected children nodes to the given parent and explore the
	 * network further
	 * 
	 * @param distance
	 *            -1->retrieves all distance == 0 don't explore further
	 * @param sA
	 *            may be null and is not used here but only for further
	 *            exploration "search Constraint" specified as [roleType
	 *            (parent), associationType, roleType(child), scope] scope may
	 *            be null, roleType(child) may be null (only roleType(parent)
	 *            and associationType considered) roleType(parent) may be null
	 *            (roleType(child) is ignored only associationType considered)
	 *            associationType may be null where only roleType(parent) or
	 *            roleType(child) is considered and searchedAssocs may be null
	 */
	protected void retrieveNodes(int distance,
			Collection<Connectable> children, UserObject parentNode,
			String roleForChildren, SearchedAssoc sA) {
		// trace nodes recursive and append to c
		if (children != null && children.size() > 0) {
			for (Connectable cNode : children) {
				attachNodeAndRecursiveRetrieveNodes(distance, cNode,
						parentNode, roleForChildren, sA);
			}
		}
	}

	/**
	 * append the selected child node to the given parent and explore the
	 * network further
	 * 
	 * @param distance
	 *            -1->retrieves all distance == 0 don't explore further
	 * @param sA
	 *            may be null and is not used here but only for further
	 *            exploration "search Constraint" specified as [roleType
	 *            (parent), associationType, roleType(child), scope] scope may
	 *            be null, roleType(child) may be null (only roleType(parent)
	 *            and associationType considered) roleType(parent) may be null
	 *            (roleType(child) is ignored only associationType considered)
	 *            associationType may be null where only roleType(parent) or
	 *            roleType(child) is considered and searchedAssocs may be null
	 */
	protected void attachNodeAndRecursiveRetrieveNodes(int distance,
			Connectable child, UserObject parentNode, String roleForChild,
			SearchedAssoc sA) {
		ConnectableUserObject current = new ConnectableUserObject();
		// logger.debug("User Object Text: "+getNameValueFrom(c));
		current.setReference(child);
		current.setCurrentRoleType(roleForChild);
		if (!alsoCreateAssociationNodes)
			current.setShowRole(false);
		else
			current.setShowRole(true);
		current.setShowOccurences(alsoShowOccurrence);
		String txt = getNodeText(current);

		if (txt != null) {
			if (alsoShowOccurrence) {
				StringBuffer newText = new StringBuffer(txt);
				current.setOccurrences(connectableService.retrieveOccurrences(
						child, occurrenceScope));
				newText.append(" [");
				for (Occurrence o : current.getOccurrences()) {
					newText.append(o.getValue().getValue());
					newText.append(", ");
				}
				if (current.getOccurrences().size() > 0)
					newText.replace(newText.lastIndexOf(","), newText.length(),
							"] ");
				current.setText(newText.toString());
			} else {
				current.setText(txt);
			}
		} else if (alsoShowOccurrence && child != null) {
			current.setOccurrences(connectableService.retrieveOccurrences(
					child, occurrenceScope));
		}
		attachNodeAndRecursiveRetrieveNodes(distance, current, parentNode, sA);
	}

	/**
	 * overwrite this with your own string that is displayed in the tree with
	 * nodes (topics/connetables).
	 */
	public String getNodeText(ConnectableUserObject connectable) {
		return null;
	}

	protected void attachNodesAndRecursiveRetrieveNodes(int distance,
			Collection<ConnectableUserObject> children, UserObject parentNode,
			List<SearchedAssoc> sA) {
		// trace nodes recursive and append to c
		if (children != null && children.size() > 0) {
			for (ConnectableUserObject cNode : children) {
				if (sA != null)
					for (SearchedAssoc sa : sA)
						attachNodeAndRecursiveRetrieveNodes(distance, cNode,
								parentNode, sa);
				else
					attachNodeAndRecursiveRetrieveNodes(distance, cNode,
							parentNode, null);
			}
		}
	}

	protected void attachNodeAndRecursiveRetrieveNodes(int distance,
			ConnectableUserObject child, UserObject parentNode, SearchedAssoc sA) {
		parentNode.addNode(child);
		child.setSearchCriteria(sA);
		child.addActionListener(this);
		if (expandAll)
			child.setExpanded(true);
		else
			child.setExpanded(false);

		treeNodeCounter++;
		if (distance > 0) {
			retrieveTreeNodes(distance - 1, child, sA);
		}
	}

	public UserObject attachNodeObj(UserObject parent, String nodetext) {
		return attachNodeObj(parent, new StringUserObject(nodetext));
	}

	public UserObject attachNodeObj(UserObject parent, UserObject node) {
		parent.addNode(node);
		node.addActionListener(this);
		return node;
	}

	public UserObject attachNodeObj(String nodetext) {
		return attachNodeObj((UserObject) ((DefaultMutableTreeNode) getRoot())
				.getUserObject(), new StringUserObject(nodetext));
	}

	public UserObject attachNodeObj(UserObject node) {
		return attachNodeObj((UserObject) getRoot(), node);
	}

	protected AssocUserObject attachAssocNodeObj(ConnectableUserObject parent,
			String rolename, Association association) {
		AssocUserObject assocObject = new AssocUserObject(parent, rolename,
				association, null);
		parent.addNode(assocObject);
		String txt = getAssociationText(assocObject);
		if (txt != null)
			assocObject.setText(txt);
		if (expandAll)
			assocObject.setExpanded(true);
		else
			assocObject.setExpanded(false);
		assocObject.addActionListener(this);
		return assocObject;
	}

	/**
	 * overwrite this with your own string that is displayed in the tree with
	 * associations.
	 */
	public String getAssociationText(AssocUserObject assocObject) {
		return null;
	}

	@Override
	public void setRoot(TreeNode rootn) {
		if (rootNode != null) {
			((UserObject) rootNode.getUserObject()).deleteNodeAndSubNodes();
		}

		treeNodeCounter = 1;
		if (rootn != null)
			rootNode = (DefaultMutableTreeNode) rootn;
		else
			rootNode = NULL_NODE;
		super.setRoot(rootNode);

		if (rootNode != null && rootNode != NULL_NODE) {
			((UserObject) rootNode.getUserObject()).setWrapper(rootNode);
			((UserObject) rootNode.getUserObject()).addActionListener(this);
			((UserObject) rootNode.getUserObject()).setExpanded(true);
		}
	}

	public void removeTreeModelListeners() {
		TreeModelListener[] l = super.getTreeModelListeners();
		for (int i = 0; i < l.length; ++i) {
			super.removeTreeModelListener(l[i]);
		}
	}

	public static List<Connectable> removeWhenInRole(List<Connectable> tmp,
			String role, Scope s, ConnectableService service) {
		AssocRoleType art = service.retrieveAssocRoleType(role);
		if (art != null) {
			Long roleId = art.getId();
			for (int i = 0; i < tmp.size(); ++i) {
				List<AssociationRole> roles = service
						.retrieveAssociationRolesOf(tmp.get(i), s);
				for (AssociationRole r : roles) {
					if (r.getType().getId().equals(roleId)) {
						tmp.remove(i);
						--i;
						break;
					}
				}
			}
		}
		return tmp;
	}

	public List<Connectable> removeWhenInRole(List<Connectable> tmp,
			String[] roles, Scope s) {
		return Tree.removeWhenInRole(tmp, roles, s, connectableService);
	}

	public static List<Connectable> removeWhenInRole(List<Connectable> tmp,
			String[] roles, Scope s, ConnectableService service) {
		ArrayList<Long> roleids = new ArrayList<Long>();
		for (int i = 0; i < roles.length; ++i) {
			AssocRoleType art = service.retrieveAssocRoleType(roles[i]);
			if (art != null) {
				roleids.add(art.getId());
			} else {
				Logger.getLogger(Tree.class).error(
						"Not AssocRoleType found: " + roles[i]);
			}
		}

		for (int i = 0; i < tmp.size(); ++i) {
			List<AssociationRole> connroles = service
					.retrieveAssociationRolesOf(tmp.get(i), s);
			boolean nextRole = true;
			if (connroles != null) {
				for (int ii = 0; ii < connroles.size() && nextRole; ++ii) {
					AssociationRole r = connroles.get(ii);
					for (int iii = 0; iii < roleids.size() && nextRole; ++iii) {
						if (r.getType().getId().equals(roleids.get(iii))) {
							tmp.remove(i);
							--i;
							nextRole = false;
						}
					}
				}
			}
		}
		return tmp;
	}

	public List<Connectable> removeWhenInRole(List<Connectable> tmp,
			String role, Scope s) {
		return Tree.removeWhenInRole(tmp, role, s, connectableService);
	}

	public static List<Connectable> removeWhenNotInRoleOnly(
			List<Connectable> tmp, String role, Scope s,
			ConnectableService service) {
		Logger.getLogger(Tree.class).warn(
				"removeWhenNotInRoleOnly: for #connectables: " + tmp.size());
		long time = System.currentTimeMillis();
		if (role != null) {
			AssocRoleType art = service.retrieveAssocRoleType(role);
			if (art != null) {
				Long roleId = art.getId();
				for (int i = 0; i < tmp.size(); ++i) {
					List<AssociationRole> roles = service
							.retrieveAssociationRolesOf(tmp.get(i), s);
					for (AssociationRole r : roles) {
						if (!r.getType().getId().equals(roleId)) {
							tmp.remove(i);
							--i;
							break;
						}
					}
				}
			}
		}
		Logger.getLogger(Tree.class).warn(
				"removeWhenNotInRoleOnly: took (ms):"
						+ (System.currentTimeMillis() - time));
		return tmp;
	}

	public List<Connectable> removeWhenNotInRoleOnly(List<Connectable> tmp,
			String role, Scope s) {
		return Tree.removeWhenNotInRoleOnly(tmp, role, s, connectableService);
	}

	public List<Connectable> removeWhenNotInRoleOnly(List<Connectable> tmp,
			String[] roles, Scope s) {
		return Tree.removeWhenNotInRoleOnly(tmp, roles, s, connectableService);
	}

	public static List<Connectable> removeWhenNotInRoleOnly(
			List<Connectable> tmp, String[] roles, Scope s,
			ConnectableService service) {
		Long[] roleids = new Long[roles.length];
		for (int i = 0; i < roles.length; ++i) {
			roleids[i] = service.retrieveAssocRoleType(roles[i]).getId();
		}

		for (int i = 0; i < tmp.size(); ++i) {
			List<AssociationRole> connroles = service
					.retrieveAssociationRolesOf(tmp.get(i), s);
			boolean nextRole = true;
			for (int ii = 0; ii < connroles.size() && nextRole; ++ii) {
				AssociationRole r = connroles.get(ii);
				for (int iii = 0; iii < roleids.length && nextRole; ++iii) {
					if (!r.getType().getId().equals(roleids[iii])) {
						tmp.remove(i);
						--i;
						nextRole = false;
					}
				}
			}
		}
		return tmp;
	}

	public static List<Connectable> removeWhenInDifferentRoles(
			List<Connectable> tmp, Scope s, ConnectableService service) {
		for (int i = 0; i < tmp.size(); ++i) {
			List<AssociationRole> roles = service.retrieveAssociationRolesOf(
					tmp.get(i), s);
			boolean brk = false;
			if (roles != null && roles.size() > 1)
				for (AssociationRole r : roles) {
					AssocRoleType art = r.getType();
					if (art != null) {
						Long roleId = art.getId();
						for (AssociationRole rr : roles) {
							if (!rr.getType().getId().equals(roleId)) {
								tmp.remove(i);
								--i;
								brk = true;
								break;
							}
						}
					}
					if (brk)
						break;
				}
		}
		return tmp;
	}

	public List<Connectable> removeWhenInDifferentRoles(List<Connectable> tmp,
			Scope s) {
		return Tree.removeWhenInDifferentRoles(tmp, s, connectableService);
	}

	public void addActionListener(ActionListener x) {
		if (!listeners.contains(x))
			listeners.add(x);
	}

	public void removeActionListener(ActionListener x) {
		listeners.remove(x);
	}

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		logger.debug("processAction:" + arg0);
		if (arg0 instanceof UserObjectActionEvent) {
			UserObject uo = ((UserObjectActionEvent) arg0).getUserObject();
			if (uo.retrieveChildren() && uo.getWrapper().isLeaf()
					&& uo instanceof ConnectableUserObject) {
				retrieveTreeNodes(1, (ConnectableUserObject) uo, uo
						.getSearchCriteria());
			}
		}
		for (int i = 0; i < listeners.size(); ++i) {
			listeners.get(i).processAction(arg0);
		}
	}

	public ConnectableService getConnectableService() {
		return connectableService;
	}

	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	/**
	 * @return the alsoCreateAssociationNodes
	 */
	public boolean getAlsoCreateAssociationNodes() {
		return alsoCreateAssociationNodes;
	}

	/**
	 * @param alsoCreateAssociationNodes
	 *            the alsoCreateAssociationNodes to set
	 */
	public void setAlsoCreateAssociationNodes(boolean alsoCreateAssociationNodes) {
		this.alsoCreateAssociationNodes = alsoCreateAssociationNodes;
	}

	/**
	 * @return the alsoShowOccurrence
	 */
	public boolean getAlsoShowOccurrence() {
		return alsoShowOccurrence;
	}

	/**
	 * @param alsoShowOccurrence
	 *            the alsoShowOccurrence to set
	 */
	public void setAlsoShowOccurrence(boolean alsoShowOccurrence) {
		this.alsoShowOccurrence = alsoShowOccurrence;
	}

	public boolean getExpandAll() {
		return this.expandAll;
	}

	public void setExpandAll(boolean expandNodes) {
		expandAll = expandNodes;
	}

	/**
	 * @return the associationScope
	 */
	public Scope getAssociationScope() {
		return associationScope;
	}

	/**
	 * @param associationScope
	 *            the associationScope to set
	 */
	public void setAssociationScope(Scope associationScope) {
		this.associationScope = associationScope;
	}

	/**
	 * @return the occurrenceScope
	 */
	public Scope getOccurrenceScope() {
		return occurrenceScope;
	}

	/**
	 * @param occurrenceScope
	 *            the occurrenceScope to set
	 */
	public void setOccurrenceScope(Scope occurrenceScope) {
		this.occurrenceScope = occurrenceScope;
	}

}
