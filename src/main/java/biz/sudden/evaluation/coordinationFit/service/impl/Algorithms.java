package biz.sudden.evaluation.coordinationFit.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Algorithms {

	public static <T> Path<T> findCriticalPath(
			NodeInfoProvider<T> nodeInfoProvider) {
		Path<T> criticalPath = null;
		for (T root : nodeInfoProvider.getRootNodes()) {
			Path<T> path = findCriticalPath(nodeInfoProvider, root);
			if (criticalPath == null
					|| path.getWeight() > criticalPath.getWeight())
				criticalPath = path;
		}
		if (criticalPath == null)
			return null;

		Collections.reverse(criticalPath.getPath());
		return criticalPath;
	}

	private static <T> Path<T> findCriticalPath(
			NodeInfoProvider<T> nodeInfoProvider, T nodeId) {
		Collection<T> childNodes = nodeInfoProvider.getChildNodes(nodeId);
		if (childNodes.isEmpty()) {
			List<T> path = new ArrayList<T>();
			path.add(nodeId);
			return new Path<T>(path, nodeInfoProvider.getNodeWeight(nodeId));
		}

		Path<T> longestPath = null;
		for (T childNodeId : childNodes) {
			Path<T> childPath = findCriticalPath(nodeInfoProvider, childNodeId);
			if (longestPath == null
					|| childPath.getWeight() > longestPath.getWeight())
				longestPath = childPath;
		}
		longestPath = longestPath.append(nodeId, nodeInfoProvider
				.getNodeWeight(nodeId));
		return longestPath;
	}

	public static <T> double computeWeigthOnCriticalPath(
			NodeInfoProvider<T> nodeInfoProvider) {
		Path<T> criticalPath = findCriticalPath(nodeInfoProvider);
		if (criticalPath == null)
			return 0;

		return criticalPath.getWeight();
	}

	public static <T> double computeTotalWeigth(
			NodeInfoProvider<T> nodeInfoProvider) {
		List<T> allNodes = new ArrayList<T>(nodeInfoProvider.getRootNodes());

		for (int index = 0; index < allNodes.size(); index++) {
			T node = allNodes.get(index);

			for (T child : nodeInfoProvider.getChildNodes(node)) {
				if (!allNodes.contains(child))
					allNodes.add(child);
			}
		}

		double graphWeight = 0;
		for (T node : allNodes) {
			graphWeight += nodeInfoProvider.getNodeWeight(node);
		}
		return graphWeight;
	}

}
