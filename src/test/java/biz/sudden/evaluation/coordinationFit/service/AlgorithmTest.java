package biz.sudden.evaluation.coordinationFit.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import biz.sudden.evaluation.coordinationFit.service.impl.Algorithms;
import biz.sudden.evaluation.coordinationFit.service.impl.NodeInfoProvider;
import biz.sudden.evaluation.coordinationFit.service.impl.Path;

public class AlgorithmTest {
	Map<String, List<String>> treeData = new HashMap<String, List<String>>();
	{
		treeData.put("A", Arrays.asList("B"));
		treeData.put("AA", Arrays.asList("B"));
		treeData.put("B", Arrays.asList("C"));
		treeData.put("C", Arrays.asList("D", "E", "F"));
		treeData.put("D", Arrays.asList("G"));
		treeData.put("E", Arrays.asList("G"));
		treeData.put("F", Arrays.asList("H", "I", "J"));
		treeData.put("G", new ArrayList<String>());
		treeData.put("H", new ArrayList<String>());
		treeData.put("I", new ArrayList<String>());
		treeData.put("J", new ArrayList<String>());
	}

	Map<String, Integer> treeWeightData = new HashMap<String, Integer>();
	{
		treeWeightData.put("A", 5);
		treeWeightData.put("AA", 3);
		treeWeightData.put("B", 2);
		treeWeightData.put("C", 4);
		treeWeightData.put("D", 3);
		treeWeightData.put("E", 10);
		treeWeightData.put("F", 8);
		treeWeightData.put("G", 10);
		treeWeightData.put("H", 1);
		treeWeightData.put("I", 2);
		treeWeightData.put("J", 14);
	}

	NodeInfoProvider<String> testNodeInfo = new NodeInfoProvider<String>() {

		@Override
		public Set<String> getRootNodes() {
			return new LinkedHashSet<String>(Arrays.asList("A", "AA"));
		}

		@Override
		public Set<String> getChildNodes(String nodeId) {
			return new LinkedHashSet<String>(treeData.get(nodeId));
		}

		@Override
		public double getNodeWeight(String nodeId) {
			return treeWeightData.get(nodeId);
		}

	};

	@Test
	public void testFindCriticalPathAlgorithm() {
		Path<String> criticalPath = Algorithms.findCriticalPath(testNodeInfo);
		List<String> actualCriticalPath = Arrays
				.asList("A", "B", "C", "F", "J");
		Assert.assertEquals(actualCriticalPath, criticalPath.getPath());
	}

	@Test
	public void testComputeWeightOnCriticalPath() {
		double computedWeigthOnCriticalPath = Algorithms
				.computeWeigthOnCriticalPath(testNodeInfo);
		Assert.assertEquals(33.0, computedWeigthOnCriticalPath);
	}

	@Test
	public void testComputeTotalWeight() {
		double computedWeigthOnCriticalPath = Algorithms
				.computeTotalWeigth(testNodeInfo);
		Assert.assertEquals(62.0, computedWeigthOnCriticalPath);
	}

}
