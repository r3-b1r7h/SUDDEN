package biz.sudden.evaluation.coordinationFit.service.impl;

import java.util.Set;

public interface NodeInfoProvider<T> {
	Set<T> getRootNodes();

	Set<T> getChildNodes(T nodeId);

	double getNodeWeight(T nodeId);
}
